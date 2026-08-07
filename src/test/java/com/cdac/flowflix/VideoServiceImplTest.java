package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.repository.MovieRepository;
import com.cdac.flowflix.serviceImpl.VideoServiceImpl;

@ExtendWith(MockitoExtension.class)
class VideoServiceImplTest {

    @InjectMocks
    private VideoServiceImpl videoService;

    @Mock
    private MovieRepository movieRepository;

    @Test
    void shouldReturnNullWhenMovieNotFound() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        assertNull(videoService.getVideoPath(1L));
    }

    @Test
    void shouldReturnVideoPathWhenMovieFound() {
        Movie movie = new Movie();
        movie.setVideo("video.mp4");
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        assertEquals("video.mp4", videoService.getVideoPath(1L));
    }
}
