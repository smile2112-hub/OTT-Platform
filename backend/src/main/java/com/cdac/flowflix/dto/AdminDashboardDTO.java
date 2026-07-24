package com.cdac.flowflix.dto;

public class AdminDashboardDTO {

    private long totalUsers;

    private long totalMovies;

    private long activeMovies;

    private long featuredMovies;

    private long totalFavorites;

    private long totalReviews;

    private long totalViews;

    private String trendingMovie;

    public AdminDashboardDTO() {
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalMovies() {
        return totalMovies;
    }

    public void setTotalMovies(long totalMovies) {
        this.totalMovies = totalMovies;
    }

    public long getActiveMovies() {
        return activeMovies;
    }

    public void setActiveMovies(long activeMovies) {
        this.activeMovies = activeMovies;
    }

    public long getFeaturedMovies() {
        return featuredMovies;
    }

    public void setFeaturedMovies(long featuredMovies) {
        this.featuredMovies = featuredMovies;
    }

    public long getTotalFavorites() {
        return totalFavorites;
    }

    public void setTotalFavorites(long totalFavorites) {
        this.totalFavorites = totalFavorites;
    }

    public long getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(long totalReviews) {
        this.totalReviews = totalReviews;
    }

    public long getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(long totalViews) {
        this.totalViews = totalViews;
    }

    public String getTrendingMovie() {
        return trendingMovie;
    }

    public void setTrendingMovie(String trendingMovie) {
        this.trendingMovie = trendingMovie;
    }

}