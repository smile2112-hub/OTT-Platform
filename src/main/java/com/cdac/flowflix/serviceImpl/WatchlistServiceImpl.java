package com.cdac.flowflix.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.flowflix.dto.WatchlistDTO;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.model.Watchlist;
import com.cdac.flowflix.repository.MovieRepository;
import com.cdac.flowflix.repository.WatchlistRepository;
import com.cdac.flowflix.service.UserService;
import com.cdac.flowflix.service.WatchlistService;

@Service
@Transactional
public class WatchlistServiceImpl
        implements WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserService userService;

    // ==========================================
    // ADD MOVIE
    // ==========================================

    @Override
    public String addToWatchlist(Long movieId) {

        User user = userService.getCurrentUser();

        if (user == null) {

            return "User Not Found";

        }

        Movie movie =
                movieRepository.findById(movieId)
                        .orElse(null);

        if (movie == null) {

            return "Movie Not Found";

        }

        if (watchlistRepository.existsByUserAndMovie(
                user,
                movie)) {

            return "Movie Already Exists In Watchlist";

        }

        Watchlist watchlist = new Watchlist();

        watchlist.setUser(user);

        watchlist.setMovie(movie);

        watchlistRepository.save(watchlist);

        return "Movie Added To Watchlist Successfully";

    }

    // ==========================================
    // REMOVE MOVIE
    // ==========================================

    @Override
    public String removeFromWatchlist(Long movieId) {

        User user = userService.getCurrentUser();

        if (user == null) {

            return "User Not Found";

        }

        Movie movie =
                movieRepository.findById(movieId)
                        .orElse(null);

        if (movie == null) {

            return "Movie Not Found";

        }

        if (!watchlistRepository.existsByUserAndMovie(
                user,
                movie)) {

            return "Movie Not Present In Watchlist";

        }

        watchlistRepository.deleteByUserAndMovie(
                user,
                movie);

        return "Movie Removed From Watchlist Successfully";

    }

    // ==========================================
    // GET WATCHLIST
    // ==========================================

    @Override
    @Transactional
    public List<WatchlistDTO> getMyWatchlist() {

        User user = userService.getCurrentUser();

        if (user == null) {

            return List.of();

        }

        return watchlistRepository

                .findByUser(user)

                .stream()

                .map(WatchlistDTO::new)

                .collect(Collectors.toList());

    }

    // ==========================================
    // CHECK EXISTS
    // ==========================================

    @Override
    public boolean existsInWatchlist(Long movieId) {

        User user = userService.getCurrentUser();

        if (user == null) {

            return false;

        }

        Movie movie =
                movieRepository.findById(movieId)
                        .orElse(null);

        if (movie == null) {

            return false;

        }

        return watchlistRepository.existsByUserAndMovie(
                user,
                movie);

    }

    // ==========================================
    // CLEAR WATCHLIST
    // ==========================================

    @Override
    public String clearWatchlist() {

        User user = userService.getCurrentUser();

        if (user == null) {

            return "User Not Found";

        }

        watchlistRepository.deleteByUser(user);

        return "Watchlist Cleared Successfully";

    }

    // ==========================================
    // COUNT
    // ==========================================

    @Override
    public Long getWatchlistCount() {

        User user = userService.getCurrentUser();

        if (user == null) {

            return 0L;

        }

        return watchlistRepository.countByUser(user);

    }

}