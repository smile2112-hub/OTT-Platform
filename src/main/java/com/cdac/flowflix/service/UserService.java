package com.cdac.flowflix.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.LoginDTO;
import com.cdac.flowflix.dto.ProfileDTO;
import com.cdac.flowflix.dto.UserDTO;
import com.cdac.flowflix.model.Login;
import com.cdac.flowflix.model.User;

@Service
public interface UserService {

    // ==========================================
    // USER CRUD
    // ==========================================

    User save(User user);

    User findOne(Long id);

    User findByUsername(String username);

    User delete(User user);

    List<UserDTO> findAllUsers();

    // ==========================================
    // CURRENT USER
    // ==========================================

    User getCurrentUser();

    void setCurrentUser(User user);

    // ==========================================
    // AUTHENTICATION
    // ==========================================

    String validateUser(User user);

    LoginDTO generateToken(Login login);

    String isValidLogout();

    // ==========================================
    // ADMIN FUNCTIONS
    // ==========================================

    String activateUser(Long id);

    String deactivateUser(Long id);

    String makeAdmin(Long id);

    String removeAdmin(Long id);

    String deleteUser(Long id);

    void createDefaultAdmin();

    // ==========================================
    // PROFILE
    // ==========================================

    ProfileDTO getMyProfile();

    String updateProfile(ProfileDTO profileDTO);

    String changePassword(
            String oldPassword,
            String newPassword);

    String uploadProfilePicture(
            String profilePicture);

    String removeProfilePicture();

    String deleteMyAccount();

}