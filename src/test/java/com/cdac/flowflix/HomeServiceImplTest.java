package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cdac.flowflix.dto.HomeDTO;
import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.service.MovieService;
import com.cdac.flowflix.service.WatchProgressService;
import com.cdac.flowflix.serviceImpl.HomeServiceImpl;

@ExtendWith(MockitoExtension.class)
class HomeServiceImplTest {

    @InjectMocks
    private HomeServiceImpl homeService;

    @Mock
    private MovieService movieService;

    @Mock
    private WatchProgressService watchProgressService;

    @Test
    void shouldBuildHomePageDto() {
        when(movieService.getFeaturedMovies()).thenReturn(List.of(new MovieDTO()));
        when(movieService.getTrendingMovies()).thenReturn(List.of(new MovieDTO()));
        when(movieService.getLatestMovies()).thenReturn(List.of(new MovieDTO()));
        when(watchProgressService.continueWatching()).thenReturn(List.of());

        HomeDTO dto = homeService.getHomePage();

        assertNotNull(dto);
        assertEquals(1, dto.getFeaturedMovies().size());
        assertEquals(1, dto.getTrendingMovies().size());
        assertEquals(1, dto.getLatestMovies().size());
        assertTrue(dto.getContinueWatching().isEmpty());
    }

    @Test
    void shouldReturnMovieDetailsAndSearchAndRecommendations() {
        MovieDTO movieDTO = new MovieDTO();
        when(movieService.findOne(1L)).thenReturn(new com.cdac.flowflix.model.Movie());
        when(movieService.searchMovies("test")).thenReturn(List.of(movieDTO));
        when(movieService.recommendMovies(1L)).thenReturn(List.of(movieDTO));

        assertNotNull(homeService.movieDetails(1L));
        assertEquals(1, homeService.searchMovies("test").size());
        assertEquals(1, homeService.recommendedMovies(1L).size());
    }
}
