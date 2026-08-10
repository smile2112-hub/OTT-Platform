package com.cdac.flowflix.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cdac.flowflix.service.UserService;

@Component

// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.

public class StartupRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) {

        userService.createDefaultAdmin();

    }

}
