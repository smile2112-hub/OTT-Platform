package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.Review;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.repository.MovieRepository;
import com.cdac.flowflix.repository.ReviewRepository;
import com.cdac.flowflix.service.UserService;
import com.cdac.flowflix.serviceImpl.ReviewServiceImpl;
import com.cdac.flowflix.dto.ReviewDTO;
import com.cdac.flowflix.model.Role;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private UserService userService;

    @AfterEach
    void cleanup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldReturnNotLoggedInWhenUserMissingOnAddReview() {
        when(userService.getCurrentUser()).thenReturn(null);

        String response = reviewService.addReview(1L, 4, "Nice movie");

        assertEquals("User Not Logged In", response);
    }

    @Test
    void shouldReturnMovieNotFoundWhenMovieMissingOnAddReview() {
        User user = new User();
        user.setId(1L);
        when(userService.getCurrentUser()).thenReturn(user);
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        String response = reviewService.addReview(1L, 4, "Nice movie");

        assertEquals("Movie Not Found", response);
    }

    @Test
    void shouldReturnAlreadyReviewedWhenReviewExists() {
        User user = new User();
        user.setId(1L);
        Movie movie = new Movie();
        movie.setId(1L);

        when(userService.getCurrentUser()).thenReturn(user);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(reviewRepository.existsByUserAndMovie(user, movie)).thenReturn(true);

        String response = reviewService.addReview(1L, 4, "Nice movie");

        assertEquals("You Have Already Reviewed This Movie", response);
    }

    @Test
    void shouldReturnInvalidRatingWhenRatingOutOfRange() {
        User user = new User();
        user.setId(1L);
        Movie movie = new Movie();
        movie.setId(1L);

        when(userService.getCurrentUser()).thenReturn(user);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(reviewRepository.existsByUserAndMovie(user, movie)).thenReturn(false);

        String response = reviewService.addReview(1L, 6, "Nice movie");

        assertEquals("Rating Must Be Between 1 And 5", response);
    }

    @Test
    void shouldSaveReviewWhenDataIsValid() {
        User user = new User();
        user.setId(1L);
        Movie movie = new Movie();
        movie.setId(1L);

        when(userService.getCurrentUser()).thenReturn(user);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(reviewRepository.existsByUserAndMovie(user, movie)).thenReturn(false);

        String response = reviewService.addReview(1L, 5, "Great watch");

        assertEquals("Review Added Successfully", response);
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void shouldReturnReviewNotFoundWhenUpdateReviewMissing() {
        User user = new User();
        user.setId(1L);
        Movie movie = new Movie();
        movie.setId(1L);

        when(userService.getCurrentUser()).thenReturn(user);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(reviewRepository.findByUserAndMovie(user, movie)).thenReturn(null);

        String response = reviewService.updateReview(1L, 4, "Updated");

        assertEquals("Review Not Found", response);
    }

    @Test
    void shouldDeleteReviewSuccessfully() {
        User user = new User();
        user.setId(1L);
        Movie movie = new Movie();
        movie.setId(1L);
        Review review = new Review();
        review.setUser(user);
        review.setMovie(movie);

        when(userService.getCurrentUser()).thenReturn(user);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(reviewRepository.findByUserAndMovie(user, movie)).thenReturn(review);

        String response = reviewService.deleteReview(1L);

        assertEquals("Review Deleted Successfully", response);
        verify(reviewRepository).delete(review);
    }

    @Test
    void shouldReturnReviewDTOForMyReview() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setName("Action");
        Review review = new Review();
        review.setId(100L);
        review.setUser(user);
        review.setMovie(movie);
        review.setRating(5);
        review.setReview("Great movie");

        when(userService.getCurrentUser()).thenReturn(user);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(reviewRepository.findByUserAndMovie(user, movie)).thenReturn(review);

        ReviewDTO dto = reviewService.getMyReview(1L);

        assertNotNull(dto);
        assertEquals(100L, dto.getReviewId());
        assertEquals(1L, dto.getMovieId());
        assertEquals("testuser", dto.getUsername());
    }

    @Test
    void shouldReturnHasReviewed() {
        User user = new User();
        user.setId(1L);
        Movie movie = new Movie();
        movie.setId(1L);

        when(userService.getCurrentUser()).thenReturn(user);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(reviewRepository.existsByUserAndMovie(user, movie)).thenReturn(true);

        assertTrue(reviewService.hasReviewed(1L));
    }

    @Test
    void shouldReturnAverageRatingAndCount() {
        Movie movie = new Movie();
        movie.setId(1L);
        Review review1 = new Review();
        review1.setRating(4);
        Review review2 = new Review();
        review2.setRating(5);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(reviewRepository.findByMovieOrderByReviewDateDesc(movie)).thenReturn(List.of(review1, review2));
        when(reviewRepository.countByMovie(movie)).thenReturn(2L);

        assertEquals(4.5, reviewService.getAverageRating(1L));
        assertEquals(2L, reviewService.getReviewCount(1L));
    }
}
