package com.cdac.flowflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.service.MovieService;

@RestController
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