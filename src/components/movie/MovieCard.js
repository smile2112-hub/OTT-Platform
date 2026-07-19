import React from 'react';

// Reusable movie card used on Home, Movies, Watchlist pages
const MovieCard = ({ movie, onClick }) => {
  const poster =
    movie?.imageUrl ||
    movie?.posterUrl ||
    movie?.image ||
    `https://via.placeholder.com/300x420/16213e/555?text=${encodeURIComponent(movie?.title || 'Movie')}`;

  return (
    <div className="movie-card" onClick={onClick} role="button" tabIndex={0}
      onKeyDown={(e) => e.key === 'Enter' && onClick?.()}>
      <img src={poster} alt={movie?.title || 'Movie'} loading="lazy" />
      <div className="card-body">
        <div className="card-title">{movie?.title || 'Untitled'}</div>
        <div className="card-text">
          {movie?.genre?.name || movie?.genre || ''}
          {movie?.releaseDate ? ` · ${movie.releaseDate.slice(0, 4)}` : ''}
        </div>
        {movie?.averageRating > 0 && (
          <div style={{ color: '#f5c518', fontSize: '0.8rem', marginTop: 4 }}>
            {'★'.repeat(Math.round(movie.averageRating))}
            {'☆'.repeat(5 - Math.round(movie.averageRating))}
            <span style={{ color: '#888', marginLeft: 4 }}>{Number(movie.averageRating).toFixed(1)}</span>
          </div>
        )}
      </div>
    </div>
  );
};

export default MovieCard;
