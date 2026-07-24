package com.cdac.flowflix.storage;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.flowflix.util.FileUploadUtil;

@Service
public class StorageServiceImpl implements StorageService {

    private static final String POSTER_FOLDER =
            "uploads/posters/";

    private static final String BANNER_FOLDER =
            "uploads/banners/";

    private static final String VIDEO_FOLDER =
            "uploads/videos/";

    private static final String TRAILER_FOLDER =
            "uploads/trailers/";

    @Override
    public String savePoster(MultipartFile file)
            throws IOException {

        return FileUploadUtil.uploadFile(
                POSTER_FOLDER,
                file);

    }

    @Override
    public String saveBanner(MultipartFile file)
            throws IOException {

        return FileUploadUtil.uploadFile(
                BANNER_FOLDER,
                file);

    }

    @Override
    public String saveVideo(MultipartFile file)
            throws IOException {

        return FileUploadUtil.uploadFile(
                VIDEO_FOLDER,
                file);

    }

    @Override
    public String saveTrailer(MultipartFile file)
            throws IOException {

        return FileUploadUtil.uploadFile(
                TRAILER_FOLDER,
                file);

    }

}