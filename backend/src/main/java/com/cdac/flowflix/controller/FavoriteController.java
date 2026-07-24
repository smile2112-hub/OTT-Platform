package com.cdac.flowflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.FavoriteDTO;
import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.service.FavoriteService;

@RestController
@RequestMapping("/api/favorite")
@CrossOrigin("*")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/add")
    public ResponseEntity<String> addFavorite(
            @RequestBody FavoriteDTO dto) {

        return ResponseEntity.ok(
                favoriteService.addToFavorite(dto));
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeFavorite(
            @RequestBody FavoriteDTO dto) {

        return ResponseEntity.ok(
                favoriteService.removeFromFavorite(dto));
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<MovieDTO>> getFavorites(
            @PathVariable String username) {

        return ResponseEntity.ok(
                favoriteService.getFavorites(username));
    }

    @GetMapping("/check/{username}/{movieId}")
    public ResponseEntity<Boolean> isFavorite(
            @PathVariable String username,
            @PathVariable Long movieId) {

        return ResponseEntity.ok(
                favoriteService.isFavorite(username, movieId));
    }
}