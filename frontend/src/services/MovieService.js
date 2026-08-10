import api from "./api";

const MovieService = {

    // ===========================
    // GET
    // ===========================

    getAllMovies() {
        return api.get("/movie/getAllMovies");
    },

    getMovie(id) {
        return api.get(`/movie/${id}`);
    },

    getMovieById(id) {
        return api.get(`/movie/${id}`);
    },

    getFeaturedMovies() {
        return api.get("/movie/featured");
    },

    getLatestMovies() {
        return api.get("/movie/latest");
    },

    getTrendingMovies() {
        return api.get("/movie/trending");
    },

    getTopViewedMovies() {
        return api.get("/movie/topViewed");
    },

    searchMovies(keyword) {
        return api.get(`/movie/search/${keyword}`);
    },

    getMoviesByGenre(genre) {
        return api.get(`/movie/genre/${genre}`);
    },

    recommendMovies(id) {
        return api.get(`/movie/recommend/${id}`);
    },

    getRelatedMovies(id) {
        return api.get(`/movie/recommend/${id}`);
    },

    getPoster(filename) {
        return `http://127.0.0.1:8081/api/movie/poster/${filename}`;
    },

    getBanner(filename) {
        return `http://127.0.0.1:8081/api/movie/banner/${filename}`;
    },

    getVideo(filename) {
        return `http://127.0.0.1:8081/api/movie/video/${filename}`;
    },

    getTrailer(filename) {
        return `http://127.0.0.1:8081/api/movie/trailer/${filename}`;
    },

    // ===========================
    // VIEW
    // ===========================

    incrementView(id) {
        return api.put(`/movie/view/${id}`);
    },

    // ===========================
    // CREATE
    // ===========================

    createMovie(formData) {

        return api.post(

            "/movie/createMovie",

            formData,

            {


// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

                headers: {

                    "Content-Type": "multipart/form-data"

                }

            }

        );

    },

    // ===========================
    // UPDATE
    // ===========================

    updateMovie(id, formData) {

        return api.put(

            `/movie/updateMovie/${id}`,

            formData,

            {

                headers: {

                    "Content-Type": "multipart/form-data"

                }

            }

        );

    },

    // ===========================
    // DELETE
    // ===========================

    deleteMovie(id) {

        return api.delete(`/movie/deleteMovie/${id}`);

    },

    // ===========================
    // STATUS
    // ===========================

    activateMovie(id) {

        return api.put(`/movie/activate/${id}`);

    },

    deactivateMovie(id) {

        return api.put(`/movie/deactivate/${id}`);

    },

    featureMovie(id) {

        return api.put(`/movie/feature/${id}`);

    },

    unFeatureMovie(id) {

        return api.put(`/movie/unfeature/${id}`);

    },

    // ===========================
    // TRENDING
    // ===========================

    makeTrending(id) {

        return api.put(`/movie/trending/${id}`);

    },

    removeTrending(id) {

        return api.put(`/movie/removeTrending/${id}`);

    }

};

export default MovieService;
