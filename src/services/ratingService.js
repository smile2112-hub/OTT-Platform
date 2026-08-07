import api from './api';

// POST   /ratings/movie/:movieId  → { rating: number }
const rateMovie = (movieId, rating) => api.post(`/ratings/movie/${movieId}`, { rating });

// PUT    /ratings/:id             → update rating
const updateRating = (ratingId, rating) => api.put(`/ratings/${ratingId}`, { rating });

// GET    /ratings/movie/:movieId  → average rating for a movie
const getMovieRating = (movieId) => api.get(`/ratings/movie/${movieId}`);

// GET    /ratings/user            → current user's ratings
const getMyRatings = () => api.get('/ratings/user');

// DELETE /ratings/:id
const deleteRating = (ratingId) => api.delete(`/ratings/${ratingId}`);

const ratingService = { rateMovie, updateRating, getMovieRating, getMyRatings, deleteRating };
export default ratingService;
