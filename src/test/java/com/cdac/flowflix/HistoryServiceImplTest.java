package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cdac.flowflix.dto.HistoryDTO;
import com.cdac.flowflix.model.History;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.repository.HistoryRepository;
import com.cdac.flowflix.repository.MovieRepository;
import com.cdac.flowflix.service.HistoryService;
import com.cdac.flowflix.service.UserService;
import com.cdac.flowflix.serviceImpl.HistoryServiceImpl;

@ExtendWith(MockitoExtension.class)
class HistoryServiceImplTest {

    @InjectMocks
    private HistoryServiceImpl historyService;

    @Mock
    private HistoryRepository historyRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private UserService userService;

    @Test
    void shouldNotSaveHistoryWithoutUser() {
        when(userService.getCurrentUser()).thenReturn(null);

        String response = historyService.saveHistory(1L, 10L, 100L);

        assertEquals("User Not Logged In", response);
    }

    @Test
    void shouldSaveNewHistoryAndCompleteWhenPercentageHigh() {
        User user = new User();
        user.setId(1L);
        Movie movie = new Movie();
        movie.setId(1L);

        when(userService.getCurrentUser()).thenReturn(user);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(historyRepository.findByUserAndMovie(user, movie)).thenReturn(null);
        when(historyRepository.save(any(History.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String response = historyService.saveHistory(1L, 95L, 100L);

        assertEquals("Watch History Saved Successfully", response);
        verify(historyRepository).save(any(History.class));
    }

    @Test
    void shouldReturnHistoryListsAndCounts() {
        User user = new User();
        Movie movie = new Movie();
        movie.setId(1L);
        History history = new History();
        history.setUser(user);
        history.setMovie(movie);
        history.setCompleted(false);
        history.setLastWatched(LocalDateTime.now());

        // Stubbing setups
        when(userService.getCurrentUser()).thenReturn(user);
        
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie)); 
        
        when(historyRepository.findByUserOrderByLastWatchedDesc(user)).thenReturn(List.of(history));
        when(historyRepository.findByUserAndCompletedFalseOrderByLastWatchedDesc(user)).thenReturn(List.of(history));
        when(historyRepository.findByUserAndCompletedTrueOrderByLastWatchedDesc(user)).thenReturn(List.of());
        when(historyRepository.findByUserAndMovie(user, movie)).thenReturn(history);
        when(historyRepository.existsByUserAndMovie(user, movie)).thenReturn(true);
        when(historyRepository.countByUser(user)).thenReturn(1L);

        // Assertions
        assertFalse(historyService.getMyHistory().isEmpty());
        assertFalse(historyService.getContinueWatching().isEmpty());
        assertTrue(historyService.getCompletedMovies().isEmpty());
        assertNotNull(historyService.getMovieHistory(1L)); // This will now pass successfully!
        assertTrue(historyService.hasHistory(1L));
        assertEquals(1L, historyService.getHistoryCount());
    }
}
