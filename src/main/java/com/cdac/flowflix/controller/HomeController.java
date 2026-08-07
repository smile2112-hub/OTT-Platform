package com.cdac.flowflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.HomeDTO;
import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.service.HomeService;

@RestController
@RequestMapping("/api/home")
@CrossOrigin("*")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping
    public HomeDTO homePage() {

        return homeService.getHomePage();

    }

    @GetMapping("/movie/{id}")
    public MovieDTO movieDetails(
            @PathVariable Long id) {

        return homeService.movieDetails(id);

    }

    @GetMapping("/search/{keyword}")
    public List<MovieDTO> search(
            @PathVariable String keyword) {

        return homeService.searchMovies(keyword);

    }

    @GetMapping("/recommend/{movieId}")
    public List<MovieDTO> recommend(
            @PathVariable Long movieId) {

        return homeService.recommendedMovies(movieId);

    }

}