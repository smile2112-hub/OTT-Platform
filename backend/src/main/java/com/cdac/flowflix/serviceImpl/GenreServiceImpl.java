package com.cdac.flowflix.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.service.GenreService;
import com.cdac.flowflix.service.MovieService;

@Service
public class GenreServiceImpl extends GenreService {

    @Autowired
    private MovieService movieService;

    public List<MovieDTO> getMovies(String genre) {

        return movieService.getMoviesByGenre(genre);

    }

}