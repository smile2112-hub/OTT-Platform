/**
 * Author:  Mahesh Ganesh Kajale
 * Email:   maheshkajale2112@gmail.com
 * Project: FlowFlix OTT Platform
 */
package com.cdac.flowflix.service;

import java.util.List;

import com.cdac.flowflix.dto.WatchProgressDTO;

public interface WatchProgressService {

// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.


    void saveProgress(
            Long movieId,
            Long watchedSeconds,
            Long totalSeconds);

    List<WatchProgressDTO> continueWatching();

    List<WatchProgressDTO> watchHistory();

    Long getResumeTime(Long movieId);

}
