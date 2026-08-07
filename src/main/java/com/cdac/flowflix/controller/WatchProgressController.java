package com.cdac.flowflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.WatchProgressDTO;
import com.cdac.flowflix.service.WatchProgressService;

@RestController
@RequestMapping("/api/watch-progress")
@CrossOrigin("*")
public class WatchProgressController {

    @Autowired
    private WatchProgressService watchProgressService;

    @PostMapping("/save")
    public String saveProgress(
            @RequestParam Long movieId,
            @RequestParam Long watchedSeconds,
            @RequestParam Long totalSeconds) {

        watchProgressService.saveProgress(
                movieId,
                watchedSeconds,
                totalSeconds);

        return "Progress Saved";
    }

    @GetMapping("/continue")
    public List<WatchProgressDTO> continueWatching() {

        return watchProgressService.continueWatching();

    }

    @GetMapping("/history")
    public List<WatchProgressDTO> history() {

        return watchProgressService.watchHistory();

    }

    @GetMapping("/resume/{movieId}")
    public Long resumeMovie(
            @PathVariable Long movieId) {

        return watchProgressService.getResumeTime(movieId);

    }

}