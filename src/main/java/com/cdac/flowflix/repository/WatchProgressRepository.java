package com.cdac.flowflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.model.WatchProgress;

@Repository
public interface WatchProgressRepository extends JpaRepository<WatchProgress, Long> {

    WatchProgress findByUserAndMovie(User user, Movie movie);

    List<WatchProgress> findByUserOrderByLastWatchedDesc(User user);

    @Transactional
    void deleteByMovie(Movie movie);

    @Transactional
    void deleteByUser(User user);
}