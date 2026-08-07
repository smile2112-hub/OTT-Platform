package com.cdac.flowflix.service;

import java.io.File;

public interface VideoService {

    String getVideoPath(Long movieId);

	File getVideo(Long movieId);

	File getTrailer(Long movieId);

}