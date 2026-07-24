package com.cdac.flowflix.dto;

public class FavoriteDTO {

    private String username;

    private Long movieId;

    public FavoriteDTO() {
    }

    public FavoriteDTO(String username, Long movieId) {
        this.username = username;
        this.movieId = movieId;
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

}