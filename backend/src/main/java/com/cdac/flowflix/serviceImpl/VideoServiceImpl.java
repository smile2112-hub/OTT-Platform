package com.cdac.flowflix.serviceImpl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.service.MovieService;
import com.cdac.flowflix.service.VideoService;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private MovieService movieService;

    @Override
    public File getVideo(Long movieId) {

        Movie movie = movieService.findOne(movieId);

        if (movie == null) {
            return null;
        }

        return new File("uploads/videos/" + movie.getVideo());

    }

    @Override
    public File getTrailer(Long movieId) {

        Movie movie = movieService.findOne(movieId);

        if (movie == null) {
            return null;
        }

        return new File("uploads/trailers/" + movie.getTrailer());

    }

}