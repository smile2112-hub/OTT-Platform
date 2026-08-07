package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cdac.flowflix.controller.ContinueWatchingController;
import com.cdac.flowflix.dto.WatchProgressDTO;
import com.cdac.flowflix.service.WatchProgressService;

@ExtendWith(MockitoExtension.class)
class ContinueWatchingControllerTest {

    @InjectMocks
    private ContinueWatchingController controller;

    @Mock
    private WatchProgressService watchProgressService;

    @Test
    void shouldReturnContinueWatchingMovies() {
        WatchProgressDTO dto = mock(WatchProgressDTO.class);
        when(watchProgressService.continueWatching()).thenReturn(List.of(dto));

        List<WatchProgressDTO> result = controller.continueWatching();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertSame(dto, result.get(0));
    }
}
