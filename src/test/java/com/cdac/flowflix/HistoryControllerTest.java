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

import com.cdac.flowflix.controller.HistoryController;
import com.cdac.flowflix.dto.HistoryDTO;
import com.cdac.flowflix.service.HistoryService;

@ExtendWith(MockitoExtension.class)
class HistoryControllerTest {

    @InjectMocks
    private HistoryController controller;

    @Mock
    private HistoryService historyService;

    @Test
    void shouldSaveAndReturnHistoryDetails() {
        HistoryDTO dto = mock(HistoryDTO.class);
        when(historyService.saveHistory(1L, 50L, 100L)).thenReturn("Saved");
        when(historyService.getMyHistory()).thenReturn(List.of(dto));
        when(historyService.getContinueWatching()).thenReturn(List.of(dto));
        when(historyService.getCompletedMovies()).thenReturn(List.of(dto));
        when(historyService.getMovieHistory(1L)).thenReturn(dto);
        when(historyService.deleteHistory(1L)).thenReturn("Deleted");
        when(historyService.clearHistory()).thenReturn("Cleared");
        when(historyService.hasHistory(1L)).thenReturn(true);
        when(historyService.getHistoryCount()).thenReturn(2L);

        ResponseEntity<String> saveResponse = controller.saveHistory(1L, 50L, 100L);
        assertEquals("Saved", saveResponse.getBody());
        assertEquals(1, controller.getMyHistory().size());
        assertEquals(1, controller.continueWatching().size());
        assertEquals(1, controller.completedMovies().size());
        assertSame(dto, controller.getMovieHistory(1L));
        assertEquals("Deleted", controller.deleteHistory(1L).getBody());
        assertEquals("Cleared", controller.clearHistory().getBody());
        assertTrue(controller.hasHistory(1L).getBody());
        assertEquals(2L, controller.historyCount().getBody());
    }
}
