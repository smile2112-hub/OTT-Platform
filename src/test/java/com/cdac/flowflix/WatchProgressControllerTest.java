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

import com.cdac.flowflix.controller.WatchProgressController;
import com.cdac.flowflix.dto.WatchProgressDTO;
import com.cdac.flowflix.service.WatchProgressService;

@ExtendWith(MockitoExtension.class)
class WatchProgressControllerTest {

    @InjectMocks
    private WatchProgressController controller;

    @Mock
    private WatchProgressService watchProgressService;

    @Test
    void shouldSaveProgressAndReturnResumeTime() {
        doNothing().when(watchProgressService).saveProgress(3L, 30L, 60L);
        when(watchProgressService.getResumeTime(3L)).thenReturn(30L);

        assertEquals("Progress Saved", controller.saveProgress(3L, 30L, 60L));
        assertEquals(30L, controller.resumeMovie(3L));
        verify(watchProgressService).saveProgress(3L, 30L, 60L);
    }

    @Test
    void shouldReturnProgressLists() {
        WatchProgressDTO dto = mock(WatchProgressDTO.class);
        when(watchProgressService.continueWatching()).thenReturn(List.of(dto));
        when(watchProgressService.watchHistory()).thenReturn(List.of(dto));

        assertEquals(1, controller.continueWatching().size());
        assertEquals(1, controller.history().size());
    }
}
