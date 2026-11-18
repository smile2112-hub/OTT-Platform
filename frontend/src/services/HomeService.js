import API from "./axiosConfig";

const HomeService = {

    getHome() {
        return API.get("/home");
    },



    search(keyword) {
        return API.get("/home/search/" + keyword);
    },

    movieDetails(id) {
        return API.get("/home/movie/" + id);
    }

};

export default HomeService;
