package com.cdac.flowflix.dto;

import com.cdac.flowflix.model.Movie;

public class RecommendationDTO {

    private Long id;

    private String name;

    private String genre;

    private String director;

    private Double rating;

    private Long totalViews;

    private String poster;

    private boolean featured;

    private boolean trending;

    public RecommendationDTO() {

    }

    public RecommendationDTO(Movie movie) {

        this.id = movie.getId();

        this.name = movie.getName();

        this.genre = movie.getGenre();

        this.director = movie.getDirector();

        this.rating = movie.getRating();

        this.totalViews = movie.getTotalViews();

        this.poster = movie.getPoster();

        this.featured = movie.isFeatured();

        this.trending = movie.isTrending();

    }

    public Long getId() {

        return id;

    }

    public void setId(Long id) {

        this.id = id;

    }

    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;

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