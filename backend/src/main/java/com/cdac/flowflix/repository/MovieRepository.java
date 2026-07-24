package com.cdac.flowflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.flowflix.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByFeaturedTrue();

    List<Movie> findTop10ByOrderByTotalViewsDesc();

    List<Movie> findTop10ByOrderByYearDesc();

    List<Movie> findByGenre(String genre);

    List<Movie> findByNameContainingIgnoreCase(String keyword);

    List<Movie> findByActiveTrue();

    long countByActiveTrue();

    long countByFeaturedTrue();

    Movie findTopByOrderByTotalViewsDesc();

    List<Movie> findTop10ByGenreAndActiveTrueOrderByTotalViewsDesc(String genre);

}