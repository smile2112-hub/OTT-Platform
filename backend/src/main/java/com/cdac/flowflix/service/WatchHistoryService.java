package com.cdac.flowflix.service;

import java.util.List;

import com.cdac.flowflix.dto.WatchHistoryDTO;
import com.cdac.flowflix.model.WatchHistory;

public interface WatchHistoryService {

    WatchHistory saveHistory(WatchHistoryDTO dto);

    WatchHistory updateProgress(
            String username,
            Long movieId,
            Long watchedDuration,
            Double progress,
            boolean completed);

    List<WatchHistory> getContinueWatching(String username);

    List<WatchHistory> getHistory(String username);

    WatchHistory getResumePosition(String username, Long movieId);

    void deleteHistory(Long id);

    void clearHistory(String username);

}