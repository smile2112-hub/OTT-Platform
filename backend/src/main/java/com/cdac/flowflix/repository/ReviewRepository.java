package com.cdac.flowflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.flowflix.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{

    List<Review> findByMovieId(Long movieId);

    List<Review> findByUsername(String username);

}