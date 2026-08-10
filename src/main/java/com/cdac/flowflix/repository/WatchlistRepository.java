/**
 * Author:  Mahesh Ganesh Kajale
 * Email:   maheshkajale2112@gmail.com
 * Project: FlowFlix OTT Platform
 */
package com.cdac.flowflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.model.Watchlist;

@Repository
public interface WatchlistRepository
        extends JpaRepository<Watchlist, Long> {

    // ==========================================
    // GET USER WATCHLIST
    // ==========================================

    List<Watchlist> findByUser(User user);

    // ==========================================
    // FIND PARTICULAR MOVIE IN WATCHLIST
    // ==========================================

    Watchlist findByUserAndMovie(
            User user,
            Movie movie);

    // ==========================================

// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.

    // CHECK MOVIE EXISTS
    // ==========================================

    boolean existsByUserAndMovie(
            User user,
            Movie movie);

    // ==========================================
    // REMOVE MOVIE
    // ==========================================

    @Transactional
    void deleteByUserAndMovie(
            User user,
            Movie movie);

    // ==========================================
    // DELETE ALL WATCHLIST ENTRIES FOR A MOVIE
    // ==========================================

    @Transactional
    void deleteByMovie(Movie movie);

    // ==========================================
    // CLEAR WATCHLIST
    // ==========================================

    @Transactional
    void deleteByUser(User user);

    // ==========================================
    // COUNT WATCHLIST
    // ==========================================

    Long countByUser(User user);

}
