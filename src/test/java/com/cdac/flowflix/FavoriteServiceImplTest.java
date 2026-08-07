package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cdac.flowflix.model.Favorite;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.repository.FavoriteRepository;
import com.cdac.flowflix.repository.MovieRepository;
import com.cdac.flowflix.service.UserService;
import com.cdac.flowflix.serviceImpl.FavoriteServiceImpl;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceImplTest {

    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private UserService userService;

    @Test
    void shouldNotAddFavoriteWhenUserNotLoggedIn() {
        when(userService.getCurrentUser()).thenReturn(null);

        String response = favoriteService.addFavorite(1L);

        assertEquals("User Not Logged In", response);
    }

    @Test
    void shouldNotAddFavoriteWhenMovieMissing() {
        when(userService.getCurrentUser()).thenReturn(new User());
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        String response = favoriteService.addFavorite(1L);

        assertEquals("Movie Not Found", response);
    }

    @Test
    void shouldNotAddFavoriteWhenAlreadyExists() {
        User user = new User();
        Movie movie = new Movie();

        when(userService.getCurrentUser()).thenReturn(user);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(favoriteRepository.existsByUserAndMovie(user, movie)).thenReturn(true);

        String response = favoriteService.addFavorite(1L);

        assertEquals("Movie Already Added To Favorites", response);
    }

    @Test
    void shouldAddFavoriteSuccessfully() {
        User user = new User();
        Movie movie = new Movie();

        when(userService.getCurrentUser()).thenReturn(user);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(favoriteRepository.existsByUserAndMovie(user, movie)).thenReturn(false);

        String response = favoriteService.addFavorite(1L);

        assertEquals("Movie Added To Favorites", response);
        verify(favoriteRepository).save(any(Favorite.class));
    }

    @Test
    void shouldRemoveFavoriteSuccessfully() {
        User user = new User();
        Movie movie = new Movie();
        Favorite favorite = new Favorite();

        when(userService.getCurrentUser()).thenReturn(user);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(favoriteRepository.findByUserAndMovie(user, movie)).thenReturn(favorite);

        String response = favoriteService.removeFavorite(1L);

        assertEquals("Movie Removed From Favorites", response);
        verify(favoriteRepository).delete(favorite);
    }

    @Test
    void shouldReturnFalseForIsFavoriteWhenMissingUserOrMovie() {
        when(userService.getCurrentUser()).thenReturn(null);
        assertFalse(favoriteService.isFavorite(1L));

        User user = new User();
        when(userService.getCurrentUser()).thenReturn(user);
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        assertFalse(favoriteService.isFavorite(1L));
    }

    @Test
    void shouldReturnTrueForIsFavoriteWhenExists() {
        User user = new User();
        Movie movie = new Movie();
        when(userService.getCurrentUser()).thenReturn(user);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(favoriteRepository.existsByUserAndMovie(user, movie)).thenReturn(true);

        assertTrue(favoriteService.isFavorite(1L));
    }

    @Test
    void shouldReturnFavoritesCounts() {
        when(favoriteRepository.count()).thenReturn(5L);
        assertEquals(5L, favoriteService.getTotalFavorites());

        when(userService.getCurrentUser()).thenReturn(null);
        assertEquals(0L, favoriteService.getUserFavoriteCount());
    }
}
