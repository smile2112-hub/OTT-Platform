package com.cdac.flowflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.WatchHistoryDTO;
import com.cdac.flowflix.model.WatchHistory;
import com.cdac.flowflix.service.WatchHistoryService;

@RestController
@RequestMapping("/api/history")
@CrossOrigin("*")
public class WatchHistoryController {

    @Autowired
    private WatchHistoryService watchHistoryService;

    @PostMapping("/save")
    public ResponseEntity<WatchHistory> saveHistory(
            @RequestBody WatchHistoryDTO dto) {

        return ResponseEntity.ok(
                watchHistoryService.saveHistory(dto));

    }

    @PutMapping("/progress")
    public ResponseEntity<WatchHistory> updateProgress(

            @RequestParam String username,

            @RequestParam Long movieId,

            @RequestParam Long watchedDuration,

            @RequestParam Double progress,

            @RequestParam boolean completed) {

        return ResponseEntity.ok(

                watchHistoryService.updateProgress(

                        username,

                        movieId,

                        watchedDuration,

                        progress,

                        completed));

    }

    @GetMapping("/continue/{username}")
    public ResponseEntity<List<WatchHistory>> continueWatching(

            @PathVariable String username) {

        return ResponseEntity.ok(

                watchHistoryService.getContinueWatching(username));

    }

    @GetMapping("/{username}")
    public ResponseEntity<List<WatchHistory>> history(

            @PathVariable String username) {

        return ResponseEntity.ok(

                watchHistoryService.getHistory(username));

    }

    @GetMapping("/resume/{username}/{movieId}")
    public ResponseEntity<WatchHistory> resumePosition(

            @PathVariable String username,

            @PathVariable Long movieId) {

        return ResponseEntity.ok(

                watchHistoryService.getResumePosition(

                        username,

                        movieId));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHistory(
            @PathVariable Long id) {

        watchHistoryService.deleteHistory(id);

        return ResponseEntity.ok("History Deleted");

    }

    @DeleteMapping("/clear/{username}")
    public ResponseEntity<String> clearHistory(
            @PathVariable String username) {

        watchHistoryService.clearHistory(username);

        return ResponseEntity.ok("History Cleared");

    }

}