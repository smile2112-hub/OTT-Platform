package com.cdac.flowflix.model;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(
        name = "reviews",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = { "user_id", "movie_id" })
        })
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================
    // USER
    // ==========================

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // ==========================
    // MOVIE
    // ==========================

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    // ==========================
    // RATING (1-5)
    // ==========================

    @Column(nullable = false)
    private Integer rating;

    // ==========================
    // REVIEW
    // ==========================

    @Column(length = 2000)
    private String review;

    // ==========================
    // REVIEW DATE
    // ==========================

    private LocalDateTime reviewDate;

    public Review() {

    }

    // ==========================
    // GETTERS & SETTERS
    // ==========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

}