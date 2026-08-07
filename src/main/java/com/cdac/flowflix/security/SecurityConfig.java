package com.cdac.flowflix.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http

                // Disable CSRF and enable CORS
                .csrf().disable()
                .cors()
                .and()

                // Stateless Session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()

                .authorizeRequests()

                // ==========================================
                // PUBLIC ENDPOINTS
                // ==========================================

                .antMatchers(
                        "/api/login",
                        "/api/logout",
                        "/api/user/register",
                        "/health",
                        "/api/auth/**",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/api-docs/**",
                        "/actuator/health",
                        "/actuator/info",
                        "/actuator/**")
                .permitAll()

                // ==========================================
                // PUBLIC MOVIE GET APIs
                // ==========================================

                .antMatchers(HttpMethod.GET, "/api/movie/**")
                .permitAll()

                // ==========================================
                // USER APIs
                // ==========================================

                .antMatchers("/api/user/**")
                .hasAnyRole("USER", "ADMIN")

                // ==========================================
                // ADMIN APIs
                // ==========================================

                .antMatchers("/api/admin/**")
                .hasRole("ADMIN")

                // ==========================================
                // MOVIE VIEW ENDPOINT (authenticated users)
                // ==========================================

                .antMatchers(HttpMethod.PUT, "/api/movie/view/**")
                .hasAnyRole("USER", "ADMIN")

                // ==========================================
                // MOVIE CREATE/UPDATE/DELETE
                // ==========================================

                .antMatchers(HttpMethod.POST, "/api/movie/**")
                .hasRole("ADMIN")

                .antMatchers(HttpMethod.PUT, "/api/movie/**")
                .hasRole("ADMIN")

                .antMatchers(HttpMethod.DELETE, "/api/movie/**")
                .hasRole("ADMIN")

                // Everything else
                .anyRequest()
                .authenticated();

        http.addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

        @Bean
        public WebSecurityCustomizer webSecurityCustomizer() {
                return (web) -> web.ignoring().antMatchers("/actuator/**");
        }
}