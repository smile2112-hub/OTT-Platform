package com.cdac.flowflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.flowflix.model.History;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.User;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    // ==========================
    // FIND USER HISTORY
    // ==========================

    List<History> findByUserOrderByLastWatchedDesc(
            User user);

    // ==========================
    // FIND MOVIE HISTORY
    // ==========================

    History findByUserAndMovie(
            User user,
            Movie movie);

    // ==========================
    // CHECK HISTORY EXISTS
    // ==========================

    boolean existsByUserAndMovie(
            User user,
            Movie movie);

    // ==========================
    // DELETE HISTORY
    // ==========================

    void deleteByUserAndMovie(
            User user,
            Movie movie);

    // ==========================
    // DELETE BY MOVIE
    // ==========================

    @org.springframework.transaction.annotation.Transactional
    void deleteByMovie(Movie movie);

    // ==========================
    // DELETE ALL HISTORY BY USER
    // ==========================

    @org.springframework.transaction.annotation.Transactional
    void deleteByUser(User user);

    // ==========================
    // CONTINUE WATCHING
    // (Not Completed)
    // ==========================

    List<History> findByUserAndCompletedFalseOrderByLastWatchedDesc(
            User user);

    // ==========================
    // COMPLETED MOVIES
    // ==========================

    List<History> findByUserAndCompletedTrueOrderByLastWatchedDesc(
            User user);

    // ==========================
    // TOTAL HISTORY COUNT
    // ==========================

    long countByUser(
            User user);

}