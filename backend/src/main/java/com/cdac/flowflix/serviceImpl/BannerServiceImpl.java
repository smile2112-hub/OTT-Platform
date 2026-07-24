package com.cdac.flowflix.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.service.BannerService;
import com.cdac.flowflix.service.MovieService;

@Service
public class BannerServiceImpl extends BannerService {

    @Autowired
    private MovieService movieService;

    public List<MovieDTO> getBannerMovies() {

        return movieService.getFeaturedMovies();

    }

}