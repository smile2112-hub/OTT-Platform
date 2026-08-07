package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.service.MovieService;
import com.cdac.flowflix.serviceImpl.GenreServiceImpl;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    @InjectMocks
    private GenreServiceImpl genreService;

    @Mock
    private MovieService movieService;

    @Test
    void shouldReturnGenreMoviesUsingMovieService() {
        when(movieService.getMoviesByGenre("Action")).thenReturn(List.of(new MovieDTO()));

        var results = genreService.getMovies("Action");

        assertEquals(1, results.size());
        verify(movieService).getMoviesByGenre("Action");
    }
}
