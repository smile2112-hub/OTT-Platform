package com.cdac.flowflix.serviceImpl;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.FavoriteDTO;
import com.cdac.flowflix.model.Favorite;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.repository.FavoriteRepository;
import com.cdac.flowflix.repository.MovieRepository;
import com.cdac.flowflix.service.FavoriteService;
import com.cdac.flowflix.service.UserService;

@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserService userService;

    // ==========================
    // ADD TO FAVORITES
    // ==========================

    @Override
    public String addFavorite(Long movieId) {

        User user = userService.getCurrentUser();

        if (user == null) {

            return "User Not Logged In";

        }

        Movie movie = movieRepository
                .findById(movieId)
                .orElse(null);

        if (movie == null) {

            return "Movie Not Found";

        }

        if (favoriteRepository.existsByUserAndMovie(user, movie)) {

            return "Movie Already Added To Favorites";

        }

        Favorite favorite = new Favorite();

        favorite.setUser(user);

        favorite.setMovie(movie);

        favorite.setAddedDate(LocalDateTime.now());

        favoriteRepository.save(favorite);

        return "Movie Added To Favorites";

    }

    // ==========================
    // REMOVE FAVORITE
    // ==========================

    @Override
    public String removeFavorite(Long movieId) {

        User user = userService.getCurrentUser();

        if (user == null) {

            return "User Not Logged In";

        }

        Movie movie = movieRepository
                .findById(movieId)
                .orElse(null);

        if (movie == null) {

            return "Movie Not Found";

        }

        Favorite favorite =
                favoriteRepository.findByUserAndMovie(user, movie);

        if (favorite == null) {

            return "Movie Not In Favorites";

        }

        favoriteRepository.delete(favorite);

        return "Movie Removed From Favorites";

    }
    // ==========================
    // GET MY FAVORITES
    // ==========================

    @Override
    @Transactional
    public List<FavoriteDTO> getMyFavorites() {

        User user = userService.getCurrentUser();

        if (user == null) {

            return List.of();

        }

        return favoriteRepository

                .findByUser(user)

                .stream()

                .map(FavoriteDTO::new)

                .collect(Collectors.toList());

    }

    // ==========================
    // CHECK FAVORITE
    // ==========================

    @Override
    public boolean isFavorite(Long movieId) {

        User user = userService.getCurrentUser();

        if (user == null) {

            return false;

        }

        Movie movie = movieRepository

                .findById(movieId)

                .orElse(null);

        if (movie == null) {

            return false;

        }

        return favoriteRepository.existsByUserAndMovie(
                user,
                movie);

    }

    // ==========================
    // TOTAL FAVORITES
    // ==========================

    @Override
    public long getTotalFavorites() {

        return favoriteRepository.count();

    }

    // ==========================
    // USER FAVORITES COUNT
    // ==========================

    @Override
    public long getUserFavoriteCount() {

        User user = userService.getCurrentUser();

        if (user == null) {

            return 0;

        }

        return favoriteRepository.countByUser(user);

    }

    // ==========================
    // MOVIE FAVORITES COUNT
    // ==========================

    @Override
    public long getMovieFavoriteCount(Long movieId) {

        Movie movie = movieRepository

                .findById(movieId)

                .orElse(null);

        if (movie == null) {

            return 0;

        }

        return favoriteRepository

                .findByUser(userService.getCurrentUser())

                .stream()

                .filter(f -> f.getMovie().getId().equals(movieId))

                .count();

    }

}