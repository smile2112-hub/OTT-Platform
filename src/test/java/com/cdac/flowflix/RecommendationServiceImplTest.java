package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cdac.flowflix.dto.RecommendationDTO;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.repository.RecommendationRepository;
import com.cdac.flowflix.serviceImpl.RecommendationServiceImpl;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceImplTest {

    @InjectMocks
    private RecommendationServiceImpl recommendationService;

    @Mock
    private RecommendationRepository recommendationRepository;

    @Test
    void shouldReturnRecommendedMovies() {
        Movie movie = new Movie();
        movie.setActive(true);
        when(recommendationRepository.findByActiveTrue()).thenReturn(List.of(movie));

        var results = recommendationService.getRecommendedMovies();

        assertEquals(1, results.size());
    }

    @Test
    void shouldFilterTrendingAndFeaturedMoviesWhenInactive() {
        
        when(recommendationRepository.findByTrendingTrue()).thenReturn(List.of());
        when(recommendationRepository.findByFeaturedTrue()).thenReturn(List.of());
        when(recommendationRepository.findTop10ByActiveTrueOrderByRatingDesc()).thenReturn(List.of());
        when(recommendationRepository.findTop10ByActiveTrueOrderByTotalViewsDesc()).thenReturn(List.of());
        when(recommendationRepository.findByGenreIgnoreCase("Action")).thenReturn(List.of());

        assertTrue(recommendationService.getTrendingMovies().isEmpty());
        assertTrue(recommendationService.getFeaturedMovies().isEmpty());
        assertTrue(recommendationService.getTopRatedMovies().isEmpty());
        assertTrue(recommendationService.getMostViewedMovies().isEmpty());
        assertTrue(recommendationService.getMoviesByGenre("Action").isEmpty());
    }
}
