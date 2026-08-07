package com.cdac.flowflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.WatchlistDTO;
import com.cdac.flowflix.service.WatchlistService;

@RestController
@RequestMapping("/api/watchlist")
@CrossOrigin("*")
public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;

    // ==========================================
    // ADD MOVIE
    // ==========================================

    @PostMapping("/add/{movieId}")
    public ResponseEntity<String> addMovie(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(

                watchlistService.addToWatchlist(movieId));

    }

    // ==========================================
    // REMOVE MOVIE
    // ==========================================

    @DeleteMapping("/remove/{movieId}")
    public ResponseEntity<String> removeMovie(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(

                watchlistService.removeFromWatchlist(movieId));

    }

    // ==========================================
    // GET WATCHLIST
    // ==========================================

    @GetMapping("/my")
    public ResponseEntity<List<WatchlistDTO>> getMyWatchlist() {

        return ResponseEntity.ok(

                watchlistService.getMyWatchlist());

    }

    // ==========================================
    // CHECK MOVIE EXISTS
    // ==========================================

    @GetMapping("/exists/{movieId}")
    public ResponseEntity<Boolean> exists(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(

                watchlistService.existsInWatchlist(movieId));

    }

    // ==========================================
    // CLEAR WATCHLIST
    // ==========================================

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearWatchlist() {

        return ResponseEntity.ok(

                watchlistService.clearWatchlist());

    }

    // ==========================================
    // WATCHLIST COUNT
    // ==========================================

    @GetMapping("/count")
    public ResponseEntity<Long> getWatchlistCount() {

        return ResponseEntity.ok(

                watchlistService.getWatchlistCount());

    }

}