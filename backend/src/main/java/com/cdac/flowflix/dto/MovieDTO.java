package com.cdac.flowflix.dto;

import com.cdac.flowflix.model.Movie;

public class MovieDTO {

	private Long id;

	private String name;
	private String director;
	private String description;
	private String genre;
	private String actors;
	private String distributor;

	private int year;
	private int duration;

	// OTT Files
	private String poster;
	private String banner;
	private String video;
	private String trailer;

	private String videoSize;

	// Statistics
	private Long totalViews;

	private Double rating;

	private boolean featured;

	private boolean active;

	public MovieDTO() {

	}

	public MovieDTO(Movie movie) {

		this.id = movie.getId();
		this.name = movie.getName();
		this.director = movie.getDirector();
		this.description = movie.getDescription();
		this.genre = movie.getGenre();
		this.actors = movie.getActors();
		this.distributor = movie.getDistributor();
		this.year = movie.getYear();
		this.duration = movie.getDuration();

		this.poster = movie.getPoster();
		this.banner = movie.getBanner();
		this.video = movie.getVideo();
		this.trailer = movie.getTrailer();

		this.videoSize = movie.getVideoSize();

		this.totalViews = movie.getTotalViews();

		this.rating = movie.getRating();

		this.featured = movie.isFeatured();

		this.active = movie.isActive();
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

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
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

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getTrailer() {
		return trailer;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	public String getVideoSize() {
		return videoSize;
	}

	public void setVideoSize(String videoSize) {
		this.videoSize = videoSize;
	}

	public Long getTotalViews() {
		return totalViews;
	}

	public void setTotalViews(Long totalViews) {
		this.totalViews = totalViews;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public boolean isFeatured() {
		return featured;
	}

	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}