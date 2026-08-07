package com.cdac.flowflix.dto;

import com.cdac.flowflix.model.Watchlist;

public class WatchlistDTO {

    private Long id;

    private Long movieId;

    private String movieName;

    private String genre;

    private String director;

    private Double rating;

    private Long totalViews;

    private String poster;

    private int duration;

    private int year;

    private boolean featured;

    private boolean trending;

    public WatchlistDTO() {

    }

    public WatchlistDTO(Watchlist watchlist) {

        this.id = watchlist.getId();

        if (watchlist.getMovie() != null) {

            this.movieId = watchlist.getMovie().getId();

            this.movieName = watchlist.getMovie().getName();

            this.genre = watchlist.getMovie().getGenre();

            this.director = watchlist.getMovie().getDirector();

            this.rating = watchlist.getMovie().getRating();

            this.totalViews = watchlist.getMovie().getTotalViews();

            this.poster = watchlist.getMovie().getPoster();

            this.duration = watchlist.getMovie().getDuration();

            this.year = watchlist.getMovie().getYear();

            this.featured = watchlist.getMovie().isFeatured();

            this.trending = watchlist.getMovie().isTrending();

        }

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

    public Long getTotalViews() {

        return totalViews;

    }

    public void setTotalViews(Long totalViews) {

        this.totalViews = totalViews;

    }

    public String getPoster() {

        return poster;

    }

    public void setPoster(String poster) {

        this.poster = poster;

    }

    public int getDuration() {

        return duration;

    }

    public void setDuration(int duration) {

        this.duration = duration;

    }

    public int getYear() {

        return year;

    }

    public void setYear(int year) {

        this.year = year;

    }

    public boolean isFeatured() {

        return featured;

    }

    public void setFeatured(boolean featured) {

        this.featured = featured;

    }

    public boolean isTrending() {

        return trending;

    }

    public void setTrending(boolean trending) {

        this.trending = trending;

    }

}