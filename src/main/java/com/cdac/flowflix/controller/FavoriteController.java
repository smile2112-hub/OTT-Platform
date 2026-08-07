package com.cdac.flowflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.FavoriteDTO;
import com.cdac.flowflix.service.FavoriteService;

@RestController
@RequestMapping("/api/favorite")
@CrossOrigin("*")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    // ==========================
    // ADD TO FAVORITES
    // ==========================

    @PostMapping("/add/{movieId}")
    public ResponseEntity<String> addFavorite(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(
                favoriteService.addFavorite(movieId));

    }

    // ==========================
    // REMOVE FROM FAVORITES
    // ==========================

    @DeleteMapping("/remove/{movieId}")
    public ResponseEntity<String> removeFavorite(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(
                favoriteService.removeFavorite(movieId));

    }

    // ==========================
    // MY FAVORITES
    // ==========================

    @GetMapping("/my")
    public List<FavoriteDTO> myFavorites() {

        return favoriteService.getMyFavorites();

    }

    // ==========================
    // CHECK FAVORITE
    // ==========================

    @GetMapping("/check/{movieId}")
    public ResponseEntity<Boolean> checkFavorite(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(
                favoriteService.isFavorite(movieId));

    }

    // ==========================
    // TOTAL FAVORITES
    // ==========================

    @GetMapping("/count")
    public ResponseEntity<Long> totalFavorites() {

        return ResponseEntity.ok(
                favoriteService.getTotalFavorites());

    }

    // ==========================
    // MY FAVORITES COUNT
    // ==========================

    @GetMapping("/my/count")
    public ResponseEntity<Long> myFavoriteCount() {

        return ResponseEntity.ok(
                favoriteService.getUserFavoriteCount());

    }

    // ==========================
    // MOVIE FAVORITES COUNT
    // ==========================

    @GetMapping("/movie/{movieId}/count")
    public ResponseEntity<Long> movieFavoriteCount(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(
                favoriteService.getMovieFavoriteCount(movieId));

    }

}