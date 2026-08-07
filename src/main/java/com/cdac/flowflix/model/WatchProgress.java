package com.cdac.flowflix.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "watch_progress")
public class WatchProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private Long watchedSeconds = 0L;

    private Long totalSeconds = 0L;

    private Double progress = 0.0;

    private boolean completed = false;

    private Long lastWatched = System.currentTimeMillis();

    public WatchProgress() {
    }

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

    public Long getWatchedSeconds() {
        return watchedSeconds;
    }

    public void setWatchedSeconds(Long watchedSeconds) {
        this.watchedSeconds = watchedSeconds;
    }

    public Long getTotalSeconds() {
        return totalSeconds;
    }

    public void setTotalSeconds(Long totalSeconds) {
        this.totalSeconds = totalSeconds;
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

    public Long getLastWatched() {
        return lastWatched;
    }

    public void setLastWatched(Long lastWatched) {
        this.lastWatched = lastWatched;
    }
}