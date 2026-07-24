package com.cdac.flowflix.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.JWTLogin;
import com.cdac.flowflix.dto.LoginDTO;
import com.cdac.flowflix.dto.UserDTO;
import com.cdac.flowflix.model.Login;
import com.cdac.flowflix.model.Role;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.repository.UserRepository;
import com.cdac.flowflix.security.JwtUtil;
import com.cdac.flowflix.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public List<UserDTO> findAllUsers() {

        List<UserDTO> list = new ArrayList<>();

        for (User user : userRepository.findAll()) {

            if (user.getRole() == Role.USER) {

                list.add(new UserDTO(user));

            }

        }

        return list;
    }

    @Override
    public User save(User user) {

        return userRepository.save(user);

    }

    @Override
    public User findOne(Long id) {

        return userRepository.findById(id).orElse(null);

    }

    @Override
    public User findByUsername(String username) {

        return userRepository.findByUsername(username);

    }

    @Override
    public User delete(User user) {

        if (user != null) {

            userRepository.delete(user);

        }

        return user;

    }

    @Override
    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {

            return null;

        }

        String username = authentication.getName();

        return userRepository.findByUsername(username);

    }

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

        if (userRepository.findByUsername(
                user.getUsername()) != null) {

            return "Username Already Exists";

        }

        return "valid";

    }

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

            dto.setMessageInvalidUsernameOrPassword("deactivatedUser");

            return dto;

        }

        if (!user.getPassword().equals(login.getPassword())) {

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

    @Override
    public String isValidLogout() {

        SecurityContextHolder.clearContext();

        return "Logout Successful";

    }

    @Override
    public String deactivateUser(Long id) {

        User user = findOne(id);

        if (user == null) {

            return "User Not Found";

        }

        user.setDeleted(true);

        userRepository.save(user);

        return "User Deactivated";

    }

}