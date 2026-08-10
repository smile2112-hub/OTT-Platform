/**
 * Author:  Mahesh Ganesh Kajale
 * Email:   maheshkajale2112@gmail.com
 * Project: FlowFlix OTT Platform
 */
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


// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.

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
