package com.cdac.flowflix.service;

import java.util.List;

import com.cdac.flowflix.dto.RecommendationDTO;

public interface RecommendationService {

    // ==========================================
    // HOME RECOMMENDATIONS
    // ==========================================

    List<RecommendationDTO> getRecommendedMovies();

    // ==========================================
    // TRENDING
    // ==========================================

    List<RecommendationDTO> getTrendingMovies();

    // ==========================================
    // FEATURED
    // ==========================================

    List<RecommendationDTO> getFeaturedMovies();

    // ==========================================
    // TOP RATED
    // ==========================================

    List<RecommendationDTO> getTopRatedMovies();

    // ==========================================
    // MOST VIEWED
    // ==========================================

    List<RecommendationDTO> getMostViewedMovies();

    // ==========================================
    // BY GENRE
    // ==========================================

    List<RecommendationDTO> getMoviesByGenre(String genre);

}