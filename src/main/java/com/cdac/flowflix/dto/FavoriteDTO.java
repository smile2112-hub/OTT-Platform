package com.cdac.flowflix.dto;

import com.cdac.flowflix.model.Favorite;
import com.cdac.flowflix.model.Movie;

public class FavoriteDTO {

    private Long favoriteId;

    private Long movieId;

    private String movieName;

    private String poster;

    private String banner;

    private String genre;

    private String director;

    private Double rating;

    private Boolean featured;

    private Boolean trending;

    public FavoriteDTO() {

    }

    public FavoriteDTO(Favorite favorite) {

        this.favoriteId = favorite.getId();

        Movie movie = favorite.getMovie();

        if (movie != null) {

            this.movieId = movie.getId();

            this.movieName = movie.getName();

            this.poster = movie.getPoster();

            this.banner = movie.getBanner();

            this.genre = movie.getGenre();

            this.director = movie.getDirector();

            this.rating = movie.getRating();

            this.featured = movie.isFeatured();

            this.trending = movie.isTrending();

        }

    }

    public Long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Long favoriteId) {
        this.favoriteId = favoriteId;
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

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Boolean getTrending() {
        return trending;
    }

    public void setTrending(Boolean trending) {
        this.trending = trending;
    }

}