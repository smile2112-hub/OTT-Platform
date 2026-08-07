package com.cdac.flowflix.serviceImpl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.repository.MovieRepository;
import com.cdac.flowflix.service.VideoService;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private MovieRepository movieRepository;


    @Override
    public String getVideoPath(Long movieId) {

        Movie movie =
                movieRepository
                        .findById(movieId)
                        .orElse(null);


        if (movie == null) {

            return null;

        }


        return movie.getVideo();

    }


	@Override
	public File getVideo(Long movieId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public File getTrailer(Long movieId) {
		// TODO Auto-generated method stub
		return null;
	}

}