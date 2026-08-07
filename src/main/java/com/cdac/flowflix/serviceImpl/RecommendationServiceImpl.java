package com.cdac.flowflix.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.RecommendationDTO;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.repository.RecommendationRepository;
import com.cdac.flowflix.service.RecommendationService;

@Service
public class RecommendationServiceImpl
        implements RecommendationService {

    @Autowired
    private RecommendationRepository recommendationRepository;

    // ==========================================
    // HOME RECOMMENDATIONS
    // ==========================================

    @Override
    public List<RecommendationDTO> getRecommendedMovies() {

        return recommendationRepository

                .findByActiveTrue()

                .stream()

                .map(RecommendationDTO::new)

                .collect(Collectors.toList());

    }

    // ==========================================
    // TRENDING
    // ==========================================

    @Override
    public List<RecommendationDTO> getTrendingMovies() {

        return recommendationRepository

                .findByTrendingTrue()

                .stream()

                .filter(Movie::isActive)

                .map(RecommendationDTO::new)

                .collect(Collectors.toList());

    }

    // ==========================================
    // FEATURED
    // ==========================================

    @Override
    public List<RecommendationDTO> getFeaturedMovies() {

        return recommendationRepository

                .findByFeaturedTrue()

                .stream()

                .filter(Movie::isActive)

                .map(RecommendationDTO::new)

                .collect(Collectors.toList());

    }

    // ==========================================
    // TOP RATED
    // ==========================================

    @Override
    public List<RecommendationDTO> getTopRatedMovies() {

        return recommendationRepository

                .findTop10ByActiveTrueOrderByRatingDesc()

                .stream()

                .map(RecommendationDTO::new)

                .collect(Collectors.toList());

    }

    // ==========================================
    // MOST VIEWED
    // ==========================================

    @Override
    public List<RecommendationDTO> getMostViewedMovies() {

        return recommendationRepository

                .findTop10ByActiveTrueOrderByTotalViewsDesc()

                .stream()

                .map(RecommendationDTO::new)

                .collect(Collectors.toList());

    }

    // ==========================================
    // BY GENRE
    // ==========================================

    @Override
    public List<RecommendationDTO> getMoviesByGenre(
            String genre) {

        return recommendationRepository

                .findByGenreIgnoreCase(genre)

                .stream()

                .filter(Movie::isActive)

                .map(RecommendationDTO::new)

                .collect(Collectors.toList());

    }

}