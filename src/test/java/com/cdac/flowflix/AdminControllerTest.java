package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.cdac.flowflix.controller.AdminController;
import com.cdac.flowflix.dto.AdminDashboardDTO;
import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.dto.SubscriptionDTO;
import com.cdac.flowflix.dto.UserDTO;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.service.AdminService;
import com.cdac.flowflix.service.MovieService;
import com.cdac.flowflix.service.SubscriptionService;
import com.cdac.flowflix.service.UserService;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @InjectMocks
    private AdminController controller;

    @Mock
    private AdminService adminService;

    @Mock
    private MovieService movieService;

    @Mock
    private UserService userService;

    @Mock
    private SubscriptionService subscriptionService;

    @Test
    void shouldReturnDashboardAndUsers() {
        AdminDashboardDTO dashboardDTO = mock(AdminDashboardDTO.class);
        UserDTO userDTO = mock(UserDTO.class);
        when(adminService.getDashboard()).thenReturn(dashboardDTO);
        when(userService.findAllUsers()).thenReturn(List.of(userDTO));

        assertSame(dashboardDTO, controller.dashboard());
        assertEquals(1, controller.getAllUsers().size());
    }

    @Test
    void shouldManageUsersAndMovies() {
        when(userService.activateUser(1L)).thenReturn("Activated");
        when(userService.deactivateUser(1L)).thenReturn("Deactivated");
        when(userService.makeAdmin(1L)).thenReturn("Admin granted");
        when(userService.removeAdmin(1L)).thenReturn("Admin removed");
        when(userService.deleteUser(1L)).thenReturn("Deleted");
        when(movieService.findAll()).thenReturn(List.of(mock(MovieDTO.class)));
        doNothing().when(movieService).activateMovie(2L);
        doNothing().when(movieService).deactivateMovie(2L);
        doNothing().when(movieService).featureMovie(2L);
        doNothing().when(movieService).unFeatureMovie(2L);
        doNothing().when(movieService).makeTrending(2L);
        doNothing().when(movieService).removeTrending(2L);
        when(movieService.updateMovie(eq(2L), any(Movie.class))).thenReturn(mock(Movie.class));
        when(movieService.deleteMovie(2L)).thenReturn("Deleted movie");
        when(movieService.getMostViewedMovie()).thenReturn("Most viewed");
        when(movieService.getTopViewedMovies()).thenReturn(List.of(mock(MovieDTO.class)));
        when(movieService.getTotalViews()).thenReturn(100L);
        when(subscriptionService.getAllSubscriptions()).thenReturn(List.of(mock(SubscriptionDTO.class)));

        assertEquals("Activated", controller.activateUser(1L).getBody());
        assertEquals("Deactivated", controller.deactivateUser(1L).getBody());
        assertEquals("Admin granted", controller.makeAdmin(1L).getBody());
        assertEquals("Admin removed", controller.removeAdmin(1L).getBody());
        assertEquals("Deleted", controller.deleteUser(1L).getBody());
        assertEquals(1, controller.getAllMovies().size());
        assertEquals("Movie Activated Successfully", controller.activateMovie(2L).getBody());
        assertEquals("Movie Deactivated Successfully", controller.deactivateMovie(2L).getBody());
        assertEquals("Movie Featured Successfully", controller.featureMovie(2L).getBody());
        assertEquals("Movie Unfeatured Successfully", controller.unFeatureMovie(2L).getBody());
        assertEquals("Movie Marked As Trending", controller.makeTrending(2L).getBody());
        assertEquals("Trending Removed", controller.removeTrending(2L).getBody());
        assertNotNull(controller.updateMovie(2L, new Movie()).getBody());
        assertEquals("Deleted movie", controller.deleteMovie(2L).getBody());
        assertEquals("Most viewed", controller.getMostViewedMovie());
        assertEquals(1, controller.getTopViewedMovies().size());
        assertEquals(100L, controller.getTotalViews());
        assertEquals(1, controller.getAllSubscriptions().size());
        assertEquals("Expired Subscriptions Updated Successfully", controller.updateExpiredSubscriptions().getBody());
    }
}
