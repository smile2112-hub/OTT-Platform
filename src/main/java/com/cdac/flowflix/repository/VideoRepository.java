package com.cdac.flowflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.flowflix.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long>{

}