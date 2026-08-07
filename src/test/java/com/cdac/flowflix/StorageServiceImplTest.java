package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.flowflix.storage.StorageServiceImpl;

class StorageServiceImplTest {

    private final StorageServiceImpl storageService = new StorageServiceImpl();

    @Test
    void shouldSaveAndReadPoster() throws IOException {
        MultipartFile file = new MockMultipartFile("poster", "poster.png", "image/png", "hello".getBytes());
        String storedFilename = storageService.savePoster(file);

        assertNotNull(storedFilename);
        byte[] data = storageService.getPoster(storedFilename);
        assertArrayEquals("hello".getBytes(), data);

        storageService.deletePoster(storedFilename);
        Path path = Paths.get("M:/OTT-Platform/uploads/posters", storedFilename);
        assertFalse(Files.exists(path));
    }

    @Test
    void shouldDeleteBannerWithoutErrorForMissingFile() throws IOException {
        storageService.deleteBanner("does-not-exist.png");
    }
}
