package com.cdac.flowflix.dto;

import java.time.LocalDateTime;

import com.cdac.flowflix.model.Review;

public class ReviewDTO {

    private Long reviewId;

    private Long movieId;

    private String movieName;

    private Long userId;

    private String username;

    private Integer rating;

    private String review;

    private LocalDateTime reviewDate;

    public ReviewDTO() {

    }

    public ReviewDTO(Review review) {

        this.reviewId = review.getId();

        if (review.getMovie() != null) {

            this.movieId = review.getMovie().getId();
            this.movieName = review.getMovie().getName();

        }

        if (review.getUser() != null) {

            this.userId = review.getUser().getId();
            this.username = review.getUser().getUsername();

        }

        this.rating = review.getRating();

        this.review = review.getReview();

        this.reviewDate = review.getReviewDate();

    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

}