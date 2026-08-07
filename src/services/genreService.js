import api from './api';

// GET  /genres           → all genres
const getAllGenres = () => api.get('/genres');

// GET  /genres/:id
const getGenreById = (id) => api.get(`/genres/${id}`);

const genreService = { getAllGenres, getGenreById };
export default genreService;
