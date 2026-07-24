package com.cdac.flowflix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.LoginDTO;
import com.cdac.flowflix.model.Login;
import com.cdac.flowflix.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(
            @RequestBody Login login) {

        return ResponseEntity.ok(
                userService.generateToken(login));

    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {

        return ResponseEntity.ok(
                userService.isValidLogout());

    }

}