package com.cdac.flowflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.model.WatchHistory;

@Repository
public interface WatchHistoryRepository
        extends JpaRepository<WatchHistory, Long> {

    // Get complete watch history of a user
    List<WatchHistory> findByUserOrderByWatchedAtDesc(User user);

    // Continue Watching (movies not completed)
    List<WatchHistory> findByUserAndWatchPositionGreaterThanOrderByWatchedAtDesc(
            User user,
            Long watchPosition);

    // Find specific movie watched by user
    WatchHistory findByUserAndMovie(
            User user,
            Movie movie);

    // Delete complete history of a user
    void deleteByUser(User user);

    // Delete all history entries for a movie
    @org.springframework.transaction.annotation.Transactional
    void deleteByMovie(Movie movie);

    // Latest 10 watched movies
    List<WatchHistory> findTop10ByUserOrderByWatchedAtDesc(
            User user);

}