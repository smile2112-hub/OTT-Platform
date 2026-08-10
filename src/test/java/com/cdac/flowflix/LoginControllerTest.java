/**
 * Author:  Mahesh Ganesh Kajale
 * Email:   maheshkajale2112@gmail.com
 * Project: FlowFlix OTT Platform
 */
package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.cdac.flowflix.controller.LoginController;
import com.cdac.flowflix.dto.LoginDTO;
import com.cdac.flowflix.model.Login;
import com.cdac.flowflix.service.UserService;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.


    @InjectMocks
    private LoginController controller;

    @Mock
    private UserService userService;

    @Test
    void shouldReturnLoginDtoOnSuccess() {
        Login login = new Login();
        login.setUsername("joe");
        login.setPassword("pass");
        LoginDTO expected = new LoginDTO("token", "Joe", "ok");

        when(userService.generateToken(login)).thenReturn(expected);
        when(userService.isValidLogout()).thenReturn("Logged out");

        ResponseEntity<LoginDTO> loginResponse = controller.login(login);
        ResponseEntity<String> logoutResponse = controller.logout();

        assertEquals(200, loginResponse.getStatusCodeValue());
        assertSame(expected, loginResponse.getBody());
        assertEquals("Logged out", logoutResponse.getBody());
    }
}
