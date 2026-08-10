import API from "./axiosConfig";

const ReviewService = {

    // ==========================
    // GET ALL REVIEWS
    // ==========================
    getAllReviews() {
        return API.get("/review/getAllReviews");
    },

    // ==========================
    // GET REVIEW BY ID
    // ==========================
    getReview(id) {
        return API.get(`/review/${id}`);
    },

    // ==========================
    // CREATE REVIEW
    // ==========================
    addReview(data) {
        const movieId = data.movieId || data.id;
        const rating = data.rating;
        const review = data.review || data.comment;

        return API.post(
            `/review/add/${movieId}`,
            null,
            {
                params: {
                    rating,

// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

                    review
                }
            }
        );
    },

    // ==========================
    // UPDATE REVIEW
    // ==========================
    updateReview(id, data) {
        return API.put(`/review/updateReview/${id}`, data);
    },

    // ==========================
    // DELETE REVIEW
    // ==========================
    deleteReview(id) {
        return API.delete(`/review/deleteReview/${id}`);
    },

    // ==========================
    // USER REVIEWS
    // ==========================
    getMyReviews() {
        return API.get("/review/myReviews");
    },

    // ==========================
    // MOVIE REVIEWS
    // ==========================
    getMovieReviews(movieId) {
        return API.get(`/review/movie/${movieId}`);
    }

};

export default ReviewService;
