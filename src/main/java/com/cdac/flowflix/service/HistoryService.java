package com.cdac.flowflix.service;

import java.util.List;

import com.cdac.flowflix.dto.HistoryDTO;

public interface HistoryService {

    // ==========================
    // SAVE / UPDATE WATCH HISTORY
    // ==========================

    String saveHistory(
            Long movieId,
            Long watchPosition,
            Long totalDuration);

    // ==========================
    // GET MY WATCH HISTORY
    // ==========================

    List<HistoryDTO> getMyHistory();

    // ==========================
    // CONTINUE WATCHING
    // ==========================

    List<HistoryDTO> getContinueWatching();

    // ==========================
    // COMPLETED MOVIES
    // ==========================

    List<HistoryDTO> getCompletedMovies();

    // ==========================
    // GET SINGLE HISTORY
    // ==========================

    HistoryDTO getMovieHistory(
            Long movieId);

    // ==========================
    // DELETE HISTORY
    // ==========================

    String deleteHistory(
            Long movieId);

    // ==========================
    // CLEAR HISTORY
    // ==========================

    String clearHistory();

    // ==========================
    // CHECK HISTORY EXISTS
    // ==========================

    boolean hasHistory(
            Long movieId);

    // ==========================
    // TOTAL WATCHED MOVIES
    // ==========================

    Long getHistoryCount();

}