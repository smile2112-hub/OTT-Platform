import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import MovieService from "../services/MovieService";
import FavoriteService from "../services/FavoriteService";
import WatchlistService from "../services/WatchlistService";
import ReviewService from "../services/ReviewService";

const MovieDetails = () => {

    const { id } = useParams();

    const { isAuthenticated, user } = useAuth();
    const [movie, setMovie] = useState(null);

    const [recommendedMovies, setRecommendedMovies] = useState([]);
    const [isFavorite, setIsFavorite] = useState(false);
    const [reviews, setReviews] = useState([]);
    const [rating, setRating] = useState(5);
    const [comment, setComment] = useState("");
    const [reviewError, setReviewError] = useState("");
    const [reviewSuccess, setReviewSuccess] = useState("");
    const [favoriteMessage, setFavoriteMessage] = useState("");
    const [favoriteError, setFavoriteError] = useState("");
    const [favoriteLoading, setFavoriteLoading] = useState(false);
    const [inWatchlist, setInWatchlist] = useState(false);
    const [watchlistLoading, setWatchlistLoading] = useState(false);

    useEffect(() => {

        loadMovie();
        loadMovieReviews();

        if (isAuthenticated) {
            checkFavoriteStatus();
            checkWatchlistStatus();
        }

    }, [id, isAuthenticated]);

    const checkWatchlistStatus = async () => {
        if (!isAuthenticated) {
            setInWatchlist(false);
            return;
        }

        try {
            const resp = await WatchlistService.exists(Number(id));
            setInWatchlist(resp.data === true || resp.data === "true");
        } catch (err) {
            console.error("Check watchlist error:", err);
            setInWatchlist(false);
        }
    };

    const handleWatchlistToggle = async () => {
        if (!isAuthenticated) {
            setFavoriteError("Please login to update watchlist.");
            return;
        }

        const movieId = Number(id);

        try {
            setWatchlistLoading(true);

            if (inWatchlist) {
                await WatchlistService.removeFromWatchlist(movieId);
                setInWatchlist(false);
            } else {
                await WatchlistService.addToWatchlist(movieId);
                setInWatchlist(true);
            }

        } catch (error) {
            console.error("Watchlist toggle error:", error);
            // Re-check actual state from server on failure
            await checkWatchlistStatus();
        } finally {
            setWatchlistLoading(false);
        }
    };

    const loadMovie = async () => {

        try {

            const response =
                await MovieService.getMovieById(id);

            setMovie(response.data);

            await MovieService.incrementView(id);

            const recommend =
                await MovieService.recommendMovies(id);

            setRecommendedMovies(recommend.data);

        }

        catch (error) {

            console.log(error);

        }

    };

    const loadMovieReviews = async () => {
        try {
            const response = await ReviewService.getMovieReviews(id);
            setReviews(response.data || []);
        } catch (error) {
            console.log("Load reviews error:", error);
        }
    };

    const checkFavoriteStatus = async () => {
        if (!isAuthenticated) {
            setIsFavorite(false);
            return;
        }

        try {
            const response = await FavoriteService.isFavorite(id);
            setIsFavorite(
                response.data === true || response.data === "true"
            );
        } catch (error) {
            console.log("Check favorite error:", error);
            setIsFavorite(false);
        }
    };

    const handleFavoriteToggle = async () => {
        if (!isAuthenticated) {
            setFavoriteError("Please login to add favorites.");
            return;
        }

        try {
            setFavoriteError("");
            setFavoriteMessage("");
            setFavoriteLoading(true);

            const response = isFavorite
                ? await FavoriteService.removeFavorite(id)
                : await FavoriteService.addFavorite(id);

            const message =
                typeof response.data === "string"
                    ? response.data
                    : "Favorite updated successfully.";

            const normalizedMessage = message.toLowerCase();
            const wasAdded = normalizedMessage.includes("added");
            const wasRemoved = normalizedMessage.includes("removed");
            const alreadyAdded = normalizedMessage.includes("already added");
            const notInFavorites = normalizedMessage.includes("not in favorites");
            const notLoggedIn = normalizedMessage.includes("not logged in");

            if (notLoggedIn) {
                setFavoriteError(message);
                await checkFavoriteStatus();
                return;
            }

            if (isFavorite) {
                if (wasRemoved || notInFavorites) {
                    setIsFavorite(false);
                    setFavoriteMessage(message);
                } else {
                    setFavoriteError(message);
                }
            } else {
                if (wasAdded || alreadyAdded) {
                    setIsFavorite(true);
                    setFavoriteMessage(message);
                } else {
                    setFavoriteError(message);
                }
            }

            await checkFavoriteStatus();
        } catch (error) {
            console.error("Favorite toggle error:", error);
            setFavoriteError(
                error?.response?.data ||
                error?.message ||
                "Unable to update favorites"
            );
        } finally {
            setFavoriteLoading(false);
        }
    };

    const handleSubmitReview = async (e) => {
        e.preventDefault();
        setReviewError("");
        setReviewSuccess("");

        if (!isAuthenticated) {
            setReviewError("Please login to add a review.");
            return;
        }

        if (!comment.trim()) {
            setReviewError("Please add a review comment.");
            return;
        }

        if (rating < 1 || rating > 5) {
            setReviewError("Rating must be between 1 and 5.");
            return;
        }

        try {
            await ReviewService.addReview({
                movieId: id,
                rating,
                comment
            });

            setReviewSuccess("Review submitted successfully.");
            setComment("");
            setRating(5);
            loadMovieReviews();
        } catch (error) {
            console.error("Submit review error:", error);
            setReviewError("Unable to submit review.");
        }
    };

    if (!movie) {

        return (

            <div className="container mt-5 text-center">

                <h3>

                    Loading Movie...

                </h3>

            </div>

        );

    }

    return (

        <div className="container-fluid">

            <div
                className="position-relative mb-5"
            >

                <img

                    src={`http://127.0.0.1:8081/api/movie/banner/${movie.banner}`}

                    alt="Banner"

                    className="img-fluid w-100"

                    style={{

                        height: "450px",

                        objectFit: "cover"

                    }}

                />

                <div

                    className="position-absolute top-50 start-50 translate-middle text-center text-white"

                >

                    <h1 className="display-4 fw-bold">

                        {movie.name}

                    </h1>

                </div>

            </div>

            <div className="container">

                <div className="row">

                    <div className="col-lg-4">

                        <img

                            src={`http://127.0.0.1:8081/api/movie/poster/${movie.poster}`}

                            alt={movie.name}

                            className="img-fluid rounded shadow"

                        />

                    </div>

                    <div className="col-lg-8">

                        <h2>

                            {movie.name}

                        </h2>

                        <hr />

                        <h5>

                            Genre :

                            <span className="ms-2">

                                {movie.genre}

                            </span>

                        </h5>

                        <h5>

                            Director :

                            <span className="ms-2">

                                {movie.director}

                            </span>

                        </h5>

                        <h5>

                            Actors :

                            <span className="ms-2">

                                {movie.actors}

                            </span>

                        </h5>

                        <h5>

                            Distributor :

                            <span className="ms-2">

                                {movie.distributor}

                            </span>

                        </h5>

                        <h5>

                            Release Year :

                            <span className="ms-2">

                                {movie.year}

                            </span>

                        </h5>

                        <h5>

                            Duration :

// � FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.


                            <span className="ms-2">

                                {movie.duration} Minutes

                            </span>

                        </h5>

                        <h5>

                            Rating :

                            <span className="text-warning ms-2">

                                ⭐ {movie.rating}

                            </span>

                        </h5>

                        <h5>

                            Views :

                            <span className="ms-2">

                                👁 {movie.totalViews}

                            </span>

                        </h5>

                        <div className="mt-4">

                            <h4>

                                Description

                            </h4>

                            <p>

                                {movie.description}

                            </p>

                        </div>

                        <div className="mt-4 d-flex flex-wrap align-items-center gap-2">

                            <Link

                                to={`/watch/${movie.id}`}

                                className="btn btn-danger btn-lg"

                            >

                                ▶ Watch Movie

                            </Link>

                            <button

                                className={`btn btn-${isFavorite ? "outline-warning" : "warning"} btn-lg`}

                                disabled={favoriteLoading}

                                onClick={handleFavoriteToggle}

                            >

                                {favoriteLoading

                                    ? "Saving..."

                                    : isFavorite

                                        ? "Added to favorites"

                                        : "Add to favorites"

                                }

                            </button>

                            <button
                                className={`btn btn-lg ${inWatchlist ? "btn-warning" : "btn-outline-warning"}`}
                                disabled={watchlistLoading}
                                onClick={async (e) => { e.preventDefault(); await handleWatchlistToggle(); }}
                                title={inWatchlist ? "Added to Watchlist" : "Add to Watchlist"}
                                aria-label={inWatchlist ? "Added to Watchlist" : "Add to Watchlist"}
                            >
                                {watchlistLoading
                                    ? "Saving..."
                                    : inWatchlist
                                        ? "✓ Added to Watchlist"
                                        : "+ Add to Watchlist"
                                }
                            </button>

                        </div>

                        {favoriteMessage && (
                            <div className="alert alert-success">
                                {favoriteMessage}
                            </div>
                        )}
                        {favoriteError && (
                            <div className="alert alert-danger">
                                {favoriteError}
                            </div>
                        )}
                        <div className="mt-5">

                            <h4>Leave a Review</h4>

                            {reviewError && (

                                <div className="alert alert-danger">

                                    {reviewError}

                                </div>

                            )}

                            {reviewSuccess && (

                                <div className="alert alert-success">

                                    {reviewSuccess}

                                </div>

                            )}

                            <form onSubmit={handleSubmitReview}>

                                <div className="mb-3">

                                    <label className="form-label">

                                        Rating

                                    </label>

                                    <select

                                        className="form-select"

                                        value={rating}

                                        onChange={(e) => setRating(Number(e.target.value))}

                                    >

                                        {[1, 2, 3, 4, 5].map((value) => (

                                            <option key={value} value={value}>

                                                {value} ⭐

                                            </option>

                                        ))}

                                    </select>

                                </div>

                                <div className="mb-3">

                                    <label className="form-label">

                                        Comment

                                    </label>

                                    <textarea

                                        className="form-control"

                                        rows="3"

                                        value={comment}

                                        onChange={(e) => setComment(e.target.value)}

                                        placeholder="Write your review"

                                    />

                                </div>

                                <button

                                    className="btn btn-primary"

                                    type="submit"

                                >

                                    Submit Review

                                </button>

                            </form>

                        </div>

                        <div className="mt-5">

                            <h4>Reviews</h4>

                            {reviews.length === 0 ? (

                                <p>No reviews yet.</p>

                            ) : (

                                reviews.map((review) => (

                                    <div key={review.id} className="card mb-3">

                                        <div className="card-body">

                                            <div className="d-flex justify-content-between mb-2">

                                                <strong>{review.userName || review.username || "User"}</strong>

                                                <span>⭐ {review.rating}</span>

                                            </div>

                                            <p>{review.comment || review.review || ""}</p>

                                            <small className="text-muted">

                                                {new Date(review.reviewDate || review.createdAt || review.created_date).toLocaleString()}

                                            </small>

                                        </div>

                                    </div>

                                ))

                            )}

                        </div>

                    </div>

                </div>

                {

                    movie.trailer &&

                    <div className="mt-5">

                        <h3>

                            Trailer

                        </h3>

                        <video

                            controls

                            width="100%"

                            height="500"

                        >

                            <source

                                src={`http://127.0.0.1:8081/api/movie/trailer/${movie.trailer}`}

                                type="video/mp4"

                            />

                        </video>

                    </div>

                }

                <div className="mt-5">

                    <h3 className="mb-4">

                        Recommended Movies

                    </h3>

                    <div className="row">

                        {

                            recommendedMovies.map(movie => (

                                <div

                                    className="col-lg-3 col-md-4 col-sm-6 mb-4"

                                    key={movie.id}

                                >

                                    <div className="card h-100 shadow">

                                        <img

                                            src={`http://127.0.0.1:8081/api/movie/poster/${movie.poster}`}

                                            alt={movie.name}

                                            className="card-img-top"

                                            style={{

                                                height: "350px",

                                                objectFit: "cover"

                                            }}

                                        />

                                        <div className="card-body">

                                            <h5>

                                                {movie.name}

                                            </h5>

                                            <p>

                                                ⭐ {movie.rating}

                                            </p>

                                        </div>

                                        <div className="card-footer bg-white">

                                            <Link

                                                to={`/movie/${movie.id}`}

                                                className="btn btn-primary w-100"

                                            >

                                                View Details

                                            </Link>

                                        </div>

                                    </div>

                                </div>

                            ))

                        }

                    </div>

                </div>

            </div>

        </div>

    );

};

export default MovieDetails;
