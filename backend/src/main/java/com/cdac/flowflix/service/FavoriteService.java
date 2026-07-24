package com.cdac.flowflix.service;

import java.util.List;

import com.cdac.flowflix.dto.FavoriteDTO;
import com.cdac.flowflix.dto.MovieDTO;

public interface FavoriteService {

    String addToFavorite(FavoriteDTO dto);

    String removeFromFavorite(FavoriteDTO dto);

    List<MovieDTO> getFavorites(String username);

    boolean isFavorite(String username, Long movieId);

}