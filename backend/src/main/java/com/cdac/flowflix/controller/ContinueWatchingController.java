package com.cdac.flowflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.WatchProgressDTO;
import com.cdac.flowflix.service.WatchProgressService;

@RestController
@RequestMapping("/api/continue")
@CrossOrigin("*")
public class ContinueWatchingController {

    @Autowired
    private WatchProgressService watchProgressService;

    @GetMapping
    public List<WatchProgressDTO> continueWatching() {

        return watchProgressService.continueWatching();

    }

}