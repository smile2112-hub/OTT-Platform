package com.cdac.flowflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.AdminDashboardDTO;
import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.service.AdminService;
import com.cdac.flowflix.service.MovieService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private MovieService movieService;

    @GetMapping("/dashboard")
    public AdminDashboardDTO dashboard() {

        return adminService.getDashboard();

    }

    @GetMapping("/movies")
    public List<MovieDTO> allMovies() {

        return movieService.findAll();

    }

    @PutMapping("/movie/activate/{id}")
    public ResponseEntity<String> activateMovie(
            @PathVariable Long id) {

        if (movieService.findOne(id) == null) {
            return ResponseEntity.ok("Movie Not Found");
        }

        movieService.activateMovie(id);

        return ResponseEntity.ok("Movie Activated Successfully");

    }

    @PutMapping("/movie/deactivate/{id}")
    public ResponseEntity<String> deactivateMovie(
            @PathVariable Long id) {

        if (movieService.findOne(id) == null) {
            return ResponseEntity.ok("Movie Not Found");
        }

        movieService.deactivateMovie(id);

        return ResponseEntity.ok("Movie Deactivated Successfully");

    }

    @PutMapping("/movie/feature/{id}")
    public ResponseEntity<String> featureMovie(
            @PathVariable Long id) {

        if (movieService.findOne(id) == null) {
            return ResponseEntity.ok("Movie Not Found");
        }

        movieService.featureMovie(id);

        return ResponseEntity.ok("Movie Featured Successfully");

    }

    @PutMapping("/movie/unfeature/{id}")
    public ResponseEntity<String> unFeatureMovie(
            @PathVariable Long id) {

        if (movieService.findOne(id) == null) {
            return ResponseEntity.ok("Movie Not Found");
        }

        movieService.unFeatureMovie(id);

        return ResponseEntity.ok("Movie Unfeatured Successfully");

    }

}