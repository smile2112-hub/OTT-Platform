package com.cdac.flowflix.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cdac.flowflix.service.CustomUserDetailService;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader =
                request.getHeader("Authorization");

        String username = null;

        String jwt = null;

        // ==========================================
        // Read JWT Token
        // ==========================================

        if (authorizationHeader != null &&
                authorizationHeader.startsWith("Bearer ")) {

            jwt = authorizationHeader.substring(7);

            try {

                username = jwtUtil.extractUsername(jwt);

                System.out.println("==================================");
                System.out.println("JWT Token Found");
                System.out.println("Username From Token : " + username);
                System.out.println("==================================");

            } catch (Exception e) {

                System.out.println("Invalid JWT Token");
                e.printStackTrace();

            }

        }

        // ==========================================
        // Authenticate User
        // ==========================================

        if (username != null &&
                SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {

            UserDetails userDetails =
                    customUserDetailService
                            .loadUserByUsername(username);

            System.out.println("==================================");
            System.out.println("User Loaded Successfully");
            System.out.println("Username : " + userDetails.getUsername());
            System.out.println("Authorities : " + userDetails.getAuthorities());
            System.out.println("==================================");

            if (jwtUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);

                System.out.println("==================================");
                System.out.println("Authentication Successful");
                System.out.println("Current User : "
                        + SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getName());

                System.out.println("Authorities : "
                        + SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getAuthorities());

                System.out.println("==================================");

            } else {

                System.out.println("JWT Validation Failed");

            }

        }

        filterChain.doFilter(request, response);

    }

}