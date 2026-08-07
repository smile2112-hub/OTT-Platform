package com.cdac.flowflix.service;

import java.util.List;

import com.cdac.flowflix.dto.FavoriteDTO;

public interface FavoriteService {

    // ==========================
    // ADD TO FAVORITES
    // ==========================

    String addFavorite(Long movieId);

    // ==========================
    // REMOVE FROM FAVORITES
    // ==========================

    String removeFavorite(Long movieId);

    // ==========================
    // GET MY FAVORITES
    // ==========================

    List<FavoriteDTO> getMyFavorites();

    // ==========================
    // CHECK FAVORITE
    // ==========================

    boolean isFavorite(Long movieId);

    // ==========================
    // TOTAL FAVORITES
    // ==========================

    long getTotalFavorites();

    // ==========================
    // USER FAVORITES COUNT
    // ==========================

    long getUserFavoriteCount();

    // ==========================
    // MOVIE FAVORITES COUNT
    // ==========================

    long getMovieFavoriteCount(Long movieId);

}