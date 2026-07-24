package com.cdac.flowflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.ReviewDTO;
import com.cdac.flowflix.model.Review;
import com.cdac.flowflix.service.ReviewService;

@RestController
@RequestMapping("/api/review")
@CrossOrigin("*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<String> addReview(
            @RequestBody ReviewDTO dto) {

        return ResponseEntity.ok(
                reviewService.addReview(dto));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Review>> getMovieReviews(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(
                reviewService.getMovieReviews(movieId));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Review>> getUserReviews(
            @PathVariable String username) {

        return ResponseEntity.ok(
                reviewService.getUserReviews(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long id) {

        reviewService.deleteReview(id);

        return ResponseEntity.ok("Review Deleted Successfully");
    }
}