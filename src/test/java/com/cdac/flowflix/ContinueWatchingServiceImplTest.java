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
import com.cdac.flowflix.service.WatchProgressService;
import com.cdac.flowflix.serviceImpl.ContinueWatchingServiceImpl;

@ExtendWith(MockitoExtension.class)
class ContinueWatchingServiceImplTest {

    @InjectMocks
    private ContinueWatchingServiceImpl continueWatchingService;

    @Mock
    private WatchProgressService watchProgressService;

    @Test
    void shouldReturnContinueWatchingFromWatchProgressService() {
        when(watchProgressService.continueWatching()).thenReturn(List.of(new WatchProgressDTO(), new WatchProgressDTO()));

        var results = continueWatchingService.getContinueWatching();

        assertEquals(2, results.size());
        verify(watchProgressService).continueWatching();
    }
}
