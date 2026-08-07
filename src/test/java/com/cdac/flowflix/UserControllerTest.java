package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.cdac.flowflix.controller.UserController;
import com.cdac.flowflix.dto.ProfileDTO;
import com.cdac.flowflix.model.Role;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @Test
    void shouldRegisterUserWhenValid() {
        User user = new User();
        when(userService.validateUser(user)).thenReturn("valid");
        
        // Fix: Changed doNothing() to thenReturn() because save() is not a void method
        when(userService.save(user)).thenReturn(user);

        ResponseEntity<String> response = controller.register(user);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User Registered Successfully", response.getBody());
        assertEquals(Role.USER, user.getRole());
        assertFalse(user.isDeleted());
        verify(userService).save(user);
    }

    @Test
    void shouldReturnBadRequestWhenRegisterInvalid() {
        User user = new User();
        when(userService.validateUser(user)).thenReturn("Invalid email");

        ResponseEntity<String> response = controller.register(user);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid email", response.getBody());
    }

    @Test
    void shouldReturnProfileAndUpdateValues() {
        ProfileDTO profileDTO = mock(ProfileDTO.class);
        when(userService.getMyProfile()).thenReturn(profileDTO);
        when(userService.updateProfile(profileDTO)).thenReturn("Updated");
        when(userService.changePassword("old", "new")).thenReturn("Password changed");
        when(userService.uploadProfilePicture("pic.png")).thenReturn("Uploaded");
        when(userService.removeProfilePicture()).thenReturn("Removed");
        when(userService.deleteMyAccount()).thenReturn("Deleted");

        assertSame(profileDTO, controller.getMyProfile().getBody());
        assertEquals("Updated", controller.updateProfile(profileDTO).getBody());
        assertEquals("Password changed", controller.changePassword("old", "new").getBody());
        assertEquals("Uploaded", controller.uploadProfilePicture("pic.png").getBody());
        assertEquals("Removed", controller.removeProfilePicture().getBody());
        assertEquals("Deleted", controller.deleteMyAccount().getBody());
    }

    @Test
    void shouldReturnNotFoundWhenProfileMissing() {
        when(userService.getMyProfile()).thenReturn(null);

        ResponseEntity<ProfileDTO> response = controller.getMyProfile();

        assertEquals(404, response.getStatusCodeValue());
    }
}
