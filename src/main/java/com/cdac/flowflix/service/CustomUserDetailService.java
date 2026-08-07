package com.cdac.flowflix.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import com.cdac.flowflix.model.User;
import com.cdac.flowflix.repository.UserRepository;

@Service
public class CustomUserDetailService
        implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(
            String username)
            throws UsernameNotFoundException {

        User user =
                repository.findByUsername(
                        username);

        if (user == null) {

            throw new UsernameNotFoundException(
                    "User Not Found");

        }

        return org.springframework.security.core.userdetails.User

                .builder()

                .username(
                        user.getUsername())

                .password(
                        user.getPassword())

                .roles(
                        user.getRole().name())

                .build();

    }

}