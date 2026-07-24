package com.cdac.flowflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.UserDTO;
import com.cdac.flowflix.model.Role;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<String> register(
            @RequestBody User user) {

        String validation =
                userService.validateUser(user);

        if (!validation.equals("valid")) {

            return ResponseEntity.ok(validation);

        }

        user.setRole(Role.USER);

        user.setDeleted(false);

        userService.save(user);

        return ResponseEntity.ok("Registration Successful");

    }

    @GetMapping("/all")
    public List<UserDTO> getUsers() {

        return userService.findAllUsers();

    }

    @GetMapping("/{id}")
    public User getUser(
            @PathVariable Long id) {

        return userService.findOne(id);

    }

    @GetMapping("/current")
    public User currentUser() {

        return userService.getCurrentUser();

    }

}