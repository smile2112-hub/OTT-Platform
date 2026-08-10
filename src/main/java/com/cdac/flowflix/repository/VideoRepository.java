/**
 * Author:  Mahesh Ganesh Kajale
 * Email:   maheshkajale2112@gmail.com
 * Project: FlowFlix OTT Platform
 */
package com.cdac.flowflix.repository;


// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.flowflix.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long>{

}
