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

import com.cdac.flowflix.dto.WatchlistDTO;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.model.Watchlist;
import com.cdac.flowflix.repository.MovieRepository;
import com.cdac.flowflix.repository.WatchlistRepository;
import com.cdac.flowflix.service.UserService;
import com.cdac.flowflix.serviceImpl.WatchlistServiceImpl;

@ExtendWith(MockitoExtension.class)
class WatchlistServiceImplTest {

    @InjectMocks
    private WatchlistServiceImpl watchlistService;

    @Mock
    private WatchlistRepository watchlistRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private UserService userService;

    @Test
    void shouldAddToWatchlistWhenValid() {
        User user = new User();
        Movie movie = new Movie();

        when(userService.getCurrentUser()).thenReturn(user);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(watchlistRepository.existsByUserAndMovie(user, movie)).thenReturn(false);

        String response = watchlistService.addToWatchlist(1L);

        assertEquals("Movie Added To Watchlist Successfully", response);
        verify(watchlistRepository).save(any(Watchlist.class));
    }

    @Test
    void shouldRemoveFromWatchlistWhenPresent() {
        User user = new User();
        Movie movie = new Movie();

        when(userService.getCurrentUser()).thenReturn(user);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(watchlistRepository.existsByUserAndMovie(user, movie)).thenReturn(true);

        String response = watchlistService.removeFromWatchlist(1L);

        assertEquals("Movie Removed From Watchlist Successfully", response);
        verify(watchlistRepository).deleteByUserAndMovie(user, movie);
    }

    @Test
    void shouldReturnWatchlistStateAndCounts() {
        User user = new User();
        Movie movie = new Movie();
        movie.setId(1L);
        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);
        watchlist.setMovie(movie);

        // Stub setups
        when(userService.getCurrentUser()).thenReturn(user);
        
      
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        
        when(watchlistRepository.findByUser(user)).thenReturn(List.of(watchlist));
        when(watchlistRepository.existsByUserAndMovie(user, movie)).thenReturn(true);
        when(watchlistRepository.countByUser(user)).thenReturn(2L);

        // Assertions
        assertFalse(watchlistService.getMyWatchlist().isEmpty());
        assertTrue(watchlistService.existsInWatchlist(1L)); // This will now successfully pass!
        assertEquals(2L, watchlistService.getWatchlistCount());

        String clearResponse = watchlistService.clearWatchlist();
        assertEquals("Watchlist Cleared Successfully", clearResponse);
        verify(watchlistRepository).deleteByUser(user);
    }
}
