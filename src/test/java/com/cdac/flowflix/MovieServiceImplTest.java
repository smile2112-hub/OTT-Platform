package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.Review;
import com.cdac.flowflix.repository.MovieRepository;
import com.cdac.flowflix.repository.ReviewRepository;
import com.cdac.flowflix.serviceImpl.MovieServiceImpl;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Test
    void shouldSetTotalViewsToZeroWhenSavingMovieWithNullTotalViews() {
        Movie movie = new Movie();
        movie.setName("Test Movie");
        movie.setTotalViews(null);

        when(movieRepository.save(any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Movie savedMovie = movieService.save(movie);

        assertNotNull(savedMovie.getTotalViews());
        assertEquals(0L, savedMovie.getTotalViews());
        verify(movieRepository).save(savedMovie);
    }

    @Test
    void shouldReturnTopViewedMovieDTOs() {
        Movie first = new Movie();
        first.setId(1L);
        first.setName("First");
        first.setTotalViews(100L);
        Movie second = new Movie();
        second.setId(2L);
        second.setName("Second");
        second.setTotalViews(50L);

        when(movieRepository.findTop10ByOrderByTotalViewsDesc()).thenReturn(List.of(first, second));

        var result = movieService.getTopViewedMovies();

        assertEquals(2, result.size());
        assertEquals("First", result.get(0).getName());
        assertEquals("Second", result.get(1).getName());
    }

    @Test
    void shouldRecommendMoviesByGenreExcludingSameMovie() {
        Movie base = new Movie();
        base.setId(1L);
        base.setGenre("Action");
        Movie similar = new Movie();
        similar.setId(2L);
        similar.setGenre("Action");
        Movie other = new Movie();
        other.setId(3L);
        other.setGenre("Action");

        when(movieRepository.findById(1L)).thenReturn(Optional.of(base));
        when(movieRepository.findByGenreContainingIgnoreCase("Action")).thenReturn(List.of(base, similar, other));

        var result = movieService.recommendMovies(1L);

        assertEquals(2, result.size());
        assertTrue(result.stream().noneMatch(dto -> dto.getId().equals(1L)));
    }

    @Test
    void shouldActivateAndDeactivateMovie() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setActive(false);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(movieRepository.save(any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));

        movieService.activateMovie(1L);
        assertTrue(movie.isActive());

        movieService.deactivateMovie(1L);
        assertFalse(movie.isActive());

        verify(movieRepository, times(2)).save(movie);
    }

    @Test
    void shouldUpdateMovieWithNewFields() {
        Movie original = new Movie();
        original.setId(1L);
        original.setName("Old Name");
        original.setDirector("Old Director");
        original.setGenre("Action");
        original.setFeatured(false);
        original.setTrending(false);
        original.setActive(false);

        Movie update = new Movie();
        update.setName("New Name");
        update.setDirector("New Director");
        update.setDescription("Description");
        update.setGenre("Drama");
        update.setActors("Actor A");
        update.setDistributor("Distributor");
        update.setYear(2025);
        update.setDuration(120);
        update.setRating(4.5);
        update.setFeatured(true);
        update.setTrending(true);
        update.setActive(true);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(original));
        when(movieRepository.save(any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Movie result = movieService.updateMovie(1L, update);

        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals("Drama", result.getGenre());
        assertTrue(result.isFeatured());
        assertTrue(result.isTrending());
        assertTrue(result.isActive());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingMissingMovie() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        String response = movieService.deleteMovie(1L);

        assertEquals("Movie Not Found", response);
    }

    @Test
    void shouldDeleteExistingMovie() {
        Movie movie = new Movie();
        movie.setId(1L);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        String response = movieService.deleteMovie(1L);

        assertEquals("Movie Deleted Successfully", response);
        verify(movieRepository).delete(movie);
    }

    @Test
    void shouldUpdateMovieRatingFromReviews() {
        Movie movie = new Movie();
        movie.setId(1L);

        Review review1 = new Review();
        review1.setRating(4);
        Review review2 = new Review();
        review2.setRating(5);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(reviewRepository.findByMovieOrderByReviewDateDesc(movie)).thenReturn(List.of(review1, review2));
        when(movieRepository.save(any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));

        movieService.updateMovieRating(1L);

        assertEquals(4.5, movie.getRating());
        verify(movieRepository).save(movie);
    }

    @Test
    void shouldReturnAverageRatingZeroWhenNoReviewsFound() {
        Movie movie = new Movie();
        movie.setId(1L);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(reviewRepository.findByMovieOrderByReviewDateDesc(movie)).thenReturn(List.of());

        Double averageRating = movieService.getAverageRating(1L);

        assertEquals(0.0, averageRating);
    }

    @Test
    void shouldReturnReviewCount() {
        Movie movie = new Movie();
        movie.setId(1L);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(reviewRepository.countByMovie(movie)).thenReturn(3L);

        Long count = movieService.getReviewCount(1L);

        assertEquals(3L, count);
    }
}
