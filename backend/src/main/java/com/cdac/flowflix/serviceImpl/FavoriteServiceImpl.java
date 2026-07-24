package com.cdac.flowflix.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.FavoriteDTO;
import com.cdac.flowflix.dto.MovieDTO;
import com.cdac.flowflix.model.Favorite;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.repository.FavoriteRepository;
import com.cdac.flowflix.repository.MovieRepository;
import com.cdac.flowflix.service.FavoriteService;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public String addToFavorite(FavoriteDTO dto) {

        Favorite favorite = favoriteRepository.findByUsernameAndMovieId(
                dto.getUsername(),
                dto.getMovieId());

        if (favorite != null) {
            return "Already Added";
        }

        favorite = new Favorite();

        favorite.setUsername(dto.getUsername());
        favorite.setMovieId(dto.getMovieId());

        favoriteRepository.save(favorite);

        return "Added Successfully";
    }

    @Override
    public String removeFromFavorite(FavoriteDTO dto) {

        Favorite favorite = favoriteRepository.findByUsernameAndMovieId(
                dto.getUsername(),
                dto.getMovieId());

        if (favorite == null) {
            return "Movie Not Found";
        }

        favoriteRepository.delete(favorite);

        return "Removed Successfully";
    }

    @Override
    public List<MovieDTO> getFavorites(String username) {

        List<MovieDTO> list = new ArrayList<>();

        List<Favorite> favorites =
                favoriteRepository.findByUsername(username);

        for (Favorite favorite : favorites) {

            Movie movie = movieRepository
                    .findById(favorite.getMovieId())
                    .orElse(null);

            if (movie != null) {
                list.add(new MovieDTO(movie));
            }
        }

        return list;
    }

    @Override
    public boolean isFavorite(String username, Long movieId) {

        return favoriteRepository
                .findByUsernameAndMovieId(username, movieId) != null;

    }

}