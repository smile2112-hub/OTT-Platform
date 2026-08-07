package com.cdac.flowflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.AdminDashboardDTO;
import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.dto.SubscriptionDTO;
import com.cdac.flowflix.dto.UserDTO;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.service.AdminService;
import com.cdac.flowflix.service.MovieService;
import com.cdac.flowflix.service.SubscriptionService;
import com.cdac.flowflix.service.UserService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    // ==========================================
    // DASHBOARD
    // ==========================================

    @GetMapping("/dashboard")
    public AdminDashboardDTO dashboard() {

        return adminService.getDashboard();

    }

    // ==========================================
    // USER MANAGEMENT
    // ==========================================

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {

        return userService.findAllUsers();

    }

    @PutMapping("/activateUser/{id}")
    public ResponseEntity<String> activateUser(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                userService.activateUser(id));

    }

    @PutMapping("/deactivateUser/{id}")
    public ResponseEntity<String> deactivateUser(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                userService.deactivateUser(id));

    }

    @PutMapping("/makeAdmin/{id}")
    public ResponseEntity<String> makeAdmin(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                userService.makeAdmin(id));

    }

    @PutMapping("/removeAdmin/{id}")
    public ResponseEntity<String> removeAdmin(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                userService.removeAdmin(id));

    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                userService.deleteUser(id));

    }

    // ==========================================
    // MOVIE MANAGEMENT
    // ==========================================

    @GetMapping("/movies")
    public List<MovieDTO> getAllMovies() {

        return movieService.findAll();

    }

    @PutMapping("/movie/activate/{id}")
    public ResponseEntity<String> activateMovie(
            @PathVariable Long id) {

        movieService.activateMovie(id);

        return ResponseEntity.ok(
                "Movie Activated Successfully");

    }

    @PutMapping("/movie/deactivate/{id}")
    public ResponseEntity<String> deactivateMovie(
            @PathVariable Long id) {

        movieService.deactivateMovie(id);

        return ResponseEntity.ok(
                "Movie Deactivated Successfully");

    }

    @PutMapping("/movie/feature/{id}")
    public ResponseEntity<String> featureMovie(
            @PathVariable Long id) {

        movieService.featureMovie(id);

        return ResponseEntity.ok(
                "Movie Featured Successfully");

    }

    @PutMapping("/movie/unfeature/{id}")
    public ResponseEntity<String> unFeatureMovie(
            @PathVariable Long id) {

        movieService.unFeatureMovie(id);

        return ResponseEntity.ok(
                "Movie Unfeatured Successfully");

    }

    @PutMapping("/movie/trending/{id}")
    public ResponseEntity<String> makeTrending(
            @PathVariable Long id) {

        movieService.makeTrending(id);

        return ResponseEntity.ok(
                "Movie Marked As Trending");

    }

    @PutMapping("/movie/removeTrending/{id}")
    public ResponseEntity<String> removeTrending(
            @PathVariable Long id) {

        movieService.removeTrending(id);

        return ResponseEntity.ok(
                "Trending Removed");

    }

    @PutMapping("/movie/update/{id}")
    public ResponseEntity<?> updateMovie(
            @PathVariable Long id,
            @RequestBody Movie movie) {

        Movie updatedMovie =
                movieService.updateMovie(id, movie);

        if (updatedMovie == null) {

            return ResponseEntity.ok(
                    "Movie Not Found");

        }

        return ResponseEntity.ok(updatedMovie);

    }

    @DeleteMapping("/movie/delete/{id}")
    public ResponseEntity<String> deleteMovie(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                movieService.deleteMovie(id));

    }

    // ==========================================
    // ANALYTICS
    // ==========================================

    @GetMapping("/analytics/mostViewed")
    public String getMostViewedMovie() {

        return movieService.getMostViewedMovie();

    }

    @GetMapping("/analytics/topViewed")
    public List<MovieDTO> getTopViewedMovies() {

        return movieService.getTopViewedMovies();

    }

    @GetMapping("/analytics/totalViews")
    public Long getTotalViews() {

        return movieService.getTotalViews();

    }

    // ==========================================
    // SUBSCRIPTION MANAGEMENT
    // ==========================================

    @GetMapping("/subscriptions")
    public List<SubscriptionDTO> getAllSubscriptions() {

        return subscriptionService.getAllSubscriptions();

    }

    @PutMapping("/subscriptions/updateExpired")
    public ResponseEntity<String> updateExpiredSubscriptions() {

        subscriptionService.updateExpiredSubscriptions();

        return ResponseEntity.ok(
                "Expired Subscriptions Updated Successfully");

    }

}