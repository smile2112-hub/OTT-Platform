import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import movieService from '../services/movieService';
import genreService from '../services/genreService';
import MovieCard from '../components/movie/MovieCard';

const Movies = () => {
  const navigate = useNavigate();
  const [movies, setMovies] = useState([]);
  const [genres, setGenres] = useState([]);
  const [search, setSearch] = useState('');
  const [selectedGenre, setSelectedGenre] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([
      movieService.getAllMovies(),
      genreService.getAllGenres(),
    ]).then(([moviesRes, genresRes]) => {
      setMovies(moviesRes.data || []);
      setGenres(genresRes.data || []);
    }).catch(console.error)
      .finally(() => setLoading(false));
  }, []);

  const filtered = movies.filter((m) => {
    const matchSearch = m.title?.toLowerCase().includes(search.toLowerCase());
    const matchGenre = selectedGenre
      ? m.genre?.id === Number(selectedGenre) || m.genreId === Number(selectedGenre)
      : true;
    return matchSearch && matchGenre;
  });

  const handleSearch = (e) => {
    e.preventDefault();
    if (search.trim()) {
      movieService.searchMovies(search).then(r => setMovies(r.data || [])).catch(console.error);
    } else {
      movieService.getAllMovies().then(r => setMovies(r.data || [])).catch(console.error);
    }
  };

  return (
    <div className="container page-wrapper">
      <h2 className="section-title mb-4">All Movies</h2>

      {/* Search + Filter */}
      <div className="row g-3 mb-4">
        <div className="col-md-6">
          <form onSubmit={handleSearch} className="d-flex gap-2">
            <input
              type="text"
              className="form-control form-control-dark"
              placeholder="Search by title..."
              value={search}
              onChange={(e) => setSearch(e.target.value)}
            />
            <button type="submit" className="btn btn-accent px-4">Search</button>
          </form>
        </div>
        <div className="col-md-3">
          <select
            className="form-select form-control-dark"
            value={selectedGenre}
            onChange={(e) => setSelectedGenre(e.target.value)}
          >
            <option value="">All Genres</option>
            {genres.map(g => <option key={g.id} value={g.id}>{g.name}</option>)}
          </select>
        </div>
      </div>

      {loading ? (
        <div className="row g-3">
          {[1,2,3,4,5,6,7,8].map(i => (
            <div className="col-6 col-md-3" key={i}>
              <div className="movie-card placeholder-glow" style={{ height: 360 }}>
                <div className="placeholder w-100" style={{ height: 300, background: '#16213e' }} />
              </div>
            </div>
          ))}
        </div>
      ) : filtered.length > 0 ? (
        <div className="row g-3">
          {filtered.map(movie => (
            <div className="col-6 col-md-3" key={movie.id}>
              <MovieCard movie={movie} onClick={() => navigate(`/movies/${movie.id}`)} />
            </div>
          ))}
        </div>
      ) : (
        <div className="text-center py-5">
          <p style={{ color: '#888', fontSize: '1.1rem' }}>No movies found.</p>
          <button
            className="btn btn-outline-accent mt-3 px-4"
            onClick={() => { setSearch(''); setSelectedGenre(''); }}
          >
            Clear Filters
          </button>
        </div>
      )}
    </div>
  );
};

export default Movies;
