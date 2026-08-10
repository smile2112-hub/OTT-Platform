/**
 * Author:  Aakanksha Ashok Hedau
 * Email:   aakanksha0499@gmail.com
 * Project: FlowFlix OTT Platform
 */
import API from "./axiosConfig";

const HomeService = {

    getHome() {
        return API.get("/home");
    },

// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.


    search(keyword) {
        return API.get("/home/search/" + keyword);
    },

    movieDetails(id) {
        return API.get("/home/movie/" + id);
    }

};

export default HomeService;
