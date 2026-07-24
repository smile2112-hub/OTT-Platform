package com.cdac.flowflix.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.WatchHistoryDTO;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.WatchHistory;
import com.cdac.flowflix.repository.WatchHistoryRepository;
import com.cdac.flowflix.service.MovieService;
import com.cdac.flowflix.service.WatchHistoryService;

@Service
public class WatchHistoryServiceImpl implements WatchHistoryService {

    @Autowired
    private WatchHistoryRepository watchHistoryRepository;

    @Autowired
    private MovieService movieService;

    @Override
    public WatchHistory saveHistory(WatchHistoryDTO dto) {

        Movie movie = movieService.findOne(dto.getMovieId());

        WatchHistory history =
                watchHistoryRepository.findByUsernameAndMovie(
                        dto.getUsername(),
                        movie);

        if (history == null) {

            history = new WatchHistory();
            history.setUsername(dto.getUsername());
            history.setMovie(movie);

        }

        history.setWatchedDuration(dto.getWatchedDuration());
        history.setProgress(dto.getProgress());
        history.setCompleted(dto.isCompleted());
        history.setLastWatched(new Date());

        return watchHistoryRepository.save(history);
    }

    @Override
    public WatchHistory updateProgress(String username,
                                       Long movieId,
                                       Long watchedDuration,
                                       Double progress,
                                       boolean completed) {

        Movie movie = movieService.findOne(movieId);

        WatchHistory history =
                watchHistoryRepository.findByUsernameAndMovie(username, movie);

        if (history == null) {

            history = new WatchHistory();
            history.setUsername(username);
            history.setMovie(movie);

        }

        history.setWatchedDuration(watchedDuration);
        history.setProgress(progress);
        history.setCompleted(completed);
        history.setLastWatched(new Date());

        return watchHistoryRepository.save(history);
    }

    @Override
    public List<WatchHistory> getContinueWatching(String username) {

        return watchHistoryRepository
                .findByUsernameAndCompletedFalseOrderByLastWatchedDesc(username);

    }

    @Override
    public List<WatchHistory> getHistory(String username) {

        return watchHistoryRepository
                .findByUsernameOrderByLastWatchedDesc(username);

    }

    @Override
    public WatchHistory getResumePosition(String username, Long movieId) {

        Movie movie = movieService.findOne(movieId);

        return watchHistoryRepository
                .findByUsernameAndMovie(username, movie);

    }

    @Override
    public void deleteHistory(Long id) {

        watchHistoryRepository.deleteById(id);

    }

    @Override
    public void clearHistory(String username) {

        watchHistoryRepository.deleteByUsername(username);

    }

}