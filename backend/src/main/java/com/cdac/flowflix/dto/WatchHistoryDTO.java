package com.cdac.flowflix.dto;

import com.cdac.flowflix.model.WatchHistory;

public class WatchHistoryDTO {

    private Long id;

    private String username;

    private Long movieId;

    private Long watchedDuration;

    private Double progress;

    private boolean completed;

    public WatchHistoryDTO() {
    }

    public WatchHistoryDTO(WatchHistory history) {

        this.id = history.getId();
        this.username = history.getUsername();
        this.movieId = history.getMovie().getId();
        this.watchedDuration = history.getWatchedDuration();
        this.progress = history.getProgress();
        this.completed = history.isCompleted();

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

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
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

}