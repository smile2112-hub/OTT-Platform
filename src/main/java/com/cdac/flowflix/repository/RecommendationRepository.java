package com.cdac.flowflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.flowflix.model.Movie;

@Repository
public interface RecommendationRepository extends JpaRepository<Movie, Long> {

    // ==========================================
    // ACTIVE MOVIES
    // ==========================================

    List<Movie> findByActiveTrue();

    // ==========================================
    // TRENDING MOVIES
    // ==========================================

    List<Movie> findByTrendingTrue();

    // ==========================================
    // FEATURED MOVIES
    // ==========================================

    List<Movie> findByFeaturedTrue();

    // ==========================================
    // GENRE
    // ==========================================

    List<Movie> findByGenreIgnoreCase(String genre);

    // ==========================================
    // TOP VIEWED
    // ==========================================

    List<Movie> findTop10ByActiveTrueOrderByTotalViewsDesc();

    // ==========================================
    // TOP RATED
    // ==========================================

    List<Movie> findTop10ByActiveTrueOrderByRatingDesc();

}