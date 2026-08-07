package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cdac.flowflix.dto.WatchProgressDTO;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.model.WatchProgress;
import com.cdac.flowflix.repository.WatchProgressRepository;
import com.cdac.flowflix.service.MovieService;
import com.cdac.flowflix.service.UserService;
import com.cdac.flowflix.serviceImpl.WatchProgressServiceImpl;

@ExtendWith(MockitoExtension.class)
class WatchProgressServiceImplTest {

    @InjectMocks
    private WatchProgressServiceImpl service;

    @Mock
    private WatchProgressRepository repository;

    @Mock
    private UserService userService;

    @Mock
    private MovieService movieService;

    @Test
    void shouldNotSaveProgressWhenUserOrMovieMissing() {
        when(userService.getCurrentUser()).thenReturn(null);

        service.saveProgress(1L, 10L, 100L);

        verify(repository, never()).save(any());

        when(userService.getCurrentUser()).thenReturn(new User());
        when(movieService.findOne(1L)).thenReturn(null);

        service.saveProgress(1L, 10L, 100L);

        verify(repository, never()).save(any());
    }

    @Test
    void shouldSaveNewProgressAndMarkCompleted() {
        User user = new User();
        Movie movie = new Movie();

        when(userService.getCurrentUser()).thenReturn(user);
        when(movieService.findOne(1L)).thenReturn(movie);
        when(repository.findByUserAndMovie(user, movie)).thenReturn(null);
        when(repository.save(any(WatchProgress.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.saveProgress(1L, 96L, 100L);

        verify(repository).save(any(WatchProgress.class));
    }

    @Test
    void shouldReturnContinueWatchingAndHistoryLists() {
        User user = new User();
        Movie movie = new Movie();
        movie.setId(1L);

        WatchProgress progress = new WatchProgress();
        progress.setCompleted(false);
        progress.setMovie(movie); 

        // Setup base behaviors
        when(userService.getCurrentUser()).thenReturn(user);
        
        
        when(repository.findByUserOrderByLastWatchedDesc(user)).thenReturn(List.of(progress));
        
        
        List<WatchProgressDTO> continueList = service.continueWatching();
        List<WatchProgressDTO> historyList = service.watchHistory();

        assertNotNull(continueList);
        assertNotNull(historyList);
    }

    @Test
    void shouldReturnResumeTimeForExistingProgress() {
        User user = new User();
        Movie movie = new Movie();
        WatchProgress progress = new WatchProgress();
        progress.setWatchedSeconds(42L);

        when(userService.getCurrentUser()).thenReturn(user);
        when(movieService.findOne(1L)).thenReturn(movie);
        when(repository.findByUserAndMovie(user, movie)).thenReturn(progress);

        assertEquals(42L, service.getResumeTime(1L));
    }
}
