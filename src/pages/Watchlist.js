import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import watchlistService from '../services/watchlistService';
import MovieCard from '../components/movie/MovieCard';
import Swal from 'sweetalert2';

const Watchlist = () => {
  const navigate = useNavigate();
  const [watchlist, setWatchlist] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    watchlistService.getWatchlist()
      .then((res) => setWatchlist(res.data || []))
      .catch(console.error)
      .finally(() => setLoading(false));
  }, []);

  const handleRemove = async (movieId) => {
    const result = await Swal.fire({
      title: 'Remove from watchlist?',
      icon: 'warning', showCancelButton: true,
      confirmButtonColor: '#e94560', cancelButtonColor: '#0f3460',
      background: '#1a1a2e', color: '#e0e0e0',
      confirmButtonText: 'Remove',
    });
    if (result.isConfirmed) {
      await watchlistService.removeFromWatchlist(movieId);
      setWatchlist(watchlist.filter(item => (item.movie?.id || item.id) !== movieId));
    }
  };

  return (
    <div className="container page-wrapper">
      <h2 className="section-title mb-4">My Watchlist</h2>

      {loading ? (
        <div className="d-flex justify-content-center py-5">
          <div className="spinner-border text-danger" />
        </div>
      ) : watchlist.length === 0 ? (
        <div className="text-center py-5">
          <p style={{ color: '#888', fontSize: '1.1rem' }}>Your watchlist is empty.</p>
          <button className="btn btn-accent mt-3" onClick={() => navigate('/movies')}>Browse Movies</button>
        </div>
      ) : (
        <div className="row g-3">
          {watchlist.map((item, i) => {
            const movie = item.movie || item;
            return (
              <div className="col-6 col-md-3" key={movie.id || i}>
                <div style={{ position: 'relative' }}>
                  <MovieCard movie={movie} onClick={() => navigate(`/movies/${movie.id}`)} />
                  <button
                    className="btn btn-sm"
                    style={{ position: 'absolute', top: 8, right: 8, background: '#e94560', color: '#fff', borderRadius: '50%', width: 28, height: 28, padding: 0, lineHeight: '28px', fontSize: 14 }}
                    onClick={(e) => { e.stopPropagation(); handleRemove(movie.id); }}
                    title="Remove from watchlist"
                  >✕</button>
                </div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
};

export default Watchlist;
