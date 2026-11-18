import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import MovieService from "../services/MovieService";
import "./Home.css";

const Home = () => {

    const [featuredMovies, setFeaturedMovies] = useState([]);
    const [latestMovies, setLatestMovies] = useState([]);
    const [trendingMovies, setTrendingMovies] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        loadHomeData();
    }, []);

    const loadHomeData = async () => {
        try {
            const [featured, latest, trending] = await Promise.all([
                MovieService.getFeaturedMovies(),
                MovieService.getLatestMovies(),
                MovieService.getTrendingMovies(),
            ]);
            setFeaturedMovies(featured.data || []);
            setLatestMovies(latest.data || []);
            setTrendingMovies(trending.data || []);
        } catch (error) {
            console.log(error);
        } finally {
            setLoading(false);
        }
    };

    const renderMovieSection = (title, movies) => {
        if (!movies || movies.length === 0) return null;
        return (
            <div className="movie-section">
                <div className="section-header">
                    <h2 className="section-title">{title}</h2>
                </div>
                <div className="home-movie-grid">
                    {movies.map(movie => (
                        <div className="home-movie-card" key={movie.id}>
                            <Link to={`/movie/${movie.id}`} className="home-card-poster-link">
                                <div className="home-poster-wrap">
                                    <img
                                        src={MovieService.getPoster(movie.poster)}
                                        className="home-poster-img"
                                        alt={movie.name}
                                    />
                                    <div className="home-poster-overlay">
                                        <span className="home-overlay-text">View Details</span>
                                    </div>
                                </div>


                            </Link>
                            <div className="home-card-body">
                                <h5 className="home-card-title">{movie.name}</h5>
                                <div className="home-card-meta">
                                    {movie.genre && (
                                        <span className="home-badge-genre">{movie.genre}</span>
                                    )}
                                    {movie.rating && (
                                        <span className="home-badge-rating">⭐ {movie.rating}</span>
                                    )}
                                </div>
                                <div className="home-card-actions">
                                    <Link to={`/watch/${movie.id}`} className="home-btn-watch">
                                        ▶ Watch Now
                                    </Link>
                                    <Link to={`/movie/${movie.id}`} className="home-btn-details">
                                        Details
                                    </Link>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        );
    };

    if (loading) {
        return (
            <div className="home-loading">
                Loading...
            </div>
        );
    }

    return (
        <div className="home-page">

            {/* ── HERO ── */}
            <div className="home-hero">
                <div className="home-hero-content">
                    <h1 className="home-hero-title">Welcome To FlowFlix</h1>
                    <p className="home-hero-sub">Unlimited Movies, TV Shows &amp; Entertainment</p>
                    <Link to="/movies" className="home-hero-btn">Browse Movies</Link>
                </div>
            </div>

            {/* ── SECTIONS ── */}
            <div className="home-sections">
                {renderMovieSection("⭐ Featured Movies", featuredMovies)}
                {renderMovieSection("🔥 Trending Movies", trendingMovies)}
                {renderMovieSection("🆕 Latest Movies", latestMovies)}
            </div>

        </div>
    );
};

export default Home;
