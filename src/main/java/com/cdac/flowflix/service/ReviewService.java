package com.cdac.flowflix.service;

import java.util.List;

import com.cdac.flowflix.dto.ReviewDTO;

public interface ReviewService {

    // ==========================
    // ADD REVIEW
    // ==========================

    String addReview(
            Long movieId,
            Integer rating,
            String review);

    // ==========================
    // UPDATE REVIEW
    // ==========================

    String updateReview(
            Long movieId,
            Integer rating,
            String review);

    // ==========================
    // DELETE REVIEW
    // ==========================

    String deleteReview(
            Long movieId);

    // ==========================
    // GET ALL REVIEWS OF MOVIE
    // ==========================

    List<ReviewDTO> getMovieReviews(
            Long movieId);

    // ==========================
    // GET MY REVIEW
    // ==========================

    ReviewDTO getMyReview(
            Long movieId);

    // ==========================
    // CHECK IF REVIEW EXISTS
    // ==========================

    boolean hasReviewed(
            Long movieId);

    // ==========================
    // GET MOVIE AVERAGE RATING
    // ==========================

    Double getAverageRating(
            Long movieId);

    // ==========================
    // GET MOVIE REVIEW COUNT
    // ==========================

    Long getReviewCount(
            Long movieId);

    // ==========================
    // GET ALL REVIEWS
    // ==========================

    List<ReviewDTO> getAllReviews();

    // ==========================
    // GET MY REVIEWS
    // ==========================

    List<ReviewDTO> getMyReviews();

    // ==========================
    // GET REVIEW BY ID
    // ==========================

    ReviewDTO getReviewById(
            Long reviewId);

    // ==========================
    // UPDATE REVIEW BY ID
    // ==========================

    String updateReviewById(
            Long reviewId,
            Integer rating,
            String review);

    // ==========================
    // DELETE REVIEW BY ID
    // ==========================

    String deleteReviewById(
            Long reviewId);

}