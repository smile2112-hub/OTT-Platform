package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.cdac.flowflix.controller.FileController;
import com.cdac.flowflix.storage.StorageService;

@ExtendWith(MockitoExtension.class)
class FileControllerTest {

    @InjectMocks
    private FileController controller;

    @Mock
    private StorageService storageService;

    @Test
    void shouldReturnPosterBannerTrailerAndVideoResources() throws IOException {
        when(storageService.getPoster("poster.png")).thenReturn(new byte[] {1, 2});
        when(storageService.getBanner("banner.png")).thenReturn(new byte[] {3, 4});
        when(storageService.getVideo("video.mp4")).thenReturn(new byte[] {5, 6});
        when(storageService.getTrailer("trailer.mp4")).thenReturn(new byte[] {7, 8});

        ResponseEntity<Resource> posterResponse = controller.getPoster("poster.png");
        ResponseEntity<Resource> bannerResponse = controller.getBanner("banner.png");
        ResponseEntity<Resource> videoResponse = controller.getVideo("video.mp4");
        ResponseEntity<Resource> trailerResponse = controller.getTrailer("trailer.mp4");

        assertEquals(MediaType.IMAGE_PNG, posterResponse.getHeaders().getContentType());
        assertEquals(MediaType.IMAGE_PNG, bannerResponse.getHeaders().getContentType());
        assertEquals(MediaType.parseMediaType("video/mp4"), videoResponse.getHeaders().getContentType());
        assertEquals(MediaType.parseMediaType("video/mp4"), trailerResponse.getHeaders().getContentType());
        assertNotNull(posterResponse.getBody());
        assertNotNull(bannerResponse.getBody());
        assertNotNull(videoResponse.getBody());
        assertNotNull(trailerResponse.getBody());
    }
}
