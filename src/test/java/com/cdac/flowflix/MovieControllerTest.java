package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.flowflix.controller.MovieController;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.service.MovieService;
import com.cdac.flowflix.storage.StorageService;

@ExtendWith(MockitoExtension.class)
class MovieControllerTest {

    @InjectMocks
    private MovieController movieController;

    @Mock
    private MovieService movieService;

    @Mock
    private StorageService storageService;

    @Test
    void shouldCreateMovieSuccessfully() throws IOException {
        MultipartFile poster = new MockMultipartFile("poster", "poster.png", "image/png", "content".getBytes());
        MultipartFile banner = new MockMultipartFile("banner", "banner.png", "image/png", "content".getBytes());
        MultipartFile video = new MockMultipartFile("video", "video.mp4", "video/mp4", "content".getBytes());
        MultipartFile trailer = new MockMultipartFile("trailer", "trailer.mp4", "video/mp4", "content".getBytes());

        String movieJson = "{\"name\":\"Test Movie\",\"genre\":\"Action\"}";

        when(storageService.savePoster(poster)).thenReturn("poster.png");
        when(storageService.saveBanner(banner)).thenReturn("banner.png");
        when(storageService.saveVideo(video)).thenReturn("video.mp4");
        when(storageService.saveTrailer(trailer)).thenReturn("trailer.mp4");
        when(movieService.save(any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<String> response = movieController.createMovie(poster, banner, video, trailer, movieJson);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Movie Uploaded Successfully", response.getBody());
        verify(storageService).savePoster(poster);
        verify(storageService).saveVideo(video);
        verify(movieService).save(any(Movie.class));
    }

    @Test
    void shouldReturnBadRequestWhenPosterMissing() throws IOException {
        MultipartFile poster = new MockMultipartFile("poster", new byte[0]);
        MultipartFile video = new MockMultipartFile("video", "video.mp4", "video/mp4", "content".getBytes());

        ResponseEntity<String> response = movieController.createMovie(poster, null, video, null, "{}");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Poster is required", response.getBody());
    }

    @Test
    void shouldReturnBadRequestWhenVideoMissing() throws IOException {
        MultipartFile poster = new MockMultipartFile("poster", "poster.png", "image/png", "content".getBytes());
        MultipartFile video = new MockMultipartFile("video", new byte[0]);

        ResponseEntity<String> response = movieController.createMovie(poster, null, video, null, "{}");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Video is required", response.getBody());
    }
}
