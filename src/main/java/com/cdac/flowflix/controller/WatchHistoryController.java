package com.cdac.flowflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.WatchHistoryDTO;
import com.cdac.flowflix.service.WatchHistoryService;

@RestController
@RequestMapping("/api/watch-history")
@CrossOrigin("*")
public class WatchHistoryController {

    @Autowired
    private WatchHistoryService watchHistoryService;

    // ==========================
    // SAVE / UPDATE WATCH HISTORY
    // ==========================

    @PostMapping("/watch/{movieId}")
    public ResponseEntity<String> watchMovie(
            @PathVariable Long movieId,
            @RequestParam(defaultValue = "0") Long watchPosition) {

        watchHistoryService.saveWatchHistory(movieId, watchPosition);

        return ResponseEntity.ok("Watch History Saved Successfully");
    }

    // ==========================
    // COMPLETE WATCH HISTORY
    // ==========================

    @GetMapping
    public List<WatchHistoryDTO> getWatchHistory() {

        return watchHistoryService.getWatchHistory();

    }

    // ==========================
    // CONTINUE WATCHING
    // ==========================

    @GetMapping("/continue")
    public List<WatchHistoryDTO> getContinueWatching() {

        return watchHistoryService.getContinueWatching();

    }

    // ==========================
    // RECENTLY WATCHED
    // ==========================

    @GetMapping("/recent")
    public List<WatchHistoryDTO> getRecentlyWatched() {

        return watchHistoryService.getRecentlyWatched();

    }

    // ==========================
    // CLEAR WATCH HISTORY
    // ==========================

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearWatchHistory() {

        watchHistoryService.clearWatchHistory();

        return ResponseEntity.ok("Watch History Cleared Successfully");

    }

}