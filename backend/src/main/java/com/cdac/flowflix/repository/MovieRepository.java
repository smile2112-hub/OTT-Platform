package com.cdac.flowflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.flowflix.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
