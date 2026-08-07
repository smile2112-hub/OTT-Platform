package com.cdac.flowflix.service;

import java.util.List;

import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.model.Movie;

public interface MovieService {

    Movie save(Movie movie);

    Movie findOne(Long id);

    MovieDTO findMovieDTOById(Long id);

    void delete(Movie movie);

    List<MovieDTO> findAll();

    List<MovieDTO> getFeaturedMovies();

    List<MovieDTO> getLatestMovies();

    List<MovieDTO> getTrendingMovies();

    List<MovieDTO> getMoviesByGenre(String genre);

    List<MovieDTO> searchMovies(String keyword);

    List<MovieDTO> getActiveMovies();

    List<MovieDTO> recommendMovies(Long movieId);

    void activateMovie(Long id);

    void deactivateMovie(Long id);

    void featureMovie(Long id);

    void unFeatureMovie(Long id);

    Movie updateMovie(Long id, Movie movie);

    String deleteMovie(Long id);

    void makeTrending(Long id);

    void removeTrending(Long id);

    void incrementViews(Long id);

    List<MovieDTO> getMostViewedMovies();

    // ======================================
    // REVIEWS & RATINGS
    // ======================================

    Double getAverageRating(Long movieId);
    Long getTotalViews();
    String getMostViewedMovie();

    Long getReviewCount(Long movieId);

    void updateMovieRating(Long movieId);
    List<MovieDTO> getTopViewedMovies();


}