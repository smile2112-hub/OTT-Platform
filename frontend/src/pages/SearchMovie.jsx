import React, { useEffect, useState } from "react";

import {
    Badge,
    Button,
    Card,
    Col,
    Container,
    Form,
    InputGroup,
    Pagination,
    Row,
    Spinner
} from "react-bootstrap";

import {
    FaHeart,
    FaPlay,
    FaSearch,
    FaStar
} from "react-icons/fa";

import { Link } from "react-router-dom";

import MovieService from "../services/MovieService";
import WatchlistService from "../services/WatchlistService";

const SearchMovie = () => {

    // =====================================
    // STATES
    // =====================================

    const [movies, setMovies] = useState([]);

    const [filteredMovies, setFilteredMovies] = useState([]);

    const [loading, setLoading] = useState(true);

    const [search, setSearch] = useState("");

    const [genre, setGenre] = useState("");

    const [language, setLanguage] = useState("");

    const [rating, setRating] = useState("");

    const [currentPage, setCurrentPage] = useState(1);

    const moviesPerPage = 12;

    const [watchlistMap, setWatchlistMap] = useState({});

    const ensureWatchlistStatus = async (movieId) => {
        if (watchlistMap[movieId] !== undefined) return;
        try {
            const resp = await WatchlistService.exists(movieId);
            setWatchlistMap(m => ({ ...m, [movieId]: Boolean(resp.data) }));
        } catch (err) {
            console.error("watchlist check", err);
        }
    };

    const toggleWatchlist = async (movieId, e) => {
        if (e) e.preventDefault();
        try {
            const known = watchlistMap[movieId];
            let exists = known;
            if (known === undefined) {
                const resp = await WatchlistService.exists(movieId);
                exists = Boolean(resp.data);
            }

            if (exists) {
                await WatchlistService.removeFromWatchlist(movieId);
                setWatchlistMap(m => ({ ...m, [movieId]: false }));
                alert("Removed from watchlist");
            } else {
                await WatchlistService.addToWatchlist(movieId);
                setWatchlistMap(m => ({ ...m, [movieId]: true }));
                alert("Added to watchlist");
            }

        } catch (err) {
            console.error(err);
            alert("Could not update watchlist");
        }
    };

    // =====================================
    // LOAD MOVIES
    // =====================================

    useEffect(() => {

        loadMovies();

    }, []);

    const loadMovies = async () => {

        try {

            setLoading(true);

            const response =
                await MovieService.getAllMovies();

            setMovies(response.data);

            setFilteredMovies(response.data);

        }

        catch (error) {

            console.log(error);

        }

        finally {

            setLoading(false);

        }

    };

    // =====================================
    // FILTER
    // =====================================

    useEffect(() => {

        let data = [...movies];

        if (search.trim() !== "") {

            const keyword = search.toLowerCase();

            data = data.filter(movie =>

                (movie.name || "")
                    .toLowerCase()
                    .includes(keyword)

                ||

                (movie.genre || "")
                    .toLowerCase()
                    .includes(keyword)

                ||

                (movie.director || "")
                    .toLowerCase()
                    .includes(keyword)

            );

        }

        if (genre !== "") {

            data = data.filter(

                movie => movie.genre === genre

            );

        }

        if (language !== "") {

            data = data.filter(

                movie => movie.language === language

            );

        }

        if (rating !== "") {

            data = data.filter(

                movie =>

                    Number(movie.rating) >=

                    Number(rating)

            );

        }

        setFilteredMovies(data);

        setCurrentPage(1);

    }, [

        movies,

        search,

        genre,

        language,

        rating

    ]);
        // =====================================
    // PAGINATION
    // =====================================

    const indexOfLastMovie =
        currentPage * moviesPerPage;

    const indexOfFirstMovie =
        indexOfLastMovie - moviesPerPage;

    const currentMovies =
        filteredMovies.slice(
            indexOfFirstMovie,
            indexOfLastMovie
        );

    const totalPages =
        Math.ceil(
            filteredMovies.length /
            moviesPerPage
        );

    // =====================================
    // JSX
    // =====================================

    return (

        <Container fluid className="mt-4 mb-5">

            <h2 className="mb-4">

                Search Movies

            </h2>

            {/* =====================================
                FILTERS
            ===================================== */}

            <Row className="mb-4">

                <Col lg={4} md={6} className="mb-3">

                    <InputGroup>

                        <InputGroup.Text>

                            <FaSearch />

                        </InputGroup.Text>

                        <Form.Control

                            placeholder="Search movie..."

                            value={search}

                            onChange={(e) =>
                                setSearch(e.target.value)
                            }

                        />

                    </InputGroup>

                </Col>

                <Col lg={2} md={6} className="mb-3">

                    <Form.Select

                        value={genre}

                        onChange={(e) =>
                            setGenre(e.target.value)
                        }

                    >

                        <option value="">

                            All Genres

                        </option>

                        <option>Action</option>

                        <option>Comedy</option>

                        <option>Drama</option>

                        <option>Horror</option>

                        <option>Romance</option>

                        <option>Sci-Fi</option>

                        <option>Thriller</option>

                    </Form.Select>

                </Col>

                <Col lg={2} md={6} className="mb-3">

                    <Form.Select

                        value={language}

                        onChange={(e) =>
                            setLanguage(e.target.value)
                        }

                    >

                        <option value="">

                            All Languages

                        </option>

                        <option>English</option>

                        <option>Hindi</option>

                        <option>Marathi</option>

                        <option>Tamil</option>

// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.


                        <option>Telugu</option>

                    </Form.Select>

                </Col>

                <Col lg={2} md={6} className="mb-3">

                    <Form.Select

                        value={rating}

                        onChange={(e) =>
                            setRating(e.target.value)
                        }

                    >

                        <option value="">

                            Any Rating

                        </option>

                        <option value="9">

                            9+

                        </option>

                        <option value="8">

                            8+

                        </option>

                        <option value="7">

                            7+

                        </option>

                        <option value="6">

                            6+

                        </option>

                        <option value="5">

                            5+

                        </option>

                    </Form.Select>

                </Col>

            </Row>

            {/* =====================================
                LOADING
            ===================================== */}

            {

                loading ?

                (

                    <div className="text-center py-5">

                        <Spinner

                            animation="border"

                            variant="danger"

                        />

                    </div>

                )

                :

                (

                    <Row>

                        {

                            currentMovies.length === 0 ?

                            (

                                <Col>

                                    <div className="text-center py-5">

                                        <h4>

                                            No Movies Found

                                        </h4>

                                    </div>

                                </Col>

                            )

                            :

                            currentMovies.map(movie => (

                                <Col

                                    lg={3}

                                    md={4}

                                    sm={6}

                                    className="mb-4"

                                    key={movie.id}

                                >

                                    <Card className="shadow h-100">

                                        <Card.Img

                                            variant="top"

                                            src={MovieService.getPoster(movie.poster)}

                                            style={{

                                                height: "380px",

                                                objectFit: "cover"

                                            }}

                                        />

                                        <Card.Body>

                                            <Card.Title>

                                                {movie.name}

                                            </Card.Title>

                                            <Badge bg="secondary">

                                                {movie.genre}

                                            </Badge>

                                            <div className="mt-2">

                                                <FaStar

                                                    color="gold"

                                                />

                                                <strong className="ms-2">

                                                    {

                                                        movie.rating ||

                                                        "0"

                                                    }

                                                </strong>

                                            </div>

                                            <div className="d-grid gap-2 mt-3">

                                                <Link to={`/watch/${movie.id}`}>

                                                    <Button variant="danger" className="w-100">

                                                        <FaPlay className="me-2" />

                                                        Watch

                                                    </Button>

                                                </Link>

                                                <div style={{ display: "flex", justifyContent: "flex-end", marginTop: 8 }}>
                                                        <Button
                                                            variant={watchlistMap[movie.id] ? "danger" : "outline-danger"}
                                                            title={watchlistMap[movie.id] ? "Remove from watchlist" : "My Watchlist"}
                                                            aria-label={watchlistMap[movie.id] ? "Remove from watchlist" : "My Watchlist"}
                                                            onMouseEnter={() => ensureWatchlistStatus(movie.id)}
                                                            onClick={(e) => toggleWatchlist(movie.id, e)}
                                                            style={{
                                                                width: 40,
                                                                height: 40,
                                                                borderRadius: "50%",
                                                                padding: 0,
                                                                display: "inline-flex",
                                                                alignItems: "center",
                                                                justifyContent: "center"
                                                            }}
                                                        >
                                                            <strong style={{ lineHeight: 1, fontSize: 20 }}>+</strong>
                                                        </Button>
                                                </div>

                                            </div>

                                        </Card.Body>

                                    </Card>

                                </Col>

                            ))

                        }

                    </Row>

                )

            }
                        {/* =====================================
                PAGINATION
            ===================================== */}

            {

                totalPages > 1 && (

                    <div className="d-flex justify-content-center mt-4">

                        <Pagination>

                            <Pagination.First

                                disabled={currentPage === 1}

                                onClick={() =>
                                    setCurrentPage(1)
                                }

                            />

                            <Pagination.Prev

                                disabled={currentPage === 1}

                                onClick={() =>
                                    setCurrentPage(
                                        currentPage - 1
                                    )
                                }

                            />

                            {

                                [...Array(totalPages)].map(

                                    (_, i) => (

                                        <Pagination.Item

                                            key={i}

                                            active={
                                                currentPage ===
                                                i + 1
                                            }

                                            onClick={() =>
                                                setCurrentPage(
                                                    i + 1
                                                )
                                            }

                                        >

                                            {i + 1}

                                        </Pagination.Item>

                                    )

                                )

                            }

                            <Pagination.Next

                                disabled={
                                    currentPage === totalPages
                                }

                                onClick={() =>
                                    setCurrentPage(
                                        currentPage + 1
                                    )
                                }

                            />

                            <Pagination.Last

                                disabled={
                                    currentPage === totalPages
                                }

                                onClick={() =>
                                    setCurrentPage(
                                        totalPages
                                    )
                                }

                            />

                        </Pagination>

                    </div>

                )

            }

        </Container>

    );

};

export default SearchMovie;
