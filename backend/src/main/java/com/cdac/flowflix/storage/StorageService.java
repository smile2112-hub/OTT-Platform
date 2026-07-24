package com.cdac.flowflix.storage;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String savePoster(MultipartFile file)
            throws IOException;

    String saveBanner(MultipartFile file)
            throws IOException;

    String saveVideo(MultipartFile file)
            throws IOException;

    String saveTrailer(MultipartFile file)
            throws IOException;

}