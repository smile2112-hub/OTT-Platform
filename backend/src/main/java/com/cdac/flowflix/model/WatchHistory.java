package com.cdac.flowflix.model;

import java.util.Date;

import javax.persistence.*;

@Entity
public class WatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private Long watchedDuration = 0L;

    private Double progress = 0.0;

    private boolean completed = false;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastWatched;

    public WatchHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Long getWatchedDuration() {
        return watchedDuration;
    }

    public void setWatchedDuration(Long watchedDuration) {
        this.watchedDuration = watchedDuration;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getLastWatched() {
        return lastWatched;
    }

    public void setLastWatched(Date lastWatched) {
        this.lastWatched = lastWatched;
    }
}