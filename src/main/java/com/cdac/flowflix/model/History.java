package com.cdac.flowflix.model;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================
    // USER
    // ==========================

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // ==========================
    // MOVIE
    // ==========================

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    // ==========================
    // WATCH POSITION (Seconds)
    // ==========================

    private Long watchPosition;

    // ==========================
    // TOTAL DURATION (Seconds)
    // ==========================

    private Long totalDuration;

    // ==========================
    // WATCH PERCENTAGE
    // ==========================

    private Double watchPercentage;

    // ==========================
    // COMPLETED
    // ==========================

    private Boolean completed;

    // ==========================
    // LAST WATCHED
    // ==========================

    private LocalDateTime lastWatched;

    public History() {

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

    public Long getWatchPosition() {
        return watchPosition;
    }

    public void setWatchPosition(Long watchPosition) {
        this.watchPosition = watchPosition;
    }

    public Long getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Long totalDuration) {
        this.totalDuration = totalDuration;
    }

    public Double getWatchPercentage() {
        return watchPercentage;
    }

    public void setWatchPercentage(Double watchPercentage) {
        this.watchPercentage = watchPercentage;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getLastWatched() {
        return lastWatched;
    }

    public void setLastWatched(LocalDateTime lastWatched) {
        this.lastWatched = lastWatched;
    }

}