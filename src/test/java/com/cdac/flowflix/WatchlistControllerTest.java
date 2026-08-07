package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.cdac.flowflix.controller.WatchlistController;
import com.cdac.flowflix.dto.WatchlistDTO;
import com.cdac.flowflix.service.WatchlistService;

@ExtendWith(MockitoExtension.class)
class WatchlistControllerTest {

    @InjectMocks
    private WatchlistController controller;

    @Mock
    private WatchlistService watchlistService;

    @Test
    void shouldAddAndRemoveMovies() {
        when(watchlistService.addToWatchlist(20L)).thenReturn("Added");
        when(watchlistService.removeFromWatchlist(20L)).thenReturn("Removed");

        ResponseEntity<String> addResponse = controller.addMovie(20L);
        ResponseEntity<String> removeResponse = controller.removeMovie(20L);

        assertEquals("Added", addResponse.getBody());
        assertEquals("Removed", removeResponse.getBody());
    }

    @Test
    void shouldReturnWatchlistAndStatusValues() {
        WatchlistDTO dto = mock(WatchlistDTO.class);
        when(watchlistService.getMyWatchlist()).thenReturn(List.of(dto));
        when(watchlistService.existsInWatchlist(30L)).thenReturn(false);
        when(watchlistService.clearWatchlist()).thenReturn("Cleared");
        when(watchlistService.getWatchlistCount()).thenReturn(7L);

        ResponseEntity<List<WatchlistDTO>> listResponse = controller.getMyWatchlist();
        ResponseEntity<Boolean> existsResponse = controller.exists(30L);
        ResponseEntity<String> clearResponse = controller.clearWatchlist();
        ResponseEntity<Long> countResponse = controller.getWatchlistCount();

        assertEquals(1, listResponse.getBody().size());
        assertFalse(existsResponse.getBody());
        assertEquals("Cleared", clearResponse.getBody());
        assertEquals(7L, countResponse.getBody());
    }
}
