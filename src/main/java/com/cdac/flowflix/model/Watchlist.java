package com.cdac.flowflix.model;

import javax.persistence.*;

@Entity
@Table(name = "watchlist")
public class Watchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================================
    // USER
    // ==========================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // ==========================================
    // MOVIE
    // ==========================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    // ==========================================
    // CONSTRUCTORS
    // ==========================================

    public Watchlist() {

    }

    public Watchlist(Long id,
                     User user,
                     Movie movie) {

        this.id = id;
        this.user = user;
        this.movie = movie;

    }

    // ==========================================
    // GETTERS & SETTERS
    // ==========================================

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

}