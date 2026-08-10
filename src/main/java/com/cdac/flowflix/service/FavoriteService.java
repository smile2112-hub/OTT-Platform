/**
 * Author:  Mahesh Ganesh Kajale
 * Email:   maheshkajale2112@gmail.com
 * Project: FlowFlix OTT Platform
 */
package com.cdac.flowflix.service;

import java.util.List;

import com.cdac.flowflix.dto.FavoriteDTO;

public interface FavoriteService {

    // ==========================
    // ADD TO FAVORITES
    // ==========================

    String addFavorite(Long movieId);

    // ==========================
    // REMOVE FROM FAVORITES
    // ==========================

    String removeFavorite(Long movieId);

    // ==========================
    // GET MY FAVORITES
    // ==========================

// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.


    List<FavoriteDTO> getMyFavorites();

    // ==========================
    // CHECK FAVORITE
    // ==========================

    boolean isFavorite(Long movieId);

    // ==========================
    // TOTAL FAVORITES
    // ==========================

    long getTotalFavorites();

    // ==========================
    // USER FAVORITES COUNT
    // ==========================

    long getUserFavoriteCount();

    // ==========================
    // MOVIE FAVORITES COUNT
    // ==========================

    long getMovieFavoriteCount(Long movieId);

}
