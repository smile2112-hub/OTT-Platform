package com.cdac.flowflix.service;

import java.io.File;

public interface VideoService {

    File getVideo(Long movieId);

    File getTrailer(Long movieId);

}