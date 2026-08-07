package com.cdac.flowflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.flowflix.model.Movie;
import com.cdac.flowflix.model.Review;
import com.cdac.flowflix.model.User;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // ==========================
    // FIND ALL REVIEWS OF A MOVIE
    // ==========================

    List<Review> findByMovieOrderByReviewDateDesc(
            Movie movie);

    // ==========================
    // FIND USER REVIEW
    // ==========================

    Review findByUserAndMovie(
            User user,
            Movie movie);

    // ==========================
    // CHECK REVIEW EXISTS
    // ==========================

    boolean existsByUserAndMovie(
            User user,
            Movie movie);

    // ==========================
    // DELETE REVIEW
    // ==========================

    @org.springframework.transaction.annotation.Transactional
    void deleteByUserAndMovie(User user, Movie movie);

    // ==========================
    // DELETE ALL REVIEWS OF A MOVIE
    // ==========================

    @org.springframework.transaction.annotation.Transactional
    void deleteByMovie(Movie movie);

    // ==========================
    // DELETE ALL REVIEWS BY A USER
    // ==========================

    @org.springframework.transaction.annotation.Transactional
    void deleteByUser(User user);

    // ==========================
    // COUNT REVIEWS OF A MOVIE
    // ==========================

    long countByMovie(
            Movie movie);

    // ==========================
    // GET ALL REVIEWS OF A USER
    // ==========================

    List<Review> findByUserOrderByReviewDateDesc(
            User user);

}