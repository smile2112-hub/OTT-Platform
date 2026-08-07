package com.cdac.flowflix.controller;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.flowflix.storage.StorageService;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final StorageService storageService;


    public FileController(
            StorageService storageService) {

        this.storageService =
                storageService;
    }


    // ==========================================
    // POSTER
    // ==========================================

    @GetMapping("/poster/{filename:.+}")
    public ResponseEntity<Resource> getPoster(
            @PathVariable String filename)
            throws IOException {

        byte[] data =
                storageService.getPoster(
                        filename
                );

        return ResponseEntity.ok()
                .contentType(
                        getMediaType(filename)
                )
                .body(
                        new ByteArrayResource(data)
                );
    }


    // ==========================================
    // BANNER
    // ==========================================

    @GetMapping("/banner/{filename:.+}")
    public ResponseEntity<Resource> getBanner(
            @PathVariable String filename)
            throws IOException {

        byte[] data =
                storageService.getBanner(
                        filename
                );

        return ResponseEntity.ok()
                .contentType(
                        getMediaType(filename)
                )
                .body(
                        new ByteArrayResource(data)
                );
    }


    // ==========================================
    // VIDEO
    // ==========================================

    @GetMapping("/video/{filename:.+}")
    public ResponseEntity<Resource> getVideo(
            @PathVariable String filename)
            throws IOException {

        byte[] data =
                storageService.getVideo(
                        filename
                );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.ACCEPT_RANGES,
                        "bytes"
                )
                .contentType(
                        getMediaType(filename)
                )
                .body(
                        new ByteArrayResource(data)
                );
    }


    // ==========================================
    // TRAILER
    // ==========================================

    @GetMapping("/trailer/{filename:.+}")
    public ResponseEntity<Resource> getTrailer(
            @PathVariable String filename)
            throws IOException {

        byte[] data =
                storageService.getTrailer(
                        filename
                );

        return ResponseEntity.ok()
                .contentType(
                        getMediaType(filename)
                )
                .body(
                        new ByteArrayResource(data)
                );
    }


    // ==========================================
    // MEDIA TYPE
    // ==========================================

    private MediaType getMediaType(
            String filename) {

        String name =
                filename.toLowerCase();


        if (name.endsWith(".jpg") ||
                name.endsWith(".jpeg")) {

            return MediaType.IMAGE_JPEG;
        }


        if (name.endsWith(".png")) {

            return MediaType.IMAGE_PNG;
        }


        if (name.endsWith(".gif")) {

            return MediaType.IMAGE_GIF;
        }


        if (name.endsWith(".webm")) {

            return MediaType.parseMediaType(
                    "video/webm"
            );
        }


        if (name.endsWith(".ogg")) {

            return MediaType.parseMediaType(
                    "video/ogg"
            );
        }


        return MediaType.parseMediaType(
                "video/mp4"
        );
    }
}