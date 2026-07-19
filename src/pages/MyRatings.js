import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ratingService from '../services/ratingService';
import Swal from 'sweetalert2';

const StarRating = ({ value, onChange }) => (
  <div className="stars">
    {[1, 2, 3, 4, 5].map((s) => (
      <span
        key={s}
        className={s <= value ? 'active' : ''}
        onClick={() => onChange(s)}
        style={{ cursor: 'pointer' }}
      >★</span>
    ))}
  </div>
);

const MyRatings = () => {
  const navigate = useNavigate();
  const [ratings, setRatings] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    ratingService.getMyRatings()
      .then((res) => setRatings(res.data || []))
      .catch(console.error)
      .finally(() => setLoading(false));
  }, []);

  const handleUpdate = async (ratingId, movieId, newValue) => {
    try {
      await ratingService.updateRating(ratingId, newValue);
      setRatings(ratings.map((r) =>
        r.id === ratingId ? { ...r, rating: newValue } : r
      ));
      Swal.fire({
        icon: 'success', title: `Updated to ${newValue}/5 ⭐`,
        timer: 900, showConfirmButton: false,
        background: '#1a1a2e', color: '#e0e0e0',
      });
    } catch (e) { console.error(e); }
  };

  const handleDelete = async (ratingId) => {
    const result = await Swal.fire({
      title: 'Remove this rating?',
      icon: 'warning', showCancelButton: true,
      confirmButtonColor: '#e94560', cancelButtonColor: '#0f3460',
      background: '#1a1a2e', color: '#e0e0e0',
    });
    if (result.isConfirmed) {
      await ratingService.deleteRating(ratingId);
      setRatings(ratings.filter((r) => r.id !== ratingId));
    }
  };

  return (
    <div className="container page-wrapper">
      <h2 className="section-title mb-4">My Ratings</h2>

      {loading ? (
        <div className="d-flex justify-content-center py-5">
          <div className="spinner-border text-danger" />
        </div>
      ) : ratings.length === 0 ? (
        <div className="text-center py-5">
          <p style={{ color: '#888', fontSize: '1.1rem' }}>You haven't rated any movies yet.</p>
          <button className="btn btn-accent mt-3" onClick={() => navigate('/movies')}>
            Browse Movies
          </button>
        </div>
      ) : (
        <div className="row g-3">
          {ratings.map((item) => {
            const movie = item.movie || {};
            return (
              <div className="col-12 col-md-6" key={item.id}>
                <div
                  className="d-flex gap-3 p-3 rounded-3"
                  style={{ background: '#16213e', border: '1px solid #0f3460' }}
                >
                  {/* Poster thumbnail */}
                  <img
                    src={movie.imageUrl || movie.posterUrl || 'https://via.placeholder.com/70x100/0f3460/888?text=🎬'}
                    alt={movie.title}
                    style={{ width: 70, height: 100, objectFit: 'cover', borderRadius: 8, flexShrink: 0 }}
                  />
                  <div className="flex-grow-1">
                    <h6
                      style={{ color: '#fff', cursor: 'pointer', marginBottom: 4 }}
                      onClick={() => navigate(`/movies/${movie.id}`)}
                    >
                      {movie.title || 'Unknown Movie'}
                    </h6>
                    <small style={{ color: '#888' }}>
                      {movie.genre?.name || movie.genre || ''}
                      {movie.releaseDate ? ` · ${movie.releaseDate.slice(0, 4)}` : ''}
                    </small>
                    <div className="mt-2">
                      <StarRating
                        value={item.rating}
                        onChange={(val) => handleUpdate(item.id, movie.id, val)}
                      />
                    </div>
                    <small style={{ color: '#555', fontSize: '0.75rem' }}>
                      {item.createdAt ? `Rated on ${new Date(item.createdAt).toLocaleDateString()}` : ''}
                    </small>
                  </div>
                  <button
                    className="btn btn-sm align-self-start"
                    style={{ background: 'transparent', color: '#e74c3c', border: 'none', fontSize: '1.1rem' }}
                    onClick={() => handleDelete(item.id)}
                    title="Delete rating"
                  >🗑</button>
                </div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
};

export default MyRatings;
