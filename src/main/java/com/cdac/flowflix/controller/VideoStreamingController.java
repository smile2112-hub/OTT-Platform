package com.cdac.flowflix.controller;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.service.VideoService;
import com.cdac.flowflix.util.VideoStreamingUtil;

@RestController
@RequestMapping("/api/video")
@CrossOrigin("*")
public class VideoStreamingController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/movie/{movieId}")
    public void streamMovie(
            @PathVariable Long movieId,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        File file = videoService.getVideo(movieId);

        if (file == null || !file.exists()) {

            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

            return;

        }

        stream(file, request, response);

    }

    @GetMapping("/trailer/{movieId}")
    public void streamTrailer(
            @PathVariable Long movieId,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        File file = videoService.getTrailer(movieId);
        

        if (file == null || !file.exists()) {

            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

            return;

        }

        stream(file, request, response);

    }

    private void stream(
            File file,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        long fileLength = file.length();

        String range = request.getHeader("Range");

        long start = 0;

        long end = fileLength - 1;

        if (range != null &&
                range.startsWith("bytes=")) {

            String[] ranges =
                    range.substring(6).split("-");

            start = Long.parseLong(ranges[0]);

            if (ranges.length > 1 &&
                    !ranges[1].isEmpty()) {

                end = Long.parseLong(ranges[1]);

            }

            response.setStatus(
                    HttpServletResponse.SC_PARTIAL_CONTENT);

        }

        long contentLength = end - start + 1;

        response.setContentType("video/mp4");

        response.setHeader(
                "Accept-Ranges",
                "bytes");

        response.setHeader(
                "Content-Length",
                String.valueOf(contentLength));

        response.setHeader(
                "Content-Range",
                "bytes " + start + "-" + end + "/" + fileLength);

        RandomAccessFile randomAccessFile =
                new RandomAccessFile(file, "r");

        VideoStreamingUtil.streamVideo(
                randomAccessFile,
                response,
                start,
                end);

        randomAccessFile.close();

    }

}