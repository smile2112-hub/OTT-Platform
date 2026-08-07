package com.cdac.flowflix.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cdac.flowflix.service.UserService;

@Component
public class StartupRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) {

        userService.createDefaultAdmin();

    }

}