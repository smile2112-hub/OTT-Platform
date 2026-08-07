import api from './api';

// GET    /reviews/movie/:movieId  → all reviews for a movie
const getReviews = (movieId) => api.get(`/reviews/movie/${movieId}`);

// POST   /reviews/movie/:movieId  → { comment: string }
const addReview = (movieId, comment) => api.post(`/reviews/movie/${movieId}`, { comment });

// PUT    /reviews/:id             → edit review
const updateReview = (reviewId, comment) => api.put(`/reviews/${reviewId}`, { comment });

// DELETE /reviews/:id
const deleteReview = (reviewId) => api.delete(`/reviews/${reviewId}`);

// GET    /reviews/user            → current user's reviews
const getMyReviews = () => api.get('/reviews/user');

const reviewService = { getReviews, addReview, updateReview, deleteReview, getMyReviews };
export default reviewService;
