package com.cdac.flowflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.flowflix.model.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>{

    List<Favorite> findByUsername(String username);

    Favorite findByUsernameAndMovieId(String username, Long movieId);

}