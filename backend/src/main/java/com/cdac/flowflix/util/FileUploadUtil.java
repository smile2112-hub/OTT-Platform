package com.cdac.flowflix.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

    private FileUploadUtil() {
    }

    public static String uploadFile(String uploadDir,
                                    MultipartFile file)
            throws IOException {

        if (file == null || file.isEmpty()) {
            return null;
        }

        Files.createDirectories(Paths.get(uploadDir));

        String filename =
                System.currentTimeMillis()
                        + "_"
                        + file.getOriginalFilename();

        Path path = Paths.get(uploadDir, filename);

        Files.write(path, file.getBytes());

        return filename;
    }

}