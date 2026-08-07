package com.cdac.flowflix.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.flowflix.util.FileUploadUtil;

@Service
public class StorageServiceImpl implements StorageService {

    private static final String ROOT_UPLOAD =
            "M:/OTT-Platform/uploads";

    private static final String POSTER_FOLDER = "posters";
    private static final String BANNER_FOLDER = "banners";
    private static final String VIDEO_FOLDER = "videos";
    private static final String TRAILER_FOLDER = "trailers";

    // ==========================================
    // SAVE POSTER
    // ==========================================

    @Override
    public String savePoster(MultipartFile file) throws IOException {
        return FileUploadUtil.uploadFile(POSTER_FOLDER, file);
    }

    // ==========================================
    // SAVE BANNER
    // ==========================================

    @Override
    public String saveBanner(MultipartFile file) throws IOException {
        return FileUploadUtil.uploadFile(BANNER_FOLDER, file);
    }

    // ==========================================
    // SAVE VIDEO
    // ==========================================

    @Override
    public String saveVideo(MultipartFile file) throws IOException {
        return FileUploadUtil.uploadFile(VIDEO_FOLDER, file);
    }

    // ==========================================
    // SAVE TRAILER
    // ==========================================

    @Override
    public String saveTrailer(MultipartFile file) throws IOException {
        return FileUploadUtil.uploadFile(TRAILER_FOLDER, file);
    }

    // ==========================================
    // GET POSTER
    // ==========================================

    @Override
    public byte[] getPoster(String filename) throws IOException {
        return readFile(POSTER_FOLDER, filename);
    }

    // ==========================================
    // GET BANNER
    // ==========================================

    @Override
    public byte[] getBanner(String filename) throws IOException {
        return readFile(BANNER_FOLDER, filename);
    }

    // ==========================================
    // GET VIDEO
    // ==========================================

    @Override
    public byte[] getVideo(String filename) throws IOException {
        return readFile(VIDEO_FOLDER, filename);
    }

    // ==========================================
    // GET TRAILER
    // ==========================================

    @Override
    public byte[] getTrailer(String filename) throws IOException {
        return readFile(TRAILER_FOLDER, filename);
    }

    // ==========================================
    // DELETE POSTER
    // ==========================================

    @Override
    public void deletePoster(String filename) throws IOException {
        deleteFile(POSTER_FOLDER, filename);
    }

    // ==========================================
    // DELETE BANNER
    // ==========================================

    @Override
    public void deleteBanner(String filename) throws IOException {
        deleteFile(BANNER_FOLDER, filename);
    }

    // ==========================================
    // DELETE VIDEO
    // ==========================================

    @Override
    public void deleteVideo(String filename) throws IOException {
        deleteFile(VIDEO_FOLDER, filename);
    }

    // ==========================================
    // DELETE TRAILER
    // ==========================================

    @Override
    public void deleteTrailer(String filename) throws IOException {
        deleteFile(TRAILER_FOLDER, filename);
    }

    // ==========================================
    // READ FILE
    // ==========================================

    private byte[] readFile(String folder, String filename)
            throws IOException {

        if (filename == null || filename.trim().isEmpty()) {
            throw new IOException("Filename is empty.");
        }

        Path path = Paths.get(
                ROOT_UPLOAD,
                folder,
                filename
        );

        System.out.println("====================================");
        System.out.println("READ FILE : " + path.toAbsolutePath());
        System.out.println("EXISTS    : " + Files.exists(path));
        System.out.println("====================================");

        if (!Files.exists(path)) {
            throw new IOException("File not found : " + path);
        }

        return Files.readAllBytes(path);
    }

    // ==========================================
    // DELETE FILE
    // ==========================================

    private void deleteFile(String folder, String filename)
            throws IOException {

        if (filename == null || filename.trim().isEmpty()) {
            return;
        }

        Path path = Paths.get(
                ROOT_UPLOAD,
                folder,
                filename
        );

        Files.deleteIfExists(path);

        System.out.println("Deleted : " + path.toAbsolutePath());
    }
}