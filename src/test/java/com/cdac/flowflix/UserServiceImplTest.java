package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cdac.flowflix.dto.JWTLogin;
import com.cdac.flowflix.dto.LoginDTO;
import com.cdac.flowflix.model.Login;
import com.cdac.flowflix.model.Role;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.repository.UserRepository;
import com.cdac.flowflix.security.JwtUtil;
import com.cdac.flowflix.serviceImpl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void cleanup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldReturnValidWhenUserDataIsValid() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("secret");
        user.setNameSurname("Test User");
        user.setEmail("test@example.com");

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);

        String result = userService.validateUser(user);

        assertEquals("valid", result);
    }

    @Test
    void shouldReturnUsernameRequiredWhenUsernameMissing() {
        User user = new User();
        user.setPassword("secret");
        user.setNameSurname("Test User");
        user.setEmail("test@example.com");

        String result = userService.validateUser(user);

        assertEquals("Username Required", result);
    }

    @Test
    void shouldReturnInvalidEmailWhenEmailMalformed() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("secret");
        user.setNameSurname("Test User");
        user.setEmail("bad-email");

        String result = userService.validateUser(user);

        assertEquals("Invalid Email", result);
    }

    @Test
    void shouldSaveUserWithEncodedPassword() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("secret");

        when(passwordEncoder.encode("secret")).thenReturn("encoded-secret");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User saved = userService.save(user);

        assertNotNull(saved);
        assertEquals("encoded-secret", saved.getPassword());
        verify(userRepository).save(saved);
    }

    @Test
    void shouldGenerateTokenWhenCredentialsValid() {
        Login login = new Login();
        login.setUsername("testuser");
        login.setPassword("secret");

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("encoded-secret");
        user.setNameSurname("Test User");
        user.setRole(Role.USER);
        user.setDeleted(false);

        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches("secret", "encoded-secret")).thenReturn(true);
        when(jwtUtil.generateToken(any(JWTLogin.class))).thenReturn("jwt-token");

        LoginDTO dto = userService.generateToken(login);

        assertEquals("jwt-token", dto.getToken());
        assertEquals("Test User", dto.getUserNameSurname());
        assertEquals("no", dto.getMessageInvalidUsernameOrPassword());
    }

    @Test
    void shouldDeactivateAndActivateUser() {
        User user = new User();
        user.setId(1L);
        user.setDeleted(false);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String deactivateResponse = userService.deactivateUser(1L);
        assertEquals("User Deactivated Successfully", deactivateResponse);
        assertTrue(user.isDeleted());

        String activateResponse = userService.activateUser(1L);
        assertEquals("User Activated Successfully", activateResponse);
        assertFalse(user.isDeleted());
    }

    @Test
    void shouldRemoveAdminWhenMoreThanOneAdminAndNotSelf() {
        User targetUser = new User();
        targetUser.setId(1L);
        targetUser.setUsername("admin2");
        targetUser.setRole(Role.ADMIN);

        User currentUser = new User();
        currentUser.setId(2L);
        currentUser.setUsername("admin1");
        currentUser.setRole(Role.ADMIN);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                currentUser.getUsername(), null, List.of(new SimpleGrantedAuthority("ROLE_" + Role.ADMIN.name())));
        SecurityContextHolder.getContext().setAuthentication(auth);

        when(userRepository.findById(1L)).thenReturn(Optional.of(targetUser));
        when(userRepository.findByUsername("admin1")).thenReturn(currentUser);
        when(userRepository.countByRole(Role.ADMIN)).thenReturn(2L);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String response = userService.removeAdmin(1L);

        assertEquals("Admin Converted To User", response);
        assertEquals(Role.USER, targetUser.getRole());
    }

    @Test
    void shouldDeleteUserWhenNotSelfAndNotLastAdmin() {
        User targetUser = new User();
        targetUser.setId(1L);
        targetUser.setUsername("user1");
        targetUser.setRole(Role.USER);

        User currentUser = new User();
        currentUser.setId(2L);
        currentUser.setUsername("admin1");
        currentUser.setRole(Role.ADMIN);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                currentUser.getUsername(), null, List.of(new SimpleGrantedAuthority("ROLE_" + Role.ADMIN.name())));
        SecurityContextHolder.getContext().setAuthentication(auth);

        when(userRepository.findById(1L)).thenReturn(Optional.of(targetUser));
        when(userRepository.findByUsername("admin1")).thenReturn(currentUser);

        String response = userService.deleteUser(1L);

        assertEquals("User Deleted Successfully", response);
        verify(userRepository).delete(targetUser);
    }
}