package com.cdac.flowflix.service;

import java.util.List;

import com.cdac.flowflix.dto.HomeDTO;
import com.cdac.flowflix.dto.MovieDTO;


// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.

public interface HomeService {

    HomeDTO getHomePage();

    MovieDTO movieDetails(Long id);

    List<MovieDTO> searchMovies(String keyword);

    List<MovieDTO> recommendedMovies(Long movieId);

}
