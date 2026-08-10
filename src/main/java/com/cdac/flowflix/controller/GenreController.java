/**
 * Author:  Mahesh Ganesh Kajale
 * Email:   maheshkajale2112@gmail.com
 * Project: FlowFlix OTT Platform
 */
package com.cdac.flowflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.service.MovieService;

@RestController

// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.

@RequestMapping("/api/genre")
@CrossOrigin("*")
public class GenreController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/{genre}")
    public List<MovieDTO> moviesByGenre(
            @PathVariable String genre) {

        return movieService.getMoviesByGenre(genre);

    }

}
