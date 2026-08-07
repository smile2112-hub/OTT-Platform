package com.cdac.flowflix.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.HomeDTO;
import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.service.HomeService;
import com.cdac.flowflix.service.MovieService;
import com.cdac.flowflix.service.WatchProgressService;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private MovieService movieService;

    @Autowired
    private WatchProgressService watchProgressService;

    @Override
    public HomeDTO getHomePage() {

        HomeDTO dto = new HomeDTO();

        dto.setFeaturedMovies(
                movieService.getFeaturedMovies());

        dto.setTrendingMovies(
                movieService.getTrendingMovies());

        dto.setLatestMovies(
                movieService.getLatestMovies());

        dto.setContinueWatching(
                watchProgressService.continueWatching());

        dto.setRecommendedMovies(
                movieService.getTrendingMovies());

        return dto;

    }

    @Override
    public MovieDTO movieDetails(Long id) {

        return new MovieDTO(movieService.findOne(id));

    }

    @Override
    public List<MovieDTO> searchMovies(String keyword) {

        return movieService.searchMovies(keyword);

    }

    @Override
    public List<MovieDTO> recommendedMovies(Long movieId) {

        return movieService.recommendMovies(movieId);

    }

}