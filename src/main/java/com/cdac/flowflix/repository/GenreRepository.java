package com.cdac.flowflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.flowflix.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long>{

}