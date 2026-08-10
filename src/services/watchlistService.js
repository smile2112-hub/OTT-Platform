import api from './api';

// GET    /watchlist              → get logged-in user's watchlist
const getWatchlist = () => api.get('/watchlist');

// POST   /watchlist/:movieId     → add movie to watchlist
const addToWatchlist = (movieId) => api.post(`/watchlist/${movieId}`);

// DELETE /watchlist/:movieId     → remove from watchlist
const removeFromWatchlist = (movieId) => api.delete(`/watchlist/${movieId}`);

// GET    /watchlist/check/:movieId → check if movie is in watchlist
const isInWatchlist = (movieId) => api.get(`/watchlist/check/${movieId}`);

const watchlistService = { getWatchlist, addToWatchlist, removeFromWatchlist, isInWatchlist };
export default watchlistService;
