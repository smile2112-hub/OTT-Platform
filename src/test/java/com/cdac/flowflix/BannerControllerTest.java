package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cdac.flowflix.controller.BannerController;
import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.service.MovieService;

@ExtendWith(MockitoExtension.class)
class BannerControllerTest {

    @InjectMocks
    private BannerController controller;

    @Mock
    private MovieService movieService;

    @Test
    void shouldReturnBannerMovies() {
        MovieDTO movieDTO = mock(MovieDTO.class);
        when(movieService.getFeaturedMovies()).thenReturn(List.of(movieDTO));

        List<MovieDTO> result = controller.getBannerMovies();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertSame(movieDTO, result.get(0));
    }
}
