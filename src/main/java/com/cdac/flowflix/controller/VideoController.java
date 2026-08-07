package com.cdac.flowflix.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.service.MovieService;

import java.io.InputStream;

@RestController
@RequestMapping("/api/video")
@CrossOrigin("*")
public class VideoController {

    @Autowired
    private MovieService movieService;


    @GetMapping("/stream/{id}")
    public ResponseEntity<InputStreamResource> streamVideo(

            @PathVariable Long id,

            @RequestHeader(
                    value = HttpHeaders.RANGE,
                    required = false
            )
            String rangeHeader) {

        try {

            Movie movie = movieService.findOne(id);

            if (movie == null) {

                return ResponseEntity
                        .notFound()
                        .build();

            }


            String videoFile = movie.getVideo();


            if (videoFile == null ||
                    videoFile.trim().isEmpty()) {

                return ResponseEntity
                        .notFound()
                        .build();

            }


            Path videoPath =
                    Paths.get("uploads/videos/")
                            .resolve(videoFile)
                            .normalize();


            if (!Files.exists(videoPath) ||
                    !Files.isRegularFile(videoPath)) {

                return ResponseEntity
                        .notFound()
                        .build();

            }


            long fileSize =
                    Files.size(videoPath);


            String contentType =
                    Files.probeContentType(videoPath);


            if (contentType == null) {

                contentType = "video/mp4";

            }


            /*
             * No Range Header
             *
             * Send complete file.
             */

            if (rangeHeader == null ||
                    rangeHeader.isEmpty()) {

                InputStream inputStream =
                        Files.newInputStream(videoPath);

                InputStreamResource resource =
                        new InputStreamResource(inputStream);


                return ResponseEntity
                        .ok()
                        .contentType(
                                MediaType.parseMediaType(
                                        contentType
                                )
                        )
                        .contentLength(fileSize)
                        .header(
                                HttpHeaders.ACCEPT_RANGES,
                                "bytes"
                        )
                        .body(resource);

            }


            /*
             * Range Header
             */

            HttpRange range;

            try {

                range =
                        HttpRange
                                .parseRanges(rangeHeader)
                                .get(0);

            } catch (Exception e) {

                return ResponseEntity
                        .status(
                                HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE
                        )
                        .header(
                                HttpHeaders.CONTENT_RANGE,
                                "bytes */" + fileSize
                        )
                        .build();

            }


            long start =
                    range.getRangeStart(fileSize);


            long end =
                    range.getRangeEnd(fileSize);


            if (start < 0) {

                start = 0;

            }


            if (end >= fileSize) {

                end = fileSize - 1;

            }


            if (start > end ||
                    start >= fileSize) {

                return ResponseEntity
                        .status(
                                HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE
                        )
                        .header(
                                HttpHeaders.CONTENT_RANGE,
                                "bytes */" + fileSize
                        )
                        .build();

            }


            long contentLength =
                    end - start + 1;


            InputStream inputStream =
                    Files.newInputStream(videoPath);


            long skipped = 0;

            while (skipped < start) {

                long current =
                        inputStream.skip(
                                start - skipped
                        );

                if (current <= 0) {

                    break;

                }

                skipped += current;

            }


            InputStreamResource resource =
                    new InputStreamResource(inputStream);


            return ResponseEntity
                    .status(HttpStatus.PARTIAL_CONTENT)
                    .contentType(
                            MediaType.parseMediaType(
                                    contentType
                            )
                    )
                    .contentLength(contentLength)
                    .header(
                            HttpHeaders.ACCEPT_RANGES,
                            "bytes"
                    )
                    .header(
                            HttpHeaders.CONTENT_RANGE,
                            "bytes "
                                    + start
                                    + "-"
                                    + end
                                    + "/"
                                    + fileSize
                    )
                    .body(resource);


        } catch (IOException e) {

            e.printStackTrace();

            return ResponseEntity
                    .status(
                            HttpStatus.INTERNAL_SERVER_ERROR
                    )
                    .build();

        }

    }

}