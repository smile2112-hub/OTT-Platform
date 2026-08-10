import React, { useEffect, useState } from "react";

import {
    Badge,
    Button,
    Card,
    Col,
    Container,
    Form,
    Row,
    Spinner
} from "react-bootstrap";

import {
    FaHeart,
    FaPlay,
    FaStar,
    FaEye,
    FaCalendarAlt,
    FaGlobe,
    FaClock
} from "react-icons/fa";

import { useParams } from "react-router-dom";

import MovieService from "../services/MovieService";

import ReviewService from "../services/ReviewService";
import WatchlistService from "../services/WatchlistService";

const WatchMovie = () => {

    const { id } = useParams();

    // =====================================
    // STATES
    // =====================================

    const [movie, setMovie] = useState(null);

    const [reviews, setReviews] = useState([]);

    const [relatedMovies, setRelatedMovies] = useState([]);

    const [loading, setLoading] = useState(true);

    const [review, setReview] = useState("");

    const [rating, setRating] = useState(5);
    const [inWatchlist, setInWatchlist] = useState(false);

    // =====================================
    // LOAD PAGE
    // =====================================

    useEffect(() => {

        loadMovie();

    }, [id]);

    const loadMovie = async () => {

        try {

            setLoading(true);

            const movieResponse =
                await MovieService.getMovieById(id);

            setMovie(movieResponse.data);

            const reviewResponse =
                await ReviewService.getMovieReviews(id);

            setReviews(reviewResponse.data);

            const relatedResponse =
                await MovieService.getRelatedMovies(id);

            setRelatedMovies(relatedResponse.data);

            try {
                const existsResp = await WatchlistService.exists(id);
                setInWatchlist(Boolean(existsResp.data));
            } catch (err) {
                console.error("watchlist exists check", err);
            }

        }

        catch (error) {

            console.log(error);

        }

        finally {

            setLoading(false);

        }

    };

    // =====================================
    // SUBMIT REVIEW
    // =====================================

    const submitReview = async () => {

        try {

            await ReviewService.addReview({

                movieId: id,

                rating,

                comment: review

            });

            setReview("");

            setRating(5);

            loadMovie();

        }

        catch (error) {

            console.log(error);

        }

    };

    // =====================================
    // LOADING
    // =====================================

    if (loading) {

        return (

            <Container className="text-center mt-5">

                <Spinner

                    animation="border"

                    variant="danger"

                />

            </Container>

        );

    }
        // =====================================
    // JSX
    // =====================================

    return (

        <>

            {/* =====================================
                MOVIE BANNER
            ===================================== */}

            <div

                style={{

                    backgroundImage: `linear-gradient(rgba(0,0,0,.75), rgba(0,0,0,.90)), url(${MovieService.getBanner(movie.banner)})`,

                    backgroundSize: "cover",

                    backgroundPosition: "center",

                    minHeight: "100vh",

                    color: "#fff",

                    paddingTop: "40px",

                    paddingBottom: "60px"

                }}

            >

                <Container>

                    <Row className="align-items-center">

                        {/* ============================
                            POSTER
                        ============================ */}

                        <Col lg={4} className="text-center mb-4">

                            <img

                                src={MovieService.getPoster(movie.poster)}

                                alt={movie.name}

                                className="img-fluid rounded shadow-lg"

                                style={{

                                    maxHeight: "520px",

                                    objectFit: "cover"

                                }}

                            />

                        </Col>

                        {/* ============================
                            MOVIE DETAILS
                        ============================ */}

                        <Col lg={8}>

                            <h1 className="display-4 fw-bold">

                                {movie.name}

                            </h1>

                            <div className="my-3">

                                <Badge bg="danger" className="me-2">

                                    {movie.genre}

                                </Badge>

                                <Badge bg="secondary" className="me-2">

                                    {movie.language}

                                </Badge>

                                <Badge bg="warning" text="dark">

                                    <FaStar className="me-1" />

                                    {movie.rating}
                                </Badge>

                            </div>

                            <p className="lead">

                                {movie.description}

                            </p>

                            <Row className="mt-4">

                                <Col md={6} className="mb-3">

                                    <FaCalendarAlt className="me-2" />

                                    <strong>Release Year :</strong>

                                    {" "}

                                    {movie.year}

                                </Col>

                                <Col md={6} className="mb-3">

                                    <FaClock className="me-2" />

                                    <strong>Duration :</strong>

                                    {" "}

                                    {movie.duration} mins

                                </Col>

                                <Col md={6} className="mb-3">

                                    <FaGlobe className="me-2" />

                                    <strong>Language :</strong>

                                    {" "}

                                    {movie.language}

                                </Col>

                                <Col md={6} className="mb-3">

                                    <FaEye className="me-2" />

                                    <strong>Views :</strong>

                                    {" "}

                                    {movie.views || 0}

                                </Col>

                            </Row>

                            <div className="mt-4">

                                <strong>Director :</strong>

                                {" "}

                                {movie.director}

                            </div>

                            <div className="mt-2 mb-4">

                                <strong>Cast :</strong>

                                {" "}

                                {movie.cast}

                            </div>

                            <div className="d-flex flex-wrap gap-3">

                                <Button

                                    variant="danger"

                                    size="lg"

                                >

                                    <FaPlay className="me-2" />

                                    Watch Movie

                                </Button>

                                <Button
                                    variant={inWatchlist ? "danger" : "outline-light"}
                                    size="lg"
                                    onClick={async () => {
                                        try {
                                            if (inWatchlist) {
                                                await WatchlistService.removeFromWatchlist(movie.id);
                                                setInWatchlist(false);
                                                alert("Removed from My Watchlist");
                                            } else {
                                                await WatchlistService.addToWatchlist(movie.id);
                                                setInWatchlist(true);
                                                alert("Added to My Watchlist");
                                            }
                                        } catch (err) {
                                            console.error(err);
                                            alert("Could not update My Watchlist");
                                        }
                                    }}
                                >
                                    <FaHeart className="me-2" />
                                    {inWatchlist ? "Remove from My Watchlist" : "Add to My Watchlist"}
                                </Button>

                                {

                                    movie.trailer &&

                                    <Button

                                        variant="warning"

                                        size="lg"

                                    >

                                        ▶ Watch Trailer

                                    </Button>

                                }

                            </div>

                        </Col>

                    </Row>
                                        {/* =====================================
                        MOVIE PLAYER
                    ===================================== */}

                    <Row className="mt-5">

                        <Col>

                            <Card

                                bg="dark"

                                text="light"

                                className="shadow-lg"

                            >

                                <Card.Header>

                                    <h4 className="mb-0">

                                        🎬 Watch Movie

                                    </h4>

                                </Card.Header>

                                <Card.Body>

                                    {

                                        movie.video ?

                                        (

                                            <video

                                                controls

                                                width="100%"

                                                poster={MovieService.getPoster(movie.poster)}

                                                style={{

                                                    borderRadius: "10px"

                                                }}

                                            >

                                                <source

                                                    src={MovieService.getVideo(movie.video)}

                                                    type="video/mp4"

                                                />

                                                Your browser does not support video.

                                            </video>

                                        )

                                        :

                                        (

                                            <div className="text-center py-5">

                                                <h5>

                                                    Movie is not available.

                                                </h5>

                                            </div>

                                        )

                                    }

                                </Card.Body>

                            </Card>

                        </Col>

// � FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.


                    </Row>

                    {/* =====================================
                        TRAILER
                    ===================================== */}

                    {

                        movie.trailer &&

                        <Row className="mt-5">

                            <Col>

                                <Card

                                    bg="dark"

                                    text="light"

                                    className="shadow"

                                >

                                    <Card.Header>

                                        <h4 className="mb-0">

                                            🎞 Official Trailer

                                        </h4>

                                    </Card.Header>

                                    <Card.Body>

                                        <video

                                            controls

                                            width="100%"

                                            style={{

                                                borderRadius: "10px"

                                            }}

                                        >

                                            <source

                                                src={MovieService.getTrailer(movie.trailer)}

                                                type="video/mp4"

                                            />

                                        </video>

                                    </Card.Body>

                                </Card>

                            </Col>

                        </Row>

                    }

                    {/* =====================================
                        ADD REVIEW
                    ===================================== */}

                    <Row className="mt-5">

                        <Col lg={8}>

                            <Card

                                bg="dark"

                                text="light"

                            >

                                <Card.Header>

                                    <h4>

                                        ⭐ Write a Review

                                    </h4>

                                </Card.Header>

                                <Card.Body>

                                    <Form.Group className="mb-3">

                                        <Form.Label>

                                            Rating

                                        </Form.Label>

                                        <Form.Select

                                            value={rating}

                                            onChange={(e) =>

                                                setRating(

                                                    e.target.value

                                                )

                                            }

                                        >

                                            <option value="5">

                                                ⭐⭐⭐⭐⭐ (5)

                                            </option>

                                            <option value="4">

                                                ⭐⭐⭐⭐ (4)

                                            </option>

                                            <option value="3">

                                                ⭐⭐⭐ (3)

                                            </option>

                                            <option value="2">

                                                ⭐⭐ (2)

                                            </option>

                                            <option value="1">

                                                ⭐ (1)

                                            </option>

                                        </Form.Select>

                                    </Form.Group>

                                    <Form.Group>

                                        <Form.Label>

                                            Review

                                        </Form.Label>

                                        <Form.Control

                                            as="textarea"

                                            rows={4}

                                            placeholder="Write your review..."

                                            value={review}

                                            onChange={(e) =>

                                                setReview(

                                                    e.target.value

                                                )

                                            }

                                        />

                                    </Form.Group>

                                    <Button

                                        variant="danger"

                                        className="mt-3"

                                        onClick={submitReview}

                                    >

                                        Submit Review

                                    </Button>

                                </Card.Body>

                            </Card>

                        </Col>

                    </Row>
                                        {/* =====================================
                        REVIEWS
                    ===================================== */}

                    <Row className="mt-5">

                        <Col>

                            <h3 className="mb-4">

                                ⭐ User Reviews

                            </h3>

                            {

                                reviews.length === 0 ?

                                (

                                    <Card bg="dark" text="light">

                                        <Card.Body className="text-center">

                                            No Reviews Available

                                        </Card.Body>

                                    </Card>

                                )

                                :

                                reviews.map((item, index) => (

                                    <Card

                                        key={index}

                                        bg="dark"

                                        text="light"

                                        className="mb-3 shadow"

                                    >

                                        <Card.Body>

                                            <div className="d-flex justify-content-between">

                                                <div>

                                                    <h5>

                                                        {

                                                            item.user?.username ||

                                                            "Anonymous"

                                                        }

                                                    </h5>

                                                </div>

                                                <Badge

                                                    bg="warning"

                                                    text="dark"

                                                >

                                                    ⭐ {item.rating}

                                                </Badge>

                                            </div>

                                            <hr />

                                            <p className="mb-0">

                                                {

                                                    item.comment

                                                }

                                            </p>

                                        </Card.Body>

                                    </Card>

                                ))

                            }

                        </Col>

                    </Row>

                    {/* =====================================
                        RELATED MOVIES
                    ===================================== */}

                    <Row className="mt-5">

                        <Col>

                            <h3 className="mb-4">

                                🎬 Related Movies

                            </h3>

                        </Col>

                    </Row>

                    <Row>

                        {

                            relatedMovies.length === 0 ?

                            (

                                <Col>

                                    <Card

                                        bg="dark"

                                        text="light"

                                    >

                                        <Card.Body className="text-center">

                                            No Related Movies Found

                                        </Card.Body>

                                    </Card>

                                </Col>

                            )

                            :

                            relatedMovies.map((related) => (

                                <Col

                                    lg={3}

                                    md={4}

                                    sm={6}

                                    className="mb-4"

                                    key={related.id}

                                >

                                    <Card

                                        className="h-100 shadow"

                                    >

                                        <Card.Img

                                            variant="top"

                                            src={MovieService.getPoster(

                                                related.poster

                                            )}

                                            style={{

                                                height: "330px",

                                                objectFit: "cover"

                                            }}

                                        />

                                        <Card.Body>

                                            <Card.Title>

                                                {

                                                    related.name

                                                }

                                            </Card.Title>

                                            <Badge bg="secondary">

                                                {

                                                    related.genre

                                                }

                                            </Badge>

                                            <div className="mt-2">

                                                <FaStar

                                                    color="gold"

                                                />

                                                <strong className="ms-2">

                                                    {

                                                        related.rating

                                                    }

                                                </strong>

                                            </div>

                                            <Link

                                                to={`/watch/${related.id}`}

                                            >

                                                <Button

                                                    variant="danger"

                                                    className="w-100 mt-3"

                                                >

                                                    <FaPlay className="me-2" />

                                                    Watch Now

                                                </Button>

                                            </Link>

                                        </Card.Body>

                                    </Card>

                                </Col>

                            ))

                        }

                    </Row>

                </Container>

            </div>

        </>

    );

};

export default WatchMovie;
