import API from "./axiosConfig";

const VideoService = {

    getVideoUrl(movieId) {

        return (
            "http://127.0.0.1:8081/api/video/stream/"
            + movieId
        );

    },


// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

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
