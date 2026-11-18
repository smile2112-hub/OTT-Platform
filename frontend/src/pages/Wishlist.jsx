import React, { useEffect, useState } from "react";
import { Badge, Card, Col, Container, Row, Spinner } from "react-bootstrap";
import { FaPlay, FaStar } from "react-icons/fa";
import { Link } from "react-router-dom";
import WatchlistService from "../services/WatchlistService";
import MovieService from "../services/MovieService";

const Wishlist = () => {

    const [items, setItems] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        loadWatchlist();
    }, []);

    const loadWatchlist = async () => {
        try {
            setLoading(true);
            setError("");
            console.log("Fetching watchlist from API...");
            const resp = await WatchlistService.getWatchlist();
            console.log("Watchlist API response:", resp.data);
            // Backend returns List<WatchlistDTO> — each item has: id, movieId, movieName, poster, genre, rating, year, etc.
            setItems(resp.data || []);
        } catch (err) {
            console.error("Load watchlist error:", err);
            console.error("Error details:", err.response?.data, err.response?.status);
            setError(err.response?.data?.message || err.message || "Could not load watchlist. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    const remove = async (movieId) => {
        try {
            await WatchlistService.removeFromWatchlist(movieId);
            // Filter by movieId field which is what the DTO returns
            setItems(prev => prev.filter(i => i.movieId !== movieId));
        } catch (err) {
            console.error("Remove from watchlist error:", err);
            alert("Could not remove movie from watchlist.");
        }
    };

    return (
        <Container className="mt-4 mb-5">

            <h2 className="mb-4 fw-bold">My Watchlist</h2>

            {error && (
                <div className="alert alert-danger">{error}</div>
            )}

            <Row>

                {loading ? (
                    <Col className="text-center py-5">
                        <Spinner animation="border" variant="danger" />
                        <p className="mt-3">Loading your watchlist...</p>


                    </Col>
                ) : items.length === 0 ? (
                    <Col>
                        <Card className="shadow border-0">
                            <Card.Body className="text-center py-5">
                                <h5>Your watchlist is empty.</h5>
                                <p className="text-muted">Add movies from the movie details page.</p>
                                <Link to="/" className="btn btn-danger mt-2">Browse Movies</Link>
                            </Card.Body>
                        </Card>
                    </Col>
                ) : (
                    items.map((item) => {
                        // WatchlistDTO fields: id (watchlist entry id), movieId, movieName, poster, genre, rating, year, duration
                        const movieId = item.movieId;

                        return (
                            <Col md={4} lg={3} key={item.id} className="mb-4">
                                <Card className="shadow h-100 border-0">
                                    <Link to={`/movie/${movieId}`}>
                                        <Card.Img
                                            variant="top"
                                            src={MovieService.getPoster(item.poster)}
                                            style={{ height: 320, objectFit: "cover" }}
                                            alt={item.movieName}
                                        />
                                    </Link>
                                    <Card.Body>
                                        <Card.Title>{item.movieName}</Card.Title>
                                        <div className="mb-2">
                                            {item.genre && (
                                                <Badge bg="secondary" className="me-2">{item.genre}</Badge>
                                            )}
                                            {item.rating && (
                                                <Badge bg="warning" text="dark">
                                                    <FaStar className="me-1" />{item.rating}
                                                </Badge>
                                            )}
                                        </div>
                                        {item.year && (
                                            <p className="text-muted mb-2" style={{ fontSize: 13 }}>Year: {item.year}</p>
                                        )}
                                        <div className="d-flex gap-2 mt-3">
                                            <Link to={`/watch/${movieId}`} className="btn btn-danger btn-sm">
                                                <FaPlay className="me-1" /> Watch
                                            </Link>
                                            <button
                                                className="btn btn-outline-secondary btn-sm"
                                                onClick={() => remove(movieId)}
                                            >
                                                ✕ Remove
                                            </button>
                                        </div>
                                    </Card.Body>
                                </Card>
                            </Col>
                        );
                    })
                )}

            </Row>

        </Container>
    );
};

export default Wishlist;
