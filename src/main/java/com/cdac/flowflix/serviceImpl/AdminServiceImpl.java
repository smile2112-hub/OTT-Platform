package com.cdac.flowflix.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.AdminDashboardDTO;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.repository.FavoriteRepository;
import com.cdac.flowflix.repository.MovieRepository;
import com.cdac.flowflix.repository.ReviewRepository;
import com.cdac.flowflix.repository.UserRepository;
import com.cdac.flowflix.service.AdminService;
import com.cdac.flowflix.service.MovieService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieService movieService;

    @Override
    public AdminDashboardDTO getDashboard() {

        AdminDashboardDTO dto = new AdminDashboardDTO();

        dto.setTotalUsers(userRepository.count());

        dto.setTotalMovies(movieRepository.count());

        dto.setActiveMovies(movieRepository.countByActiveTrue());

        dto.setFeaturedMovies(movieRepository.countByFeaturedTrue());

        dto.setTrendingMovies(movieRepository.findByTrendingTrue().size());

        dto.setTotalFavorites(favoriteRepository.count());

        dto.setTotalReviews(reviewRepository.count());

        dto.setTotalViews(movieService.getTotalViews());

        dto.setMostViewedMovie(movieService.getMostViewedMovie());

        dto.setTopViewedMovies(movieService.getMostViewedMovies());

        return dto;
    }

    public Movie getMostViewedMovieEntity() {
        return movieRepository.findTopByOrderByTotalViewsDesc();
    }

}