package com.cdac.flowflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.ReviewDTO;
import com.cdac.flowflix.service.ReviewService;

@RestController
@RequestMapping("/api/review")
@CrossOrigin("*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // ==========================
    // ADD REVIEW
    // ==========================

    @PostMapping("/add/{movieId}")
    public ResponseEntity<String> addReview(
            @PathVariable Long movieId,
            @RequestParam Integer rating,
            @RequestParam String review) {

        return ResponseEntity.ok(

                reviewService.addReview(
                        movieId,
                        rating,
                        review));

    }

    // ==========================
    // UPDATE REVIEW
    // ==========================

    @PutMapping("/update/{movieId}")
    public ResponseEntity<String> updateReview(
            @PathVariable Long movieId,
            @RequestParam Integer rating,
            @RequestParam String review) {

        return ResponseEntity.ok(

                reviewService.updateReview(
                        movieId,
                        rating,
                        review));

    }

    // ==========================
    // DELETE REVIEW
    // ==========================

    @DeleteMapping("/delete/{movieId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(

                reviewService.deleteReview(movieId));

    }

    // ==========================
    // GET ALL REVIEWS OF MOVIE
    // ==========================

    @GetMapping("/movie/{movieId}")
    public List<ReviewDTO> getMovieReviews(
            @PathVariable Long movieId) {

        return reviewService.getMovieReviews(movieId);

    }

    // ==========================
    // GET MY REVIEW
    // ==========================

    @GetMapping("/my/{movieId}")
    public ReviewDTO getMyReview(
            @PathVariable Long movieId) {

        return reviewService.getMyReview(movieId);

    }

    // ==========================
    // CHECK REVIEW EXISTS
    // ==========================

    @GetMapping("/check/{movieId}")
    public ResponseEntity<Boolean> hasReviewed(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(

                reviewService.hasReviewed(movieId));

    }

    // ==========================
    // GET MOVIE AVERAGE RATING
    // ==========================

    @GetMapping("/average/{movieId}")
    public ResponseEntity<Double> averageRating(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(

                reviewService.getAverageRating(movieId));

    }

    // ==========================
    // GET REVIEW COUNT
    // ==========================

    @GetMapping("/count/{movieId}")
    public ResponseEntity<Long> reviewCount(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(

                reviewService.getReviewCount(movieId));

    }

    // ==========================
    // GET ALL REVIEWS
    // ==========================

    @GetMapping("/getAllReviews")
    public List<ReviewDTO> getAllReviews() {

        return reviewService.getAllReviews();

    }

    // ==========================
    // GET MY REVIEWS
    // ==========================

    @GetMapping("/myReviews")
    public List<ReviewDTO> getMyReviews() {

        return reviewService.getMyReviews();

    }

    // ==========================
    // GET REVIEW BY ID
    // ==========================

    @GetMapping("/{reviewId}")
    public ReviewDTO getReviewById(
            @PathVariable Long reviewId) {

        return reviewService.getReviewById(reviewId);

    }

    // ==========================
    // UPDATE REVIEW BY ID
    // ==========================

    @PutMapping("/updateReview/{reviewId}")
    public ResponseEntity<String> updateReviewById(
            @PathVariable Long reviewId,
            @RequestParam Integer rating,
            @RequestParam String review) {

        return ResponseEntity.ok(

                reviewService.updateReviewById(
                        reviewId,
                        rating,
                        review));

    }

    // ==========================
    // DELETE REVIEW BY ID
    // ==========================

    @DeleteMapping("/deleteReview/{reviewId}")
    public ResponseEntity<String> deleteReviewById(
            @PathVariable Long reviewId) {

        return ResponseEntity.ok(

                reviewService.deleteReviewById(reviewId));

    }

}