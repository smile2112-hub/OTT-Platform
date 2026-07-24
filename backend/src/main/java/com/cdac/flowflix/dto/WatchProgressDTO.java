package com.cdac.flowflix.dto;

import com.cdac.flowflix.model.WatchProgress;

public class WatchProgressDTO {

    private Long movieId;

    private String movieName;

    private String poster;

    private Double progress;

    private Long watchedSeconds;

    private Long totalSeconds;

    private boolean completed;

    public WatchProgressDTO() {
    }

    public WatchProgressDTO(WatchProgress progress) {

        this.movieId = progress.getMovie().getId();
        this.movieName = progress.getMovie().getName();
        this.poster = progress.getMovie().getPoster();
        this.progress = progress.getProgress();
        this.watchedSeconds = progress.getWatchedSeconds();
        this.totalSeconds = progress.getTotalSeconds();
        this.completed = progress.isCompleted();

    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}