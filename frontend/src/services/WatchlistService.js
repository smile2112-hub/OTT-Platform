/**
 * Author:  Aakanksha Ashok Hedau
 * Email:   aakanksha0499@gmail.com
 * Project: FlowFlix OTT Platform
 */
import API from "./axiosConfig";

const WatchlistService = {

    addToWatchlist: function (movieId) {

        return API.post(`/watchlist/add/${movieId}`);

    },

    removeFromWatchlist: function (movieId) {

        return API.delete(`/watchlist/remove/${movieId}`);

// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.


    },

    getWatchlist: function () {

        return API.get(`/watchlist/my`);

    },

    exists: function (movieId) {

        return API.get(`/watchlist/exists/${movieId}`);

    }

};

export default WatchlistService;
