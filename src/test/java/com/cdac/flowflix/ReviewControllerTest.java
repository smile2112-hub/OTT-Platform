package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.cdac.flowflix.controller.ReviewController;
import com.cdac.flowflix.dto.ReviewDTO;
import com.cdac.flowflix.service.ReviewService;

@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

    @InjectMocks
    private ReviewController controller;

    @Mock
    private ReviewService reviewService;

    @Test
    void shouldAddUpdateAndDeleteReview() {
        when(reviewService.addReview(1L, 4, "great")).thenReturn("Added");
        when(reviewService.updateReview(1L, 5, "excellent")).thenReturn("Updated");
        when(reviewService.deleteReview(1L)).thenReturn("Deleted");

        assertEquals("Added", controller.addReview(1L, 4, "great").getBody());
        assertEquals("Updated", controller.updateReview(1L, 5, "excellent").getBody());
        assertEquals("Deleted", controller.deleteReview(1L).getBody());
    }

    @Test
    void shouldReturnReviewListsAndCounts() {
        ReviewDTO reviewDTO = mock(ReviewDTO.class);
        when(reviewService.getMovieReviews(1L)).thenReturn(List.of(reviewDTO));
        when(reviewService.getMyReview(1L)).thenReturn(reviewDTO);
        when(reviewService.hasReviewed(1L)).thenReturn(true);
        when(reviewService.getAverageRating(1L)).thenReturn(4.5);
        when(reviewService.getReviewCount(1L)).thenReturn(8L);

        assertEquals(1, controller.getMovieReviews(1L).size());
        assertSame(reviewDTO, controller.getMyReview(1L));
        assertTrue(controller.hasReviewed(1L).getBody());
        assertEquals(4.5, controller.averageRating(1L).getBody());
        assertEquals(8L, controller.reviewCount(1L).getBody());
    }
}
