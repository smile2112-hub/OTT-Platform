package com.cdac.flowflix;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.flowflix.util.FileUploadUtil;

class FileUploadUtilTest {

    @Test
    void shouldReturnNullForEmptyFile() throws IOException {
        MultipartFile file = new MockMultipartFile("file", "", "text/plain", new byte[0]);

        assertNull(FileUploadUtil.uploadFile("test-upload", file));
    }

    @Test
    void shouldUploadFileAndCleanup() throws IOException {
        MultipartFile file = new MockMultipartFile("file", "sample.txt", "text/plain", "content".getBytes());
        String filename = FileUploadUtil.uploadFile("test-upload", file);

        assertNotNull(filename);
        Path path = Paths.get("M:/OTT-Platform/uploads/test-upload", filename);
        assertTrue(Files.exists(path));

        Files.deleteIfExists(path);
    }
}
