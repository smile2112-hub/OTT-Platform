package com.cdac.flowflix.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.flowflix.dto.WatchHistoryDTO;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.model.WatchHistory;
import com.cdac.flowflix.repository.MovieRepository;
import com.cdac.flowflix.repository.WatchHistoryRepository;
import com.cdac.flowflix.service.MovieService;
import com.cdac.flowflix.service.UserService;
import com.cdac.flowflix.service.WatchHistoryService;

@Service
@Transactional
public class WatchHistoryServiceImpl
        implements WatchHistoryService {

    @Autowired
    private WatchHistoryRepository watchHistoryRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieService movieService;

    // ==========================
    // SAVE WATCH HISTORY
    // ==========================

    @Override
    public void saveWatchHistory(
            Long movieId,
            Long watchPosition) {

        User user =
                userService.getCurrentUser();

        if (user == null) {

            return;

        }

        Movie movie =
                movieRepository.findById(movieId)
                        .orElse(null);

        if (movie == null) {

            return;

        }

        WatchHistory history =
                watchHistoryRepository.findByUserAndMovie(
                        user,
                        movie);

        if (history == null) {

            history = new WatchHistory();

            history.setUser(user);

            history.setMovie(movie);

        }

        history.setWatchPosition(watchPosition);

        history.setWatchedAt(LocalDateTime.now());

        watchHistoryRepository.save(history);

        movieService.incrementViews(movieId);

    }

    // ==========================
    // GET COMPLETE HISTORY
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<WatchHistoryDTO> getWatchHistory() {

        User user =
                userService.getCurrentUser();

        if (user == null) {

            return List.of();

        }

        return watchHistoryRepository

                .findByUserOrderByWatchedAtDesc(user)

                .stream()

                .map(WatchHistoryDTO::new)

                .collect(Collectors.toList());

    }
    
    // ==========================
    // CONTINUE WATCHING
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<WatchHistoryDTO> getContinueWatching() {

        User user =
                userService.getCurrentUser();

        if (user == null) {

            return List.of();

        }

        return watchHistoryRepository

                .findByUserAndWatchPositionGreaterThanOrderByWatchedAtDesc(

                        user,

                        0L)

                .stream()

                .map(WatchHistoryDTO::new)

                .collect(Collectors.toList());

    }

    // ==========================
    // RECENTLY WATCHED
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<WatchHistoryDTO> getRecentlyWatched() {

        User user =
                userService.getCurrentUser();

        if (user == null) {

            return List.of();

        }

        return watchHistoryRepository

                .findTop10ByUserOrderByWatchedAtDesc(user)

                .stream()

                .map(WatchHistoryDTO::new)

                .collect(Collectors.toList());

    }

    // ==========================
    // CLEAR WATCH HISTORY
    // ==========================

    @Override
    public void clearWatchHistory() {

        User user =
                userService.getCurrentUser();

        if (user == null) {

            return;

        }

        watchHistoryRepository.deleteByUser(user);

    }

}
