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

import com.cdac.flowflix.controller.WatchHistoryController;
import com.cdac.flowflix.dto.WatchHistoryDTO;
import com.cdac.flowflix.service.WatchHistoryService;

@ExtendWith(MockitoExtension.class)
class WatchHistoryControllerTest {

    @InjectMocks
    private WatchHistoryController controller;

    @Mock
    private WatchHistoryService watchHistoryService;

    @Test
    void shouldSaveWatchHistoryAndClear() {
        doNothing().when(watchHistoryService).saveWatchHistory(2L, 100L);
        doNothing().when(watchHistoryService).clearWatchHistory();

        ResponseEntity<String> saveResponse = controller.watchMovie(2L, 100L);
        ResponseEntity<String> clearResponse = controller.clearWatchHistory();

        assertEquals("Watch History Saved Successfully", saveResponse.getBody());
        
        verify(watchHistoryService).saveWatchHistory(2L, 100L);
        verify(watchHistoryService).clearWatchHistory();
    }

    @Test
    void shouldReturnWatchHistoryLists() {
        WatchHistoryDTO dto = mock(WatchHistoryDTO.class);
        List<WatchHistoryDTO> dtoList = List.of(dto);

        doReturn(dtoList).when(watchHistoryService).getWatchHistory();
        doReturn(dtoList).when(watchHistoryService).getContinueWatching();
        doReturn(dtoList).when(watchHistoryService).getRecentlyWatched();

        assertEquals(1, controller.getWatchHistory().size());
        assertEquals(1, controller.getContinueWatching().size());
        assertEquals(1, controller.getRecentlyWatched().size());
    }
}