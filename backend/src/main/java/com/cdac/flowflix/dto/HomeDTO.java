package com.cdac.flowflix.dto;

import java.util.List;

public class HomeDTO {

    private List<MovieDTO> featuredMovies;

    private List<MovieDTO> trendingMovies;

    private List<MovieDTO> latestMovies;

    private List<MovieDTO> recommendedMovies;

    private List<WatchProgressDTO> continueWatching;

    public HomeDTO() {
    }

    public List<MovieDTO> getFeaturedMovies() {
        return featuredMovies;
    }

    public void setFeaturedMovies(List<MovieDTO> featuredMovies) {
        this.featuredMovies = featuredMovies;
    }

    public List<MovieDTO> getTrendingMovies() {
        return trendingMovies;
    }

    public void setTrendingMovies(List<MovieDTO> trendingMovies) {
        this.trendingMovies = trendingMovies;
    }

    public List<MovieDTO> getLatestMovies() {
        return latestMovies;
    }

    public void setLatestMovies(List<MovieDTO> latestMovies) {
        this.latestMovies = latestMovies;
    }

    public List<MovieDTO> getRecommendedMovies() {
        return recommendedMovies;
    }

    public void setRecommendedMovies(List<MovieDTO> recommendedMovies) {
        this.recommendedMovies = recommendedMovies;
    }

    public List<WatchProgressDTO> getContinueWatching() {
        return continueWatching;
    }

    public void setContinueWatching(List<WatchProgressDTO> continueWatching) {
        this.continueWatching = continueWatching;
    }

}