import api from './api';

// GET  /api/movie/getAllMovies
const getAllMovies = () => api.get('/movie/getAllMovies');

// There is no getById endpoint yet — fetch all and filter locally
const getMovieById = (id) =>
  api.get('/movie/getAllMovies').then(res => ({
    ...res,
    data: (res.data || []).find(m => String(m.id) === String(id)) || null,
  }));

// POST /api/movie/createMovie  (multipart)
const createMovie = (formData) =>
  api.post('/movie/createMovie', formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });

// PUT  /api/movie/updateMovie
const updateMovie = (movieData) => api.put('/movie/updateMovie', movieData);

// DELETE /api/movie/deleteMovie/:id
const deleteMovie = (id) => api.delete(`/movie/deleteMovie/${id}`);

// Search/filter — done locally since backend has no search endpoint yet
const searchMovies = (query) =>
  api.get('/movie/getAllMovies').then(res => ({
    ...res,
    data: (res.data || []).filter(m =>
      m.title?.toLowerCase().includes(query.toLowerCase())
    ),
  }));

const filterByGenre = (genre) =>
  api.get('/movie/getAllMovies').then(res => ({
    ...res,
    data: (res.data || []).filter(m => m.genre === genre),
  }));

const movieService = {
  getAllMovies, getMovieById, createMovie, updateMovie,
  deleteMovie, searchMovies, filterByGenre,
};
export default movieService;
