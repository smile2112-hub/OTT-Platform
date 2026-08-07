package com.cdac.flowflix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.ProfileDTO;
import com.cdac.flowflix.model.Role;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    // ==========================================
    // REGISTER USER
    // ==========================================

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody User user) {

        String validation =
                userService.validateUser(user);

        if (!validation.equals("valid")) {

            return ResponseEntity.badRequest()
                    .body(validation);

        }

        user.setRole(Role.USER);

        user.setDeleted(false);

        userService.save(user);

        return ResponseEntity.ok(
                "User Registered Successfully");

    }

    // ==========================================
    // GET MY PROFILE
    // ==========================================

    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> getMyProfile() {

        ProfileDTO profile = userService.getMyProfile();

        if (profile == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(profile);

    }

    // ==========================================
    // UPDATE PROFILE
    // ==========================================

    @PutMapping("/profile")
    public ResponseEntity<String> updateProfile(
            @RequestBody ProfileDTO profileDTO) {

        return ResponseEntity.ok(
                userService.updateProfile(profileDTO));

    }

    // ==========================================
    // CHANGE PASSWORD
    // ==========================================

    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {

        return ResponseEntity.ok(
                userService.changePassword(
                        oldPassword,
                        newPassword));

    }

    // ==========================================
    // UPLOAD PROFILE PICTURE
    // ==========================================

    @PutMapping("/profilePicture")
    public ResponseEntity<String> uploadProfilePicture(
            @RequestParam String profilePicture) {

        return ResponseEntity.ok(
                userService.uploadProfilePicture(
                        profilePicture));

    }

    // ==========================================
    // REMOVE PROFILE PICTURE
    // ==========================================

    @DeleteMapping("/profilePicture")
    public ResponseEntity<String> removeProfilePicture() {

        return ResponseEntity.ok(
                userService.removeProfilePicture());

    }

    // ==========================================
    // DELETE ACCOUNT
    // ==========================================

    @DeleteMapping("/deleteMyAccount")
    public ResponseEntity<String> deleteMyAccount() {

        return ResponseEntity.ok(
                userService.deleteMyAccount());

    }

}