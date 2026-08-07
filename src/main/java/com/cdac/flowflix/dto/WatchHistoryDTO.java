package com.cdac.flowflix.dto;

import java.time.LocalDateTime;

import com.cdac.flowflix.model.WatchHistory;

public class WatchHistoryDTO {

    private Long id;

    private Long movieId;

    private String movieName;

    private String poster;

    private String banner;

    private String genre;

    private Long watchPosition;

    private LocalDateTime watchedAt;

    public WatchHistoryDTO() {

    }

    public WatchHistoryDTO(WatchHistory history) {

        this.id = history.getId();

        if (history.getMovie() != null) {

            this.movieId = history.getMovie().getId();

            this.movieName = history.getMovie().getName();

            this.poster = history.getMovie().getPoster();

            this.banner = history.getMovie().getBanner();

            this.genre = history.getMovie().getGenre();

        }

        this.watchPosition = history.getWatchPosition();

        this.watchedAt = history.getWatchedAt();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getWatchPosition() {
        return watchPosition;
    }

    public void setWatchPosition(Long watchPosition) {
        this.watchPosition = watchPosition;
    }

    public LocalDateTime getWatchedAt() {
        return watchedAt;
    }

    public void setWatchedAt(LocalDateTime watchedAt) {
        this.watchedAt = watchedAt;
    }

}