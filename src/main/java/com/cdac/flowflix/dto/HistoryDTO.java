package com.cdac.flowflix.dto;

import java.time.LocalDateTime;

import com.cdac.flowflix.model.History;
import com.cdac.flowflix.model.Movie;

public class HistoryDTO {

    private Long historyId;

    private Long movieId;

    private String movieName;

    private String poster;

    private String banner;

    private String genre;

    private String director;

    private Double rating;

    private Long watchPosition;

    private Long totalDuration;

    private Double watchPercentage;

    private Boolean completed;

    private LocalDateTime lastWatched;

    public HistoryDTO() {

    }

    public HistoryDTO(History history) {

        this.historyId = history.getId();

        Movie movie = history.getMovie();

        if (movie != null) {

            this.movieId = movie.getId();

            this.movieName = movie.getName();

            this.poster = movie.getPoster();

            this.banner = movie.getBanner();

            this.genre = movie.getGenre();

            this.director = movie.getDirector();

            this.rating = movie.getRating();

        }

        this.watchPosition = history.getWatchPosition();

        this.totalDuration = history.getTotalDuration();

        this.watchPercentage = history.getWatchPercentage();

        this.completed = history.getCompleted();

        this.lastWatched = history.getLastWatched();

    }

    // ==========================
    // GETTERS & SETTERS
    // ==========================

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
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

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
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