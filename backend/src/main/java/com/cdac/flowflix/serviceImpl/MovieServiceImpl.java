package com.cdac.flowflix.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.repository.MovieRepository;
import com.cdac.flowflix.service.MovieService;
import com.cdac.flowflix.exception.*;
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public List<MovieDTO> findAll() {

        List<MovieDTO> movieDTOList = new ArrayList<>();

        for (Movie movie : movieRepository.findAll()) {
            movieDTOList.add(new MovieDTO(movie));
        }

        return movieDTOList;
    }

    @Override
    public String isValidInput(Movie movie) {

        if (movie.getName() == null || movie.getName().trim().isEmpty())
            return "invalid";

        if (movie.getDirector() == null || movie.getDirector().trim().isEmpty())
            return "invalid";

        if (movie.getDescription() == null || movie.getDescription().trim().isEmpty())
            return "invalid";

        if (movie.getGenre() == null || movie.getGenre().trim().isEmpty())
            return "invalid";

        if (movie.getActors() == null || movie.getActors().trim().isEmpty())
            return "invalid";

        if (movie.getDistributor() == null || movie.getDistributor().trim().isEmpty())
            return "invalid";

        if (movie.getYear() <= 0)
            return "invalid";

        if (movie.getDuration() <= 0)
            return "invalid";

        return "valid";
    }

    @Override
    public String editMovie(Movie movie) {

        Movie dbMovie = movieRepository.findById(movie.getId()).orElse(null);

        if (dbMovie == null)
            return "Movie Not Found";

        dbMovie.setName(movie.getName());
        dbMovie.setDirector(movie.getDirector());
        dbMovie.setDescription(movie.getDescription());
        dbMovie.setGenre(movie.getGenre());
        dbMovie.setActors(movie.getActors());
        dbMovie.setDistributor(movie.getDistributor());
        dbMovie.setYear(movie.getYear());
        dbMovie.setDuration(movie.getDuration());

        dbMovie.setPoster(movie.getPoster());
        dbMovie.setBanner(movie.getBanner());
        dbMovie.setVideo(movie.getVideo());
        dbMovie.setTrailer(movie.getTrailer());

        dbMovie.setVideoSize(movie.getVideoSize());
        dbMovie.setFeatured(movie.isFeatured());
        dbMovie.setActive(movie.isActive());

        dbMovie.setRating(movie.getRating());

        movieRepository.save(dbMovie);

        return "success";
    }

    @Override
    public Movie delete(Movie movie) {

    	if (movie == null) {
    	    throw new ResourceNotFoundException("Movie not found.");
    	}

        movieRepository.delete(movie);

        return movie;
    }

    @Override
    public Movie findOne(Long id) {

        return movieRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Movie not found with id : " + id));

    }

    @Override
    public Movie incrementViews(Long id) {

        Movie movie = findOne(id);

        if (movie == null)
            return null;

        movie.setTotalViews(movie.getTotalViews() + 1);

        return movieRepository.save(movie);

    }

    @Override
    public List<MovieDTO> getFeaturedMovies() {

        List<MovieDTO> list = new ArrayList<>();

        for (Movie movie : movieRepository.findByFeaturedTrue()) {

            list.add(new MovieDTO(movie));

        }

        return list;
    }

    @Override
    public List<MovieDTO> getTrendingMovies() {

        List<MovieDTO> list = new ArrayList<>();

        for (Movie movie : movieRepository.findTop10ByOrderByTotalViewsDesc()) {

            list.add(new MovieDTO(movie));

        }

        return list;
    }

    @Override
    public List<MovieDTO> getLatestMovies() {

        List<MovieDTO> list = new ArrayList<>();

        for (Movie movie : movieRepository.findTop10ByOrderByYearDesc()) {

            list.add(new MovieDTO(movie));

        }

        return list;
    }

    @Override
    public List<MovieDTO> getMoviesByGenre(String genre) {

        List<MovieDTO> list = new ArrayList<>();

        for (Movie movie : movieRepository.findByGenre(genre)) {

            list.add(new MovieDTO(movie));

        }

        return list;
    }

    @Override
    public Movie activateMovie(Long id) {

        Movie movie = findOne(id);

        if (movie == null)
            return null;

        movie.setActive(true);

        return movieRepository.save(movie);
    }

    @Override
    public Movie deactivateMovie(Long id) {

        Movie movie = findOne(id);

        if (movie == null)
            return null;

        movie.setActive(false);

        return movieRepository.save(movie);
    }

    @Override
    public Movie featureMovie(Long id) {

        Movie movie = findOne(id);

        if (movie == null)
            return null;

        movie.setFeatured(true);

        return movieRepository.save(movie);
    }

    @Override
    public Movie unFeatureMovie(Long id) {

        Movie movie = findOne(id);

        if (movie == null)
            return null;

        movie.setFeatured(false);

        return movieRepository.save(movie);
    }

    @Override
    public List<MovieDTO> searchMovies(String keyword) {

        List<MovieDTO> list = new ArrayList<>();

        for (Movie movie : movieRepository.findByNameContainingIgnoreCase(keyword)) {

            if (movie.isActive()) {

                list.add(new MovieDTO(movie));

            }
        }

        return list;
    }

    @Override
    public List<MovieDTO> getActiveMovies() {

        List<MovieDTO> list = new ArrayList<>();

        for (Movie movie : movieRepository.findByActiveTrue()) {

            list.add(new MovieDTO(movie));

        }

        return list;
    }
    @Override
    public List<MovieDTO> recommendMovies(Long movieId) {

        List<MovieDTO> list = new ArrayList<>();

        Movie movie = findOne(movieId);

        if (movie == null) {
            return list;
        }

        List<Movie> recommendations =
                movieRepository.findTop10ByGenreAndActiveTrueOrderByTotalViewsDesc(
                        movie.getGenre());

        for (Movie m : recommendations) {

            if (!m.getId().equals(movieId)) {

                list.add(new MovieDTO(m));

            }
        }

        return list;
    }

}