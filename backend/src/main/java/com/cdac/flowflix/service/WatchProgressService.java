package com.cdac.flowflix.service;

import java.util.List;

import com.cdac.flowflix.dto.WatchProgressDTO;

public interface WatchProgressService {

    void saveProgress(
            Long movieId,
            Long watchedSeconds,
            Long totalSeconds);

    List<WatchProgressDTO> continueWatching();

    List<WatchProgressDTO> watchHistory();

    Long getResumeTime(Long movieId);

}