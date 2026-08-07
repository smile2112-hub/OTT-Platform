package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cdac.flowflix.controller.HomeController;
import com.cdac.flowflix.dto.HomeDTO;
import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.service.HomeService;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {

    @InjectMocks
    private HomeController controller;

    @Mock
    private HomeService homeService;

    @Test
    void shouldReturnHomePageAndSearchResults() {
        HomeDTO homeDTO = mock(HomeDTO.class);
        MovieDTO movieDTO = mock(MovieDTO.class);
        when(homeService.getHomePage()).thenReturn(homeDTO);
        when(homeService.movieDetails(5L)).thenReturn(movieDTO);
        when(homeService.searchMovies("test")).thenReturn(List.of(movieDTO));
        when(homeService.recommendedMovies(6L)).thenReturn(List.of(movieDTO));

        assertSame(homeDTO, controller.homePage());
        assertSame(movieDTO, controller.movieDetails(5L));
        assertEquals(1, controller.search("test").size());
        assertEquals(1, controller.recommend(6L).size());
    }
}
