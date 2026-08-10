/**
 * Author:  Mahesh Ganesh Kajale
 * Email:   maheshkajale2112@gmail.com
 * Project: FlowFlix OTT Platform
 */
package com.cdac.flowflix.repository;

import java.util.List;


// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.flowflix.model.Projection;

public interface ProjectionRepository extends JpaRepository<Projection, Long>{

    List<Projection> findByMovieId(Long movieId);

}
