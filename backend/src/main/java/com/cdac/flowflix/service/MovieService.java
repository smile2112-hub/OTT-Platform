package com.cdac.flowflix.service;

import java.util.List;

import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.model.Movie;

public interface MovieService {

    String isValidInput(Movie movie);

    List<MovieDTO> findAll();

    Movie save(Movie movie);

    String editMovie(Movie movie);

    Movie delete(Movie movie);

    Movie findOne(Long id);

    Movie incrementViews(Long id);

    List<MovieDTO> getFeaturedMovies();

    List<MovieDTO> getTrendingMovies();

    List<MovieDTO> getLatestMovies();

    List<MovieDTO> getMoviesByGenre(String genre);

    Movie activateMovie(Long id);

    Movie deactivateMovie(Long id);

    Movie featureMovie(Long id);

    Movie unFeatureMovie(Long id);

    List<MovieDTO> searchMovies(String keyword);

    List<MovieDTO> getActiveMovies();

    List<MovieDTO> recommendMovies(Long movieId);

}