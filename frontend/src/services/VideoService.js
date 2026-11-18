import API from "./axiosConfig";

const VideoService = {

    getVideoUrl(movieId) {

        return (
            "http://127.0.0.1:8081/api/video/stream/"
            + movieId
        );

    },



    getVideo(movieId) {

        return API.get(

            "/video/stream/" + movieId,

            {
                responseType: "blob"
            }

        );

    }

};

export default VideoService;
