package com.cdac.flowflix.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.cdac.flowflix.dto.LoginDTO;

import com.cdac.flowflix.model.Login;

import com.cdac.flowflix.service.UserService;

@RestController

@RequestMapping("/api/auth")

@CrossOrigin("*")

public class AuthController {

    @Autowired

    private UserService userService;

    @PostMapping("/login")

    public ResponseEntity<LoginDTO>

    login(

            @RequestBody Login login) {

        LoginDTO dto =

                userService.generateToken(

                        login);

        return ResponseEntity.ok(dto);

    }

}