import React, { useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import movieService from '../services/movieService';
import MovieCard from '../components/movie/MovieCard';

const Home = () => {
  const { user } = useContext(AuthContext);
  const navigate = useNavigate();
  const [movies, setMovies] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    movieService.getAllMovies()
      .then((res) => setMovies(res.data || []))
      .catch(() => setError('Could not load movies. Is the backend running?'))
      .finally(() => setLoading(false));
  }, []);

  const trending = movies.slice(0, 4);
  const newReleases = movies.slice(4, 8);

  return (
    <div>
      {/* Hero Banner */}
      <div className="hero-banner">
        <div className="container">
          <h1>Welcome back, <span>{user?.username || 'User'}</span></h1>
          <p>Discover movies, build your watchlist, and share your ratings.</p>
          <button className="btn btn-accent btn-lg me-3" onClick={() => navigate('/movies')}>
            Browse Movies
          </button>
          <button className="btn btn-outline-accent btn-lg" onClick={() => navigate('/watchlist')}>
            My Watchlist
          </button>
        </div>
      </div>

      <div className="container page-wrapper">
        {error && (
          <div className="alert mb-4" style={{ background: '#2b0d0d', color: '#f5b7b1', border: '1px solid #e74c3c', fontSize: '1rem' }}>
            {error}
          </div>
        )}

        {/* Trending */}
        <div className="mb-5">
          <h3 className="section-title">Trending Now</h3>
          {loading ? (
            <div className="row g-3">
              {[1,2,3,4].map(i => (
                <div key={i} className="col-6 col-md-3">
                  <div className="movie-card placeholder-glow">
                    <div className="placeholder" style={{ height: 300, width: '100%', background: '#16213e' }} />
                    <div className="card-body">
                      <span className="placeholder col-8" style={{ background: '#0f3460', borderRadius: 4, display: 'block', height: 16 }} />
                    </div>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <div className="row g-3">
              {trending.length > 0 ? trending.map(movie => (
                <div className="col-6 col-md-3" key={movie.id}>
                  <MovieCard movie={movie} onClick={() => navigate(`/movies/${movie.id}`)} />
                </div>
              )) : (
                <p style={{ color: '#888', fontSize: '1rem' }}>No movies yet — connect the backend to see content.</p>
              )}
            </div>
          )}
        </div>

        {/* New Releases */}
        <div className="mb-5">
          <h3 className="section-title">New Releases</h3>
          {!loading && (
            <div className="row g-3">
              {newReleases.length > 0 ? newReleases.map(movie => (
                <div className="col-6 col-md-3" key={movie.id}>
                  <MovieCard movie={movie} onClick={() => navigate(`/movies/${movie.id}`)} />
                </div>
              )) : (
                <p style={{ color: '#888', fontSize: '1rem' }}>No new releases found.</p>
              )}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Home;
