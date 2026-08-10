package com.cdac.flowflix.service;

import java.util.List;

import com.cdac.flowflix.dto.WatchHistoryDTO;

public interface WatchHistoryService {

    // ==========================
    // Save / Update Watch History
    // ==========================

    void saveWatchHistory(
            Long movieId,
            Long watchPosition);

    // ==========================
    // Complete Watch History

// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.

    // ==========================

    List<WatchHistoryDTO> getWatchHistory();

    // ==========================
    // Continue Watching
    // ==========================

    List<WatchHistoryDTO> getContinueWatching();

    // ==========================
    // Recently Watched
    // ==========================

    List<WatchHistoryDTO> getRecentlyWatched();

    // ==========================
    // Clear Watch History
    // ==========================

    void clearWatchHistory();

}
