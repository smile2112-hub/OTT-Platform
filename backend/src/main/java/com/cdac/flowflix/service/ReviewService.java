package com.cdac.flowflix.service;

import java.util.List;

import com.cdac.flowflix.dto.ReviewDTO;
import com.cdac.flowflix.model.Review;

public interface ReviewService {

    String addReview(ReviewDTO dto);

    List<Review> getMovieReviews(Long movieId);

    List<Review> getUserReviews(String username);

    void deleteReview(Long id);

}