package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import com.cdac.flowflix.controller.VideoController;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.service.MovieService;

@ExtendWith(MockitoExtension.class)
class VideoControllerTest {

    @InjectMocks
    private VideoController controller;

    @Mock
    private MovieService movieService;

    @Test
    void shouldReturnNotFoundWhenMovieMissing() {
        when(movieService.findOne(1L)).thenReturn(null);

        ResponseEntity<InputStreamResource> response = controller.streamVideo(1L, null);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void shouldReturnNotFoundWhenVideoMissing() {
        Movie movie = new Movie();
        movie.setVideo("");
        when(movieService.findOne(1L)).thenReturn(movie);

        ResponseEntity<InputStreamResource> response = controller.streamVideo(1L, null);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void shouldStreamVideoWhenFileExists() throws IOException {
        Path videos = Paths.get("uploads/videos");
        Files.createDirectories(videos);
        Path file = videos.resolve("test-video.mp4");
        Files.write(file, new byte[] {0, 1, 2, 3});

        try {
            Movie movie = new Movie();
            movie.setVideo("test-video.mp4");
            when(movieService.findOne(2L)).thenReturn(movie);

            ResponseEntity<InputStreamResource> response = controller.streamVideo(2L, null);

            assertEquals(200, response.getStatusCodeValue());
            assertEquals("bytes", response.getHeaders().getFirst("Accept-Ranges"));
            assertEquals(4L, response.getHeaders().getContentLength());
            assertNotNull(response.getBody());
        } finally {
            Files.deleteIfExists(file);
        }
    }
}
