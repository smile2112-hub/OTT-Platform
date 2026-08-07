package com.cdac.flowflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.flowflix.model.Projection;

public interface ProjectionRepository extends JpaRepository<Projection, Long>{

    List<Projection> findByMovieId(Long movieId);

}