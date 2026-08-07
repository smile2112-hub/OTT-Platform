package com.cdac.flowflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.RecommendationDTO;
import com.cdac.flowflix.service.RecommendationService;

@RestController
@RequestMapping("/api/recommendation")
@CrossOrigin("*")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    // ==========================================
    // HOME RECOMMENDATIONS
    // ==========================================

    @GetMapping("/home")
    public List<RecommendationDTO> getRecommendedMovies() {

        return recommendationService.getRecommendedMovies();

    }

    // ==========================================
    // TRENDING MOVIES
    // ==========================================

    @GetMapping("/trending")
    public List<RecommendationDTO> getTrendingMovies() {

        return recommendationService.getTrendingMovies();

    }

    // ==========================================
    // FEATURED MOVIES
    // ==========================================

    @GetMapping("/featured")
    public List<RecommendationDTO> getFeaturedMovies() {

        return recommendationService.getFeaturedMovies();

    }

    // ==========================================
    // TOP RATED MOVIES
    // ==========================================

    @GetMapping("/top-rated")
    public List<RecommendationDTO> getTopRatedMovies() {

        return recommendationService.getTopRatedMovies();

    }

    // ==========================================
    // MOST VIEWED MOVIES
    // ==========================================

    @GetMapping("/most-viewed")
    public List<RecommendationDTO> getMostViewedMovies() {

        return recommendationService.getMostViewedMovies();

    }

    // ==========================================
    // MOVIES BY GENRE
    // ==========================================

    @GetMapping("/genre/{genre}")
    public List<RecommendationDTO> getMoviesByGenre(
            @PathVariable String genre) {

        return recommendationService.getMoviesByGenre(genre);

    }

}