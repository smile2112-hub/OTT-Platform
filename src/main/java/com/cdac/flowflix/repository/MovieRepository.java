package com.cdac.flowflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.flowflix.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByActiveTrue();

    List<Movie> findByFeaturedTrue();

    List<Movie> findByTrendingTrue();

    List<Movie> findByGenreContainingIgnoreCase(String genre);

    List<Movie> findByNameContainingIgnoreCase(String keyword);

    List<Movie> findTop10ByOrderByIdDesc();

    long countByActiveTrue();

    long countByFeaturedTrue();

    Movie findTopByOrderByTotalViewsDesc();
    List<Movie> findTop10ByOrderByTotalViewsDesc();

}