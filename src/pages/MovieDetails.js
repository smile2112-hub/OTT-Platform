import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import movieService from '../services/movieService';
import watchlistService from '../services/watchlistService';
import ratingService from '../services/ratingService';
import reviewService from '../services/reviewService';
import Swal from 'sweetalert2';

const StarRating = ({ value, onChange }) => (
  <div className="stars mb-3">
    {[1,2,3,4,5].map(s => (
      <span key={s} className={s <= value ? 'active' : ''} onClick={() => onChange(s)}>★</span>
    ))}
  </div>
);

const MovieDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [movie, setMovie] = useState(null);
  const [inWatchlist, setInWatchlist] = useState(false);
  const [rating, setRating] = useState(0);
  const [reviews, setReviews] = useState([]);
  const [comment, setComment] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([
      movieService.getMovieById(id),
      watchlistService.isInWatchlist(id).catch(() => ({ data: false })),
      reviewService.getReviews(id).catch(() => ({ data: [] })),
      ratingService.getMovieRating(id).catch(() => ({ data: { averageRating: 0 } })),
    ]).then(([mRes, wRes, rRes, ratRes]) => {
      setMovie(mRes.data);
      setInWatchlist(wRes.data === true || wRes.data?.inWatchlist === true);
      setReviews(rRes.data || []);
      setRating(ratRes.data?.userRating || ratRes.data?.averageRating || 0);
    }).catch(console.error)
      .finally(() => setLoading(false));
  }, [id]);

  const toggleWatchlist = async () => {
    try {
      if (inWatchlist) {
        await watchlistService.removeFromWatchlist(id);
        setInWatchlist(false);
        Swal.fire({ icon: 'info', title: 'Removed from watchlist', timer: 1000, showConfirmButton: false, background: '#1a1a2e', color: '#e0e0e0' });
      } else {
        await watchlistService.addToWatchlist(id);
        setInWatchlist(true);
        Swal.fire({ icon: 'success', title: 'Added to watchlist!', timer: 1000, showConfirmButton: false, background: '#1a1a2e', color: '#e0e0e0' });
      }
    } catch (e) { console.error(e); }
  };

  const submitRating = async (val) => {
    setRating(val);
    try {
      await ratingService.rateMovie(id, val);
      Swal.fire({ icon: 'success', title: `Rated ${val}/5 ⭐`, timer: 900, showConfirmButton: false, background: '#1a1a2e', color: '#e0e0e0' });
    } catch (e) { console.error(e); }
  };

  const submitReview = async (e) => {
    e.preventDefault();
    if (!comment.trim()) return;
    try {
      const res = await reviewService.addReview(id, comment);
      setReviews([res.data, ...reviews]);
      setComment('');
    } catch (e) { console.error(e); }
  };

  if (loading) return (
    <div className="d-flex justify-content-center align-items-center" style={{ minHeight: '60vh' }}>
      <div className="spinner-border text-danger" />
    </div>
  );

  if (!movie) return (
    <div className="container py-5 text-center">
      <h3 style={{ color: '#888' }}>Movie not found.</h3>
      <button className="btn btn-accent mt-3" onClick={() => navigate('/movies')}>← Back to Movies</button>
    </div>
  );

  return (
    <div className="container page-wrapper">
      <button className="btn btn-outline-accent btn-sm mb-4" onClick={() => navigate('/movies')}>← Back</button>

      <div className="row g-4">
        {/* Poster */}
        <div className="col-md-3">
          <img
            src={movie.imageUrl || movie.posterUrl || 'https://via.placeholder.com/300x420/16213e/888?text=No+Image'}
            alt={movie.title}
            className="w-100 rounded-3"
            style={{ border: '1px solid #0f3460' }}
          />
        </div>

        {/* Info */}
        <div className="col-md-9">
          <h1 style={{ color: '#fff', fontWeight: 700 }}>{movie.title}</h1>
          <div className="mb-2">
            {movie.genre && <span className="genre-badge">{movie.genre?.name || movie.genre}</span>}
            {movie.releaseDate && <span className="genre-badge">{movie.releaseDate?.slice(0, 4)}</span>}
            {movie.language && <span className="genre-badge">{movie.language}</span>}
          </div>
          <p style={{ color: '#aaa', lineHeight: 1.7, marginTop: 12 }}>{movie.description || movie.overview}</p>

          {movie.cast && <p style={{ color: '#888', fontSize: '0.85rem', marginTop: 8 }}><b style={{ color: '#ccc' }}>Cast:</b> {movie.cast}</p>}
          {movie.director && <p style={{ color: '#888', fontSize: '0.85rem' }}><b style={{ color: '#ccc' }}>Director:</b> {movie.director}</p>}

          {/* Actions */}
          <div className="d-flex gap-3 flex-wrap mt-4">
            <button className={`btn ${inWatchlist ? 'btn-outline-accent' : 'btn-accent'}`} onClick={toggleWatchlist}>
              {inWatchlist ? 'In Watchlist' : 'Add to Watchlist'}
            </button>
          </div>

          {/* Rating */}
          <div className="mt-4">
            <h5 style={{ color: '#fff' }}>Your Rating</h5>
            <StarRating value={rating} onChange={submitRating} />
          </div>
        </div>
      </div>

      {/* Reviews */}
      <div className="mt-5">
        <h4 className="section-title">Reviews</h4>
        <form onSubmit={submitReview} className="d-flex gap-2 mt-3 mb-4">
          <input
            type="text" className="form-control form-control-dark"
            placeholder="Write a review..." value={comment}
            onChange={(e) => setComment(e.target.value)}
          />
          <button type="submit" className="btn btn-accent px-4">Post</button>
        </form>

        {reviews.length === 0 ? (
          <p style={{ color: '#888' }}>No reviews yet. Be the first!</p>
        ) : reviews.map((r, i) => (
          <div key={r.id || i} className="mb-3 p-3 rounded-3" style={{ background: '#16213e', border: '1px solid #0f3460' }}>
            <div className="d-flex justify-content-between">
              <strong style={{ color: '#7ec8e3' }}>{r.username || r.user?.username || 'User'}</strong>
              <small style={{ color: '#888' }}>{r.createdAt ? new Date(r.createdAt).toLocaleDateString() : ''}</small>
            </div>
            <p className="mt-1 mb-0" style={{ color: '#ccc', fontSize: '0.9rem' }}>{r.comment}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default MovieDetails;
