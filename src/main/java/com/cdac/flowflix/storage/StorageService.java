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

    byte[] getPoster(String filename)
            throws IOException;

    byte[] getBanner(String filename)
            throws IOException;

    byte[] getVideo(String filename)
            throws IOException;

    byte[] getTrailer(String filename)
            throws IOException;

    void deletePoster(String filename)
            throws IOException;

    void deleteBanner(String filename)
            throws IOException;

    void deleteVideo(String filename)
            throws IOException;

    void deleteTrailer(String filename)
            throws IOException;
}