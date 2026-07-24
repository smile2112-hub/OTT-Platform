package com.cdac.flowflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.WatchHistory;

public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Long>{

    List<WatchHistory> findByUsernameOrderByLastWatchedDesc(String username);

    List<WatchHistory> findByUsernameAndCompletedFalseOrderByLastWatchedDesc(String username);

    WatchHistory findByUsernameAndMovie(String username, Movie movie);

    void deleteByUsername(String username);

}