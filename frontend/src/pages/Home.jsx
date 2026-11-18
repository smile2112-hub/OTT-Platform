import React, { useEffect, useState } from "react";

import {
    Button,
    Card,
    Col,
    Container,
    Row,
    Spinner,
    Badge
} from "react-bootstrap";

import {
    FaPlay,
    FaStar,
    FaFire,
    FaClock
} from "react-icons/fa";

import { Link } from "react-router-dom";

import MovieService from "../services/MovieService";

const Home = () => {

    // =====================================
    // STATES
    // =====================================

    const [featuredMovies, setFeaturedMovies] = useState([]);

    const [trendingMovies, setTrendingMovies] = useState([]);

    const [latestMovies, setLatestMovies] = useState([]);

    const [loading, setLoading] = useState(true);

    // =====================================
    // LOAD HOME DATA
    // =====================================

    useEffect(() => {

        loadHome();

    }, []);

    const loadHome = async () => {

        try {

            setLoading(true);

            const featured =
                await MovieService.getFeaturedMovies();

            const trending =
                await MovieService.getTrendingMovies();

            const latest =
                await MovieService.getLatestMovies();

            setFeaturedMovies(featured.data);

            setTrendingMovies(trending.data);

            setLatestMovies(latest.data);

        }

        catch (error) {

            console.log(error);

        }

        finally {

            setLoading(false);

        }

    };

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

    const heroMovie = featuredMovies.length > 0
        ? featuredMovies[0]
        : null;
            return (

        <>

            {/* =====================================
                HERO SECTION
            ===================================== */}

            {

                heroMovie && (

                    <div

                        style={{

                            minHeight: "90vh",

                            backgroundImage: `linear-gradient(rgba(0,0,0,.75), rgba(0,0,0,.90)), url(${MovieService.getBanner(heroMovie.banner)})`,

                            backgroundSize: "cover",

                            backgroundPosition: "center",

                            display: "flex",

                            alignItems: "center",

                            color: "#fff"

                        }}

                    >

                        <Container>

                            <Row>

                                <Col lg={7}>

                                    <Badge

                                        bg="danger"

                                        className="mb-3"

                                    >

                                        FEATURED

                                    </Badge>

                                    <h1

                                        className="display-3 fw-bold"

                                    >

                                        {

                                            heroMovie.name

                                        }

                                    </h1>

                                    <p

                                        className="lead mt-3"

                                    >

                                        {

                                            heroMovie.description

                                        }

                                    </p>

                                    <div className="mt-4">

                                        <Badge

                                            bg="warning"

                                            text="dark"

                                            className="me-2"

                                        >

                                            <FaStar className="me-1" />

                                            {

                                                heroMovie.rating

                                            }

                                        </Badge>

                                        <Badge

                                            bg="secondary"

                                            className="me-2"

                                        >

                                            {

                                                heroMovie.genre

                                            }

                                        </Badge>

                                        <Badge

                                            bg="info"

                                        >

                                            {

                                                heroMovie.year

                                            }

                                        </Badge>

                                    </div>

                                    <div className="mt-5">

                                        <Link

                                            to={`/watch/${heroMovie.id}`}

                                        >

                                            <Button

                                                size="lg"

                                                variant="danger"

                                                className="me-3"

                                            >

                                                <FaPlay className="me-2" />

                                                Watch Now

                                            </Button>

                                        </Link>

                                        <Link

                                            to={`/movie/${heroMovie.id}`}

                                        >

                                            <Button

                                                size="lg"

                                                variant="outline-light"

                                            >

                                                More Info

                                            </Button>

                                        </Link>

                                    </div>

                                </Col>

                            </Row>

                        </Container>

                    </div>

                )

            }

            <Container

                fluid

                className="mt-5 mb-5"

            >
                            {/* =====================================
                TRENDING MOVIES
            ===================================== */}

            <Row className="mb-5">

                <Col>

                    <h2 className="mb-4 fw-bold">

                        <FaFire className="text-danger me-2" />

                        Trending Movies

                    </h2>

                </Col>

            </Row>

            <Row>

                {

                    trendingMovies.length === 0 ?

                    (

                        <Col>

                            <div className="text-center py-5">

                                <h5>

                                    No Trending Movies Available

                                </h5>

                            </div>

                        </Col>

                    )

                    :

                    trendingMovies.map((movie) => (

                        <Col

                            lg={3}

                            md={4}

                            sm={6}

                            xs={12}

                            className="mb-4"

                            key={movie.id}

                        >

                            <Card

                                className="shadow h-100 border-0"

                                style={{

                                    transition: "0.3s",

                                    cursor: "pointer"

                                }}

                            >

                                <Card.Img

                                    variant="top"

                                    src={MovieService.getPoster(movie.poster)}

                                    style={{

                                        height: "370px",

                                        objectFit: "cover"

                                    }}

                                />

                                <Card.Body>

                                    <Card.Title>

                                        {movie.name}

                                    </Card.Title>

                                    <div className="mb-2">

                                        <Badge

                                            bg="secondary"

                                            className="me-2"

                                        >

                                            {movie.genre}

                                        </Badge>

                                        <Badge

                                            bg="warning"

                                            text="dark"

                                        >

                                            <FaStar className="me-1" />

                                            {movie.rating}

                                        </Badge>

                                    </div>

                                    <p

                                        className="text-muted"



                                        style={{

                                            fontSize: "14px",

                                            minHeight: "45px"

                                        }}

                                    >

                                        {

                                            movie.description?.length > 70

                                                ?

                                                movie.description.substring(0, 70) + "..."

                                                :

                                                movie.description

                                        }

                                    </p>

                                    <Link

                                        to={`/watch/${movie.id}`}

                                        className="text-decoration-none"

                                    >

                                        <Button

                                            variant="danger"

                                            className="w-100"

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
                        {/* =====================================
                LATEST MOVIES
            ===================================== */}

            <Row className="mt-5 mb-4">

                <Col>

                    <h2 className="fw-bold">

                        <FaClock className="text-primary me-2" />

                        Latest Movies

                    </h2>

                </Col>

            </Row>

            <Row>

                {

                    latestMovies.length === 0 ?

                    (

                        <Col>

                            <div className="text-center py-5">

                                <h5>

                                    No Latest Movies Available

                                </h5>

                            </div>

                        </Col>

                    )

                    :

                    latestMovies.map((movie) => (

                        <Col

                            lg={3}

                            md={4}

                            sm={6}

                            xs={12}

                            className="mb-4"

                            key={movie.id}

                        >

                            <Card

                                className="shadow h-100 border-0"

                                style={{

                                    transition: "0.3s",

                                    cursor: "pointer"

                                }}

                            >

                                <Card.Img

                                    variant="top"

                                    src={MovieService.getPoster(movie.poster)}

                                    style={{

                                        height: "370px",

                                        objectFit: "cover"

                                    }}

                                />

                                <Card.Body>

                                    <Card.Title>

                                        {movie.name}

                                    </Card.Title>

                                    <div className="mb-2">

                                        <Badge

                                            bg="secondary"

                                            className="me-2"

                                        >

                                            {movie.genre}

                                        </Badge>

                                        <Badge

                                            bg="warning"

                                            text="dark"

                                        >

                                            <FaStar className="me-1" />

                                            {movie.rating}

                                        </Badge>

                                    </div>

                                    <p

                                        className="text-muted"

                                        style={{

                                            fontSize: "14px",

                                            minHeight: "45px"

                                        }}

                                    >

                                        {

                                            movie.description?.length > 70

                                                ?

                                                movie.description.substring(0, 70) + "..."

                                                :

                                                movie.description

                                        }

                                    </p>

                                    <Link

                                        to={`/watch/${movie.id}`}

                                        className="text-decoration-none"

                                    >

                                        <Button

                                            variant="primary"

                                            className="w-100"

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

            {/* =====================================
                FEATURED MOVIES
            ===================================== */}

            <Row className="mt-5 mb-4">

                <Col>

                    <h2 className="fw-bold">

                        ⭐ Featured Movies

                    </h2>

                </Col>

            </Row>

            <Row>

                {

                    featuredMovies.map((movie) => (

                        <Col

                            lg={3}

                            md={4}

                            sm={6}

                            xs={12}

                            className="mb-4"

                            key={movie.id}

                        >

                            <Card

                                className="shadow h-100 border-0"

                            >

                                <Card.Img

                                    variant="top"

                                    src={MovieService.getPoster(movie.poster)}

                                    style={{

                                        height: "370px",

                                        objectFit: "cover"

                                    }}

                                />

                                <Card.Body>

                                    <Card.Title>

                                        {movie.name}

                                    </Card.Title>

                                    <Badge bg="danger">

                                        Featured

                                    </Badge>

                                    <div className="mt-3">

                                        <Link

                                            to={`/watch/${movie.id}`}

                                            className="text-decoration-none"

                                        >

                                            <Button

                                                variant="danger"

                                                className="w-100"

                                            >

                                                <FaPlay className="me-2" />

                                                Watch Now

                                            </Button>

                                        </Link>

                                    </div>

                                </Card.Body>

                            </Card>

                        </Col>

                    ))

                }

            </Row>
                        {/* =====================================
                FOOTER SECTION
            ===================================== */}

            <Row className="mt-5">

                <Col>

                    <Card

                        className="border-0 shadow"

                        bg="dark"

                        text="light"

                    >

                        <Card.Body className="text-center py-5">

                            <h2 className="mb-3">

                                🍿 Welcome to FlowFlix

                            </h2>

                            <p className="lead">

                                Stream thousands of movies anytime, anywhere.

                                Discover trending blockbusters, latest releases,

                                and timeless classics all in one place.

                            </p>

                            <Link

                                to="/search"

                                className="text-decoration-none"

                            >

                                <Button

                                    variant="danger"

                                    size="lg"

                                >

                                    Browse All Movies

                                </Button>

                            </Link>

                        </Card.Body>

                    </Card>

                </Col>

            </Row>

        </Container>

        </>

    );

};

export default Home;
