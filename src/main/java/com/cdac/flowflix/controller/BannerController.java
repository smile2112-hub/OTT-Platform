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

@RequestMapping("/api/banner")
@CrossOrigin("*")
public class BannerController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<MovieDTO> getBannerMovies() {

        return movieService.getFeaturedMovies();

    }

}
