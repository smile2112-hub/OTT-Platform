/**
 * Author:  Mahesh Ganesh Kajale
 * Email:   maheshkajale2112@gmail.com
 * Project: FlowFlix OTT Platform
 */
package com.cdac.flowflix.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.service.GenreService;

// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.

import com.cdac.flowflix.service.MovieService;

@Service
public class GenreServiceImpl extends GenreService {

    @Autowired
    private MovieService movieService;

    public List<MovieDTO> getMovies(String genre) {

        return movieService.getMoviesByGenre(genre);

    }

}
