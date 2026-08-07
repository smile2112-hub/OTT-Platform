package com.cdac.flowflix.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.JWTLogin;
import com.cdac.flowflix.dto.LoginDTO;
import com.cdac.flowflix.dto.ProfileDTO;
import com.cdac.flowflix.dto.UserDTO;
import com.cdac.flowflix.model.Login;
import com.cdac.flowflix.model.Role;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.repository.FavoriteRepository;
import com.cdac.flowflix.repository.HistoryRepository;
import com.cdac.flowflix.repository.ReviewRepository;
import com.cdac.flowflix.repository.UserRepository;
import com.cdac.flowflix.repository.WatchHistoryRepository;
import com.cdac.flowflix.repository.WatchlistRepository;
import com.cdac.flowflix.repository.WatchProgressRepository;
import com.cdac.flowflix.security.JwtUtil;
import com.cdac.flowflix.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private WatchHistoryRepository watchHistoryRepository;

    @Autowired
    private WatchProgressRepository watchProgressRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ==========================================
    // FIND ALL USERS
    // ==========================================

    @Override
    public List<UserDTO> findAllUsers() {

        List<UserDTO> users = new ArrayList<>();

        for (User user : userRepository.findAll()) {

            if (user.getRole() == Role.USER) {

                users.add(new UserDTO(user));

            }

        }

        return users;

    }

    // ==========================================
    // SAVE USER
    // ==========================================

    @Override
    public User save(User user) {

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()));

        return userRepository.save(user);

    }

    // ==========================================
    // FIND USER BY ID
    // ==========================================

    @Override
    public User findOne(Long id) {

        return userRepository
                .findById(id)
                .orElse(null);

    }

    // ==========================================
    // FIND USERNAME
    // ==========================================

    @Override
    public User findByUsername(String username) {

        return userRepository.findByUsername(username);

    }

    // ==========================================
    // DELETE USER
    // ==========================================

    @Override
    public User delete(User user) {

        if (user != null) {

            userRepository.delete(user);

        }

        return user;

    }

    // ==========================================
    // CURRENT USER
    // ==========================================

    @Override
    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            return null;

        }

        return userRepository.findByUsername(
                authentication.getName());

    }

    // ==========================================
    // SET CURRENT USER
    // ==========================================

    @Override
    public void setCurrentUser(User user) {

        Collection<GrantedAuthority> authorities =
                new ArrayList<>();

        authorities.add(

                new SimpleGrantedAuthority(

                        user.getRole().toString()));

        Authentication authentication =
                new PreAuthenticatedAuthenticationToken(

                        user.getUsername(),

                        null,

                        authorities);

        SecurityContextHolder

                .getContext()

                .setAuthentication(authentication);

    }

    // ==========================================
    // VALIDATE USER
    // ==========================================

    @Override
    public String validateUser(User user) {

        if (user.getUsername() == null ||
                user.getUsername().trim().isEmpty()) {

            return "Username Required";

        }

        if (user.getPassword() == null ||
                user.getPassword().trim().isEmpty()) {

            return "Password Required";

        }

        if (user.getNameSurname() == null ||
                user.getNameSurname().trim().isEmpty()) {

            return "Name Required";

        }

        if (user.getEmail() == null ||
                !user.getEmail().matches("^(.+)@(.+)$")) {

            return "Invalid Email";

        }

        if (userRepository.existsByUsername(
                user.getUsername())) {

            return "Username Already Exists";

        }

        if (userRepository.existsByEmail(
                user.getEmail())) {

            return "Email Already Exists";

        }

        return "valid";

    }

    // ==========================================
    // LOGIN
    // ==========================================

    @Override
    public LoginDTO generateToken(Login login) {

        LoginDTO dto = new LoginDTO();

        User user =
                userRepository.findByUsername(
                        login.getUsername());

        if (user == null) {

            dto.setMessageInvalidUsernameOrPassword("yes");

            return dto;

        }

        if (user.isDeleted()) {

            dto.setMessageInvalidUsernameOrPassword(
                    "deactivatedUser");

            return dto;

        }

        if (!passwordEncoder.matches(

                login.getPassword(),

                user.getPassword())) {

            dto.setMessageInvalidUsernameOrPassword("yes");

            return dto;

        }

        JWTLogin jwt = new JWTLogin();

        jwt.setUsername(user.getUsername());

        jwt.setRole(user.getRole().toString());

        dto.setToken(jwtUtil.generateToken(jwt));

        dto.setUserNameSurname(user.getNameSurname());

        dto.setMessageInvalidUsernameOrPassword("no");

        return dto;

    }

    // ==========================================
    // LOGOUT
    // ==========================================

    @Override
    public String isValidLogout() {

        SecurityContextHolder.clearContext();

        return "Logout Successful";

    }
    // ==========================================
    // DEACTIVATE USER
    // ==========================================

    @Override
    public String deactivateUser(Long id) {

        User user = findOne(id);

        if (user == null) {

            return "User Not Found";

        }

        user.setDeleted(true);

        userRepository.save(user);

        return "User Deactivated Successfully";

    }

    // ==========================================
    // ACTIVATE USER
    // ==========================================

    @Override
    public String activateUser(Long id) {

        User user = findOne(id);

        if (user == null) {

            return "User Not Found";

        }

        user.setDeleted(false);

        userRepository.save(user);

        return "User Activated Successfully";

    }

    // ==========================================
    // MAKE ADMIN
    // ==========================================

    @Override
    public String makeAdmin(Long id) {

        User user = findOne(id);

        if (user == null) {

            return "User Not Found";

        }

        user.setRole(Role.ADMIN);

        userRepository.save(user);

        return "User Promoted To Admin";

    }

    // ==========================================
    // REMOVE ADMIN
    // ==========================================

    @Override
    public String removeAdmin(Long id) {

        User user = findOne(id);

        if (user == null) {

            return "User Not Found";

        }

        User currentUser = getCurrentUser();

        if (currentUser != null &&
                currentUser.getId().equals(id)) {

            return "You Cannot Remove Your Own Admin Role";

        }

        if (userRepository.countByRole(Role.ADMIN) <= 1) {

            return "System Must Have At Least One Admin";

        }

        user.setRole(Role.USER);

        userRepository.save(user);

        return "Admin Converted To User";

    }

    // ==========================================
    // DELETE USER
    // ==========================================

    @Override
    @org.springframework.transaction.annotation.Transactional
    public String deleteUser(Long id) {

        User user = findOne(id);

        if (user == null) {
            return "User Not Found";
        }

        User currentUser = getCurrentUser();

        if (currentUser != null &&
                currentUser.getId().equals(id)) {
            return "You Cannot Delete Your Own Account";
        }

        if (user.getRole() == Role.ADMIN &&
                userRepository.countByRole(Role.ADMIN) <= 1) {
            return "Cannot Delete Last Admin";
        }

        // Delete all related records first
        favoriteRepository.deleteByUser(user);
        watchlistRepository.deleteByUser(user);
        reviewRepository.deleteByUser(user);
        watchHistoryRepository.deleteByUser(user);
        watchProgressRepository.deleteByUser(user);
        historyRepository.deleteByUser(user);

        userRepository.delete(user);

        return "User Deleted Successfully";

    }

    // ==========================================
    // CREATE DEFAULT ADMIN
    // ==========================================

    @Override
    public void createDefaultAdmin() {

        if (userRepository.countByRole(Role.ADMIN) > 0) {

            return;

        }

        User admin = new User();

        admin.setNameSurname("Super Admin");

        admin.setUsername("admin");

        admin.setPassword(
                passwordEncoder.encode("admin123"));

        admin.setEmail("admin@flowflix.com");

        admin.setRole(Role.ADMIN);

        admin.setDeleted(false);

        admin.setProfilePicture(null);

        userRepository.save(admin);

        System.out.println("Default Admin Created Successfully");

    }

    // ==========================================
    // GET MY PROFILE
    // ==========================================

    @Override
    public ProfileDTO getMyProfile() {

        User user = getCurrentUser();

        if (user == null) {

            return null;

        }

        return new ProfileDTO(user);

    }

    // ==========================================
    // UPDATE PROFILE
    // ==========================================

    @Override
    public String updateProfile(ProfileDTO profileDTO) {

        User user = getCurrentUser();

        if (user == null) {

            return "User Not Found";

        }

        user.setNameSurname(profileDTO.getNameSurname());

        user.setEmail(profileDTO.getEmail());

        user.setProfilePicture(profileDTO.getProfilePicture());

        userRepository.save(user);

        return "Profile Updated Successfully";

    }
    // ==========================================
    // CHANGE PASSWORD
    // ==========================================

    @Override
    public String changePassword(
            String oldPassword,
            String newPassword) {

        User user = getCurrentUser();

        if (user == null) {

            return "User Not Found";

        }

        if (!passwordEncoder.matches(
                oldPassword,
                user.getPassword())) {

            return "Old Password Is Incorrect";

        }

        user.setPassword(
                passwordEncoder.encode(newPassword));

        userRepository.save(user);

        return "Password Changed Successfully";

    }

    // ==========================================
    // UPLOAD PROFILE PICTURE
    // ==========================================

    @Override
    public String uploadProfilePicture(
            String profilePicture) {

        User user = getCurrentUser();

        if (user == null) {

            return "User Not Found";

        }

        user.setProfilePicture(profilePicture);

        userRepository.save(user);

        return "Profile Picture Updated Successfully";

    }

    // ==========================================
    // REMOVE PROFILE PICTURE
    // ==========================================

    @Override
    public String removeProfilePicture() {

        User user = getCurrentUser();

        if (user == null) {

            return "User Not Found";

        }

        user.setProfilePicture(null);

        userRepository.save(user);

        return "Profile Picture Removed Successfully";

    }

    // ==========================================
    // DELETE MY ACCOUNT
    // ==========================================

    @Override
    public String deleteMyAccount() {

        User user = getCurrentUser();

        if (user == null) {

            return "User Not Found";

        }

        user.setDeleted(true);

        userRepository.save(user);

        SecurityContextHolder.clearContext();

        return "Account Deleted Successfully";

    }

}