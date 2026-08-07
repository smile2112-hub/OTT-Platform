package com.cdac.flowflix.model;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(
        name = "favorites",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "movie_id"})
        })
public class Favorite {

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
    // DATE
    // ==========================================

    private LocalDateTime addedDate;

    // ==========================================
    // CONSTRUCTORS
    // ==========================================

    public Favorite() {

    }

    public Favorite(Long id,
                    User user,
                    Movie movie,
                    LocalDateTime addedDate) {

        this.id = id;
        this.user = user;
        this.movie = movie;
        this.addedDate = addedDate;

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

    public LocalDateTime getAddedDate() {

        return addedDate;

    }

    public void setAddedDate(LocalDateTime addedDate) {

        this.addedDate = addedDate;

    }

}