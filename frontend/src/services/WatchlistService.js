import API from "./axiosConfig";

const WatchlistService = {

    addToWatchlist: function (movieId) {

        return API.post(`/watchlist/add/${movieId}`);

    },

    removeFromWatchlist: function (movieId) {

        return API.delete(`/watchlist/remove/${movieId}`);



    },

    getWatchlist: function () {

        return API.get(`/watchlist/my`);

    },

    exists: function (movieId) {

        return API.get(`/watchlist/exists/${movieId}`);

    }

};

export default WatchlistService;
