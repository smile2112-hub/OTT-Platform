package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cdac.flowflix.dto.AdminDashboardDTO;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.repository.FavoriteRepository;
import com.cdac.flowflix.repository.MovieRepository;
import com.cdac.flowflix.repository.ReviewRepository;
import com.cdac.flowflix.repository.UserRepository;
import com.cdac.flowflix.service.MovieService;
import com.cdac.flowflix.serviceImpl.AdminServiceImpl;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MovieService movieService;

    @Test
    void shouldReturnDashboardMetrics() {
        when(userRepository.count()).thenReturn(10L);
        when(movieRepository.count()).thenReturn(20L);
        when(movieRepository.countByActiveTrue()).thenReturn(5L);
        when(movieRepository.countByFeaturedTrue()).thenReturn(3L);
        when(movieRepository.findByTrendingTrue()).thenReturn(List.of(new Movie(), new Movie()));
        when(favoriteRepository.count()).thenReturn(7L);
        when(reviewRepository.count()).thenReturn(15L);
        when(movieService.getTotalViews()).thenReturn(500L);
        when(movieService.getMostViewedMovie()).thenReturn("Top Movie");
        when(movieService.getMostViewedMovies()).thenReturn(List.of());

        AdminDashboardDTO dto = adminService.getDashboard();

        assertNotNull(dto);
        assertEquals(10L, dto.getTotalUsers());
        assertEquals(20L, dto.getTotalMovies());
        assertEquals(5L, dto.getActiveMovies());
        assertEquals(3L, dto.getFeaturedMovies());
        assertEquals(2, dto.getTrendingMovies());
        assertEquals(7L, dto.getTotalFavorites());
        assertEquals(15L, dto.getTotalReviews());
        assertEquals(500L, dto.getTotalViews());
        assertEquals("Top Movie", dto.getMostViewedMovie());
    }

    @Test
    void shouldReturnMostViewedMovieEntity() {
        Movie movie = new Movie();
        movie.setId(42L);
        when(movieRepository.findTopByOrderByTotalViewsDesc()).thenReturn(movie);

        Movie result = adminService.getMostViewedMovieEntity();

        assertSame(movie, result);
    }
}
