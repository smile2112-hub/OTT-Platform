package com.cdac.flowflix.repository;


// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.flowflix.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long>{

}
