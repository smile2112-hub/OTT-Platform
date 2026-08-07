package com.cdac.flowflix.service;

import java.util.List;

import com.cdac.flowflix.dto.WatchlistDTO;

public interface WatchlistService {

    // ==========================================
    // ADD MOVIE
    // ==========================================

    String addToWatchlist(Long movieId);

    // ==========================================
    // REMOVE MOVIE
    // ==========================================

    String removeFromWatchlist(Long movieId);

    // ==========================================
    // GET MY WATCHLIST
    // ==========================================

    List<WatchlistDTO> getMyWatchlist();

    // ==========================================
    // CHECK MOVIE EXISTS
    // ==========================================

    boolean existsInWatchlist(Long movieId);

    // ==========================================
    // CLEAR WATCHLIST
    // ==========================================

    String clearWatchlist();

    // ==========================================
    // WATCHLIST COUNT
    // ==========================================

    Long getWatchlistCount();

}