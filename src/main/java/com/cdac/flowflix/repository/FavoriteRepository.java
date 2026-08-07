package com.cdac.flowflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.flowflix.model.Favorite;
import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.User;

@Repository
public interface FavoriteRepository
        extends JpaRepository<Favorite, Long> {

    // ==========================================
    // USER FAVORITES
    // ==========================================

    List<Favorite> findByUser(User user);

    // ==========================================
    // CHECK FAVORITE
    // ==========================================

    boolean existsByUserAndMovie(
            User user,
            Movie movie);

    // ==========================================
    // FIND FAVORITE
    // ==========================================

    Favorite findByUserAndMovie(
            User user,
            Movie movie);

    // ==========================================
    // REMOVE FAVORITE
    // ==========================================

    @Transactional
    void deleteByUserAndMovie(
            User user,
            Movie movie);

    // ==========================================
    // DELETE ALL FAVORITES FOR A MOVIE
    // ==========================================

    @Transactional
    void deleteByMovie(Movie movie);

    // ==========================================
    // DELETE ALL FAVORITES FOR A USER
    // ==========================================

    @Transactional
    void deleteByUser(User user);

    // ==========================================
    // USER FAVORITE COUNT
    // ==========================================

    long countByUser(User user);

    // ==========================================
    // MOVIE FAVORITE COUNT
    // ==========================================

    long countByMovie(Movie movie);

}