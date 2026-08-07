package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.cdac.flowflix.controller.AuthController;
import com.cdac.flowflix.dto.LoginDTO;
import com.cdac.flowflix.model.Login;
import com.cdac.flowflix.service.UserService;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Test
    void shouldReturnTokenWhenLoginSucceeds() {
        Login login = new Login();
        login.setUsername("testuser");
        login.setPassword("secret");

        LoginDTO expected = new LoginDTO("jwt-token", "Test User", "no");
        when(userService.generateToken(login)).thenReturn(expected);

        ResponseEntity<LoginDTO> response = authController.login(login);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("jwt-token", response.getBody().getToken());
        assertEquals("no", response.getBody().getMessageInvalidUsernameOrPassword());
    }
}
