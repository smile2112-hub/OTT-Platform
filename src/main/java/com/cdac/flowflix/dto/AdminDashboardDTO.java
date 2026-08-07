package com.cdac.flowflix.dto;

import java.util.List;

public class AdminDashboardDTO {

    // ==========================================
    // USER
    // ==========================================

    private Long totalUsers;

    // ==========================================
    // MOVIES
    // ==========================================

    private Long totalMovies;

    private Long activeMovies;

    private Long featuredMovies;

    private Integer trendingMovies;

    // ==========================================
    // FAVORITES
    // ==========================================

    private Long totalFavorites;

    // ==========================================
    // REVIEWS
    // ==========================================

    private Long totalReviews;

    // ==========================================
    // VIEWS
    // ==========================================

    private Long totalViews;

    private String mostViewedMovie;

    private List<MovieDTO> topViewedMovies;

    // ==========================================
    // SUBSCRIPTIONS
    // ==========================================

    private Long totalSubscriptions;

    private Long activeSubscriptions;

    private Long basicSubscriptions;

    private Long standardSubscriptions;

    private Long premiumSubscriptions;

    private Long vipSubscriptions;

    private Double totalRevenue;

    public AdminDashboardDTO() {

    }

    // ==========================================
    // USER
    // ==========================================

    public Long getTotalUsers() {

        return totalUsers;

    }

    public void setTotalUsers(Long totalUsers) {

        this.totalUsers = totalUsers;

    }

    // ==========================================
    // MOVIES
    // ==========================================

    public Long getTotalMovies() {

        return totalMovies;

    }

    public void setTotalMovies(Long totalMovies) {

        this.totalMovies = totalMovies;

    }

    public Long getActiveMovies() {

        return activeMovies;

    }

    public void setActiveMovies(Long activeMovies) {

        this.activeMovies = activeMovies;

    }

    public Long getFeaturedMovies() {

        return featuredMovies;

    }

    public void setFeaturedMovies(Long featuredMovies) {

        this.featuredMovies = featuredMovies;

    }

    public Integer getTrendingMovies() {

        return trendingMovies;

    }

    public void setTrendingMovies(Integer trendingMovies) {

        this.trendingMovies = trendingMovies;

    }

    // ==========================================
    // FAVORITES
    // ==========================================

    public Long getTotalFavorites() {

        return totalFavorites;

    }

    public void setTotalFavorites(Long totalFavorites) {

        this.totalFavorites = totalFavorites;

    }

    // ==========================================
    // REVIEWS
    // ==========================================

    public Long getTotalReviews() {

        return totalReviews;

    }

    public void setTotalReviews(Long totalReviews) {

        this.totalReviews = totalReviews;

    }

    // ==========================================
    // VIEWS
    // ==========================================

    public Long getTotalViews() {

        return totalViews;

    }

    public void setTotalViews(Long totalViews) {

        this.totalViews = totalViews;

    }

    public String getMostViewedMovie() {

        return mostViewedMovie;

    }

    public void setMostViewedMovie(String mostViewedMovie) {

        this.mostViewedMovie = mostViewedMovie;

    }

    public List<MovieDTO> getTopViewedMovies() {

        return topViewedMovies;

    }

    public void setTopViewedMovies(List<MovieDTO> topViewedMovies) {

        this.topViewedMovies = topViewedMovies;

    }

    // ==========================================
    // SUBSCRIPTIONS
    // ==========================================

    public Long getTotalSubscriptions() {

        return totalSubscriptions;

    }

    public void setTotalSubscriptions(Long totalSubscriptions) {

        this.totalSubscriptions = totalSubscriptions;

    }

    public Long getActiveSubscriptions() {

        return activeSubscriptions;

    }

    public void setActiveSubscriptions(Long activeSubscriptions) {

        this.activeSubscriptions = activeSubscriptions;

    }

    public Long getBasicSubscriptions() {

        return basicSubscriptions;

    }

    public void setBasicSubscriptions(Long basicSubscriptions) {

        this.basicSubscriptions = basicSubscriptions;

    }

    public Long getStandardSubscriptions() {

        return standardSubscriptions;

    }

    public void setStandardSubscriptions(Long standardSubscriptions) {

        this.standardSubscriptions = standardSubscriptions;

    }

    public Long getPremiumSubscriptions() {

        return premiumSubscriptions;

    }

    public void setPremiumSubscriptions(Long premiumSubscriptions) {

        this.premiumSubscriptions = premiumSubscriptions;

    }

    public Long getVipSubscriptions() {

        return vipSubscriptions;

    }

    public void setVipSubscriptions(Long vipSubscriptions) {

        this.vipSubscriptions = vipSubscriptions;

    }

    public Double getTotalRevenue() {

        return totalRevenue;

    }

    public void setTotalRevenue(Double totalRevenue) {

        this.totalRevenue = totalRevenue;

    }

}