package com.cdac.flowflix.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.flowflix.dto.HistoryDTO;
import com.cdac.flowflix.model.History;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.repository.HistoryRepository;
import com.cdac.flowflix.repository.MovieRepository;
import com.cdac.flowflix.service.HistoryService;
import com.cdac.flowflix.service.UserService;

@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserService userService;

    // ==========================
    // SAVE / UPDATE HISTORY
    // ==========================

    @Override
    public String saveHistory(
            Long movieId,
            Long watchPosition,
            Long totalDuration) {

        User user = userService.getCurrentUser();

        if (user == null) {

            return "User Not Logged In";

        }

        Movie movie =
                movieRepository.findById(movieId)
                        .orElse(null);

        if (movie == null) {

            return "Movie Not Found";

        }

        History history =
                historyRepository.findByUserAndMovie(
                        user,
                        movie);

        if (history == null) {

            history = new History();

            history.setUser(user);

            history.setMovie(movie);

        }

        history.setWatchPosition(watchPosition);

        history.setTotalDuration(totalDuration);

        double percentage = 0.0;

        if (totalDuration != null && totalDuration > 0) {

            percentage =
                    (watchPosition.doubleValue() * 100.0)
                            / totalDuration.doubleValue();

        }

        history.setWatchPercentage(percentage);

        history.setCompleted(percentage >= 90);

        history.setLastWatched(LocalDateTime.now());

        historyRepository.save(history);

        return "Watch History Saved Successfully";

    }

    // ==========================
    // GET MY HISTORY
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<HistoryDTO> getMyHistory() {

        User user = userService.getCurrentUser();

        if (user == null) {

            return List.of();

        }

        return historyRepository

                .findByUserOrderByLastWatchedDesc(user)

                .stream()

                .map(HistoryDTO::new)

                .collect(Collectors.toList());

    }

    // ==========================
    // CONTINUE WATCHING
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<HistoryDTO> getContinueWatching() {

        User user = userService.getCurrentUser();

        if (user == null) {

            return List.of();

        }

        return historyRepository

                .findByUserAndCompletedFalseOrderByLastWatchedDesc(user)

                .stream()

                .map(HistoryDTO::new)

                .collect(Collectors.toList());

    }

    // ==========================
    // COMPLETED MOVIES
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public List<HistoryDTO> getCompletedMovies() {

        User user = userService.getCurrentUser();

        if (user == null) {

            return List.of();

        }

        return historyRepository

                .findByUserAndCompletedTrueOrderByLastWatchedDesc(user)

                .stream()

                .map(HistoryDTO::new)

                .collect(Collectors.toList());

    }

    // ==========================
    // GET SINGLE HISTORY
    // ==========================

    @Override
    @Transactional(readOnly = true)
    public HistoryDTO getMovieHistory(Long movieId) {

        User user = userService.getCurrentUser();

        if (user == null) {

            return null;

        }

        Movie movie =
                movieRepository.findById(movieId)
                        .orElse(null);

        if (movie == null) {

            return null;

        }

        History history =
                historyRepository.findByUserAndMovie(
                        user,
                        movie);

        if (history == null) {

            return null;

        }

        return new HistoryDTO(history);

    }

    // ==========================
    // DELETE HISTORY
    // ==========================

    @Override
    public String deleteHistory(
            Long movieId) {

        User user = userService.getCurrentUser();

        if (user == null) {

            return "User Not Logged In";

        }

        Movie movie =
                movieRepository.findById(movieId)
                        .orElse(null);

        if (movie == null) {

            return "Movie Not Found";

        }

        History history =
                historyRepository.findByUserAndMovie(
                        user,
                        movie);

        if (history == null) {

            return "History Not Found";

        }

        historyRepository.delete(history);

        return "History Deleted Successfully";

    }

    // ==========================
    // CLEAR HISTORY
    // ==========================

    @Override
    public String clearHistory() {

        User user = userService.getCurrentUser();

        if (user == null) {

            return "User Not Logged In";

        }

        List<History> historyList =
                historyRepository.findByUserOrderByLastWatchedDesc(user);

        historyRepository.deleteAll(historyList);

        return "History Cleared Successfully";

    }

    // ==========================
    // CHECK HISTORY
    // ==========================

    @Override
    public boolean hasHistory(
            Long movieId) {

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

        return historyRepository.existsByUserAndMovie(
                user,
                movie);

    }

    // ==========================
    // HISTORY COUNT
    // ==========================

    @Override
    public Long getHistoryCount() {

        User user = userService.getCurrentUser();

        if (user == null) {

            return 0L;

        }

        return historyRepository.countByUser(user);

    }

}