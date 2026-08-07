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

import com.cdac.flowflix.dto.WatchHistoryDTO;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.model.WatchHistory;
import com.cdac.flowflix.repository.MovieRepository;
import com.cdac.flowflix.repository.WatchHistoryRepository;
import com.cdac.flowflix.service.MovieService;
import com.cdac.flowflix.service.UserService;
import com.cdac.flowflix.serviceImpl.WatchHistoryServiceImpl;

@ExtendWith(MockitoExtension.class)
class WatchHistoryServiceImplTest {

    @InjectMocks
    private WatchHistoryServiceImpl watchHistoryService;

    @Mock
    private WatchHistoryRepository watchHistoryRepository;

    @Mock
    private UserService userService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieService movieService;

    @Test
    void shouldSaveWatchHistoryAndIncrementViews() {
        User user = new User();
        Movie movie = new Movie();
        movie.setId(1L);
        WatchHistory history = new WatchHistory();

        when(userService.getCurrentUser()).thenReturn(user);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(watchHistoryRepository.findByUserAndMovie(user, movie)).thenReturn(history);
        when(watchHistoryRepository.save(any(WatchHistory.class))).thenAnswer(invocation -> invocation.getArgument(0));
        doNothing().when(movieService).incrementViews(1L);

        watchHistoryService.saveWatchHistory(1L, 50L);

        verify(watchHistoryRepository).save(any(WatchHistory.class));
        verify(movieService).incrementViews(1L);
    }

    @Test
    void shouldReturnHistoryLists() {
        User user = new User();
        WatchHistory history = new WatchHistory();
        when(userService.getCurrentUser()).thenReturn(user);
        when(watchHistoryRepository.findByUserOrderByWatchedAtDesc(user)).thenReturn(List.of(history));
        when(watchHistoryRepository.findByUserAndWatchPositionGreaterThanOrderByWatchedAtDesc(user, 0L)).thenReturn(List.of(history));
        when(watchHistoryRepository.findTop10ByUserOrderByWatchedAtDesc(user)).thenReturn(List.of(history));

        assertEquals(1, watchHistoryService.getWatchHistory().size());
        assertEquals(1, watchHistoryService.getContinueWatching().size());
        assertEquals(1, watchHistoryService.getRecentlyWatched().size());
    }

    @Test
    void shouldClearWatchHistory() {
        User user = new User();
        when(userService.getCurrentUser()).thenReturn(user);

        watchHistoryService.clearWatchHistory();

        verify(watchHistoryRepository).deleteByUser(user);
    }
}
