import API from "./axiosConfig";

const WatchHistoryService = {

    updateProgress: function (data) {

        return API.put(
            "/watch-history/progress",
            null,
            {
                params: {
                    username: data.username,
                    movieId: data.movieId,
                    progress: data.progress,
                    watchedTime: data.watchedTime
                }
            }
        );

    },


    saveHistory: function (data) {

        return API.post(
            "/watch-history/save",
            null,
            {
                params: {
                    username: data.username,
                    movieId: data.movieId,
                    progress: data.progress,
                    watchedTime: data.watchedTime
                }
            }
        );

    },




    getHistory: function (
        username,
        movieId
    ) {

        return API.get(
            "/watch-history/"
            + username
            + "/"
            + movieId
        );

    },


    getUserHistory: function (
        username
    ) {

        return API.get(
            "/watch-history/user/"
            + username
        );

    },


    deleteHistory: function (
        username,
        movieId
    ) {

        return API.delete(
            "/watch-history/"
            + username
            + "/"
            + movieId
        );

    }

};

export default WatchHistoryService;
