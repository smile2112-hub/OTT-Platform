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
import com.cdac.flowflix.serviceImpl.BannerServiceImpl;

@ExtendWith(MockitoExtension.class)
class BannerServiceImplTest {

    @InjectMocks
    private BannerServiceImpl bannerService;

    @Mock
    private MovieService movieService;

    @Test
    void shouldReturnBannerMoviesFromMovieService() {
        when(movieService.getFeaturedMovies()).thenReturn(List.of(new MovieDTO(), new MovieDTO()));

        List<MovieDTO> results = bannerService.getBannerMovies();

        assertEquals(2, results.size());
        verify(movieService).getFeaturedMovies();
    }
}
