package com.cdac.flowflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.HistoryDTO;
import com.cdac.flowflix.service.HistoryService;

@RestController
@RequestMapping("/api/history")
@CrossOrigin("*")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    // ==========================
    // SAVE / UPDATE HISTORY
    // ==========================

    @PostMapping("/save/{movieId}")
    public ResponseEntity<String> saveHistory(
            @PathVariable Long movieId,
            @RequestParam Long watchPosition,
            @RequestParam Long totalDuration) {

        return ResponseEntity.ok(
                historyService.saveHistory(
                        movieId,
                        watchPosition,
                        totalDuration));
    }

    // ==========================
    // MY WATCH HISTORY
    // ==========================

    @GetMapping("/my")
    public List<HistoryDTO> getMyHistory() {

        return historyService.getMyHistory();

    }

    // ==========================
    // CONTINUE WATCHING
    // ==========================

    @GetMapping("/continue")
    public List<HistoryDTO> continueWatching() {

        return historyService.getContinueWatching();

    }

    // ==========================
    // COMPLETED MOVIES
    // ==========================

    @GetMapping("/completed")
    public List<HistoryDTO> completedMovies() {

        return historyService.getCompletedMovies();

    }

    // ==========================
    // GET MOVIE HISTORY
    // ==========================

    @GetMapping("/movie/{movieId}")
    public HistoryDTO getMovieHistory(
            @PathVariable Long movieId) {

        return historyService.getMovieHistory(movieId);

    }

    // ==========================
    // DELETE HISTORY
    // ==========================

    @DeleteMapping("/delete/{movieId}")
    public ResponseEntity<String> deleteHistory(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(
                historyService.deleteHistory(movieId));

    }

    // ==========================
    // CLEAR HISTORY
    // ==========================

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearHistory() {

        return ResponseEntity.ok(
                historyService.clearHistory());

    }

    // ==========================
    // CHECK HISTORY
    // ==========================

    @GetMapping("/check/{movieId}")
    public ResponseEntity<Boolean> hasHistory(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(
                historyService.hasHistory(movieId));

    }

    // ==========================
    // HISTORY COUNT
    // ==========================

    @GetMapping("/count")
    public ResponseEntity<Long> historyCount() {

        return ResponseEntity.ok(
                historyService.getHistoryCount());

    }

}