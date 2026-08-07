package com.cdac.flowflix.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

    private FileUploadUtil() {
    }

    private static final String ROOT_UPLOAD =
            "M:/OTT-Platform/uploads";

    public static String uploadFile(
            String folder,
            MultipartFile file)
            throws IOException {

        if (file == null || file.isEmpty()) {
            return null;
        }

        Path uploadPath = Paths.get(
                ROOT_UPLOAD,
                folder
        );

        Files.createDirectories(uploadPath);

        String originalFilename =
                file.getOriginalFilename();

        if (originalFilename == null ||
                originalFilename.isBlank()) {

            originalFilename = "file";
        }

        originalFilename =
                Paths.get(originalFilename)
                        .getFileName()
                        .toString();

        originalFilename =
                originalFilename.replaceAll("\\s+", "_");

        String filename =
                UUID.randomUUID()
                        + "_"
                        + originalFilename;

        Path filePath =
                uploadPath.resolve(filename);

        Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        System.out.println("=================================");
        System.out.println("FILE SAVED : " + filename);
        System.out.println("PATH       : " + filePath);
        System.out.println("=================================");

        return filename;
    }
}