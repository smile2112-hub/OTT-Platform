package com.cdac.flowflix.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.AdminDashboardDTO;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.repository.FavoriteRepository;
import com.cdac.flowflix.repository.MovieRepository;
import com.cdac.flowflix.repository.ReviewRepository;
import com.cdac.flowflix.repository.UserRepository;
import com.cdac.flowflix.service.AdminService;

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

    @Override
    public AdminDashboardDTO getDashboard() {

        AdminDashboardDTO dto = new AdminDashboardDTO();

        dto.setTotalUsers(userRepository.count());

        dto.setTotalMovies(movieRepository.count());

        dto.setActiveMovies(movieRepository.countByActiveTrue());

        dto.setFeaturedMovies(movieRepository.countByFeaturedTrue());

        dto.setTotalFavorites(favoriteRepository.count());

        dto.setTotalReviews(reviewRepository.count());

        List<Movie> movies = movieRepository.findAll();

        long totalViews = 0;

        for(Movie movie : movies){

            if(movie.getTotalViews() != null){

                totalViews += movie.getTotalViews();

            }

        }

        dto.setTotalViews(totalViews);

        Movie trending = movieRepository.findTopByOrderByTotalViewsDesc();

        if(trending != null){

            dto.setTrendingMovie(trending.getName());

        }else{

            dto.setTrendingMovie("No Movie");

        }

        return dto;

    }

}