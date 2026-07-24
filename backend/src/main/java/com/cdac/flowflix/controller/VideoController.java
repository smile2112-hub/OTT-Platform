package com.cdac.flowflix.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.service.VideoService;

@RestController
@RequestMapping("/api/video")
@CrossOrigin("*")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/stream/{movieId}")
    public ResponseEntity<InputStreamResource> streamVideo(
            @PathVariable Long movieId,
            HttpServletRequest request) throws IOException {

        File video = videoService.getVideo(movieId);

        if (video == null || !video.exists()) {
            return ResponseEntity.notFound().build();
        }

        long length = video.length();

        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_TYPE, "video/mp4");
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(length));
        headers.add(HttpHeaders.ACCEPT_RANGES, "bytes");

        InputStreamResource resource =
                new InputStreamResource(new FileInputStream(video));

        return new ResponseEntity<>(
                resource,
                headers,
                HttpStatus.OK);
    }

}