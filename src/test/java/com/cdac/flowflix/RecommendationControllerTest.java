package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cdac.flowflix.controller.RecommendationController;
import com.cdac.flowflix.dto.RecommendationDTO;
import com.cdac.flowflix.service.RecommendationService;

@ExtendWith(MockitoExtension.class)
class RecommendationControllerTest {

    @InjectMocks
    private RecommendationController controller;

    @Mock
    private RecommendationService recommendationService;

    @Test
    void shouldReturnAllRecommendationLists() {
        RecommendationDTO dto = mock(RecommendationDTO.class);
        when(recommendationService.getRecommendedMovies()).thenReturn(List.of(dto));
        when(recommendationService.getTrendingMovies()).thenReturn(List.of(dto));
        when(recommendationService.getFeaturedMovies()).thenReturn(List.of(dto));
        when(recommendationService.getTopRatedMovies()).thenReturn(List.of(dto));
        when(recommendationService.getMostViewedMovies()).thenReturn(List.of(dto));
        when(recommendationService.getMoviesByGenre("Action")).thenReturn(List.of(dto));

        assertEquals(1, controller.getRecommendedMovies().size());
        assertEquals(1, controller.getTrendingMovies().size());
        assertEquals(1, controller.getFeaturedMovies().size());
        assertEquals(1, controller.getTopRatedMovies().size());
        assertEquals(1, controller.getMostViewedMovies().size());
        assertEquals(1, controller.getMoviesByGenre("Action").size());
    }
}
