import React, { useEffect, useState } from "react";

import {
    Button,
    Card,
    Col,
    Container,
    Form,
    InputGroup,
    Modal,
    Pagination,
    Row,
    Spinner,
    Table,
    Badge
} from "react-bootstrap";

import {
    FaSearch,
    FaTrash,
    FaEdit,
    FaEye,
    FaStar,
    FaRegStar,
    FaFire,
    FaCheckCircle,
    FaTimesCircle
} from "react-icons/fa";

import { Link } from "react-router-dom";

import MovieService from "../../services/MovieService";

const ManageMovies = () => {

    // =====================================
    // STATES
    // =====================================

    const [movies, setMovies] = useState([]);

    const [filteredMovies, setFilteredMovies] = useState([]);

    const [loading, setLoading] = useState(true);

    const [search, setSearch] = useState("");

    const [currentPage, setCurrentPage] = useState(1);

    const moviesPerPage = 8;

    const [selectedMovie, setSelectedMovie] = useState(null);

    const [showDetails, setShowDetails] = useState(false);

    const [showDelete, setShowDelete] = useState(false);

    const [movieToDelete, setMovieToDelete] = useState(null);

    // =====================================
    // LOAD MOVIES
    // =====================================

    useEffect(() => {

        loadMovies();

    }, []);

    // =====================================
    // SEARCH FILTER
    // =====================================

    useEffect(() => {

        if (search.trim() === "") {

            setFilteredMovies(movies);

            return;

        }

        const keyword = search.toLowerCase();

        const data = movies.filter((movie) => {

            return (

                (movie.name || "")
                    .toLowerCase()
                    .includes(keyword)

                ||

                (movie.genre || "")
                    .toLowerCase()
                    .includes(keyword)

                ||

                (movie.language || "")
                    .toLowerCase()
                    .includes(keyword)

                ||

                (movie.director || "")
                    .toLowerCase()
                    .includes(keyword)

            );

        });

        setFilteredMovies(data);

        setCurrentPage(1);

    }, [search, movies]);

    // =====================================
    // LOAD DATA
    // =====================================

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
    // DELETE
    // =====================================

    const confirmDelete = (movie) => {

        setMovieToDelete(movie);

        setShowDelete(true);

    };

    const deleteMovie = async () => {

        try {

            await MovieService.deleteMovie(movieToDelete.id);

            setShowDelete(false);

            loadMovies();

        }

        catch (error) {

            console.log(error);

        }

    };

    // =====================================
    // ACTIVATE
    // =====================================

    const activateMovie = async (id) => {

        try {

            await MovieService.activateMovie(id);

            loadMovies();

        }

        catch (error) {

            console.log(error);

        }

    };

    // =====================================
    // DEACTIVATE
    // =====================================

    const deactivateMovie = async (id) => {

        try {

            await MovieService.deactivateMovie(id);

            loadMovies();

        }

        catch (error) {

            console.log(error);

        }

    };

    // =====================================
    // FEATURE
    // =====================================

    const featureMovie = async (id) => {

        try {

            await MovieService.featureMovie(id);

            loadMovies();

        }

        catch (error) {

            console.log(error);

        }

    };

    // =====================================
    // UNFEATURE
    // =====================================

    const unFeatureMovie = async (id) => {

        try {

            await MovieService.unFeatureMovie(id);

            loadMovies();

        }

        catch (error) {

            console.log(error);

        }

    };

    // =====================================
    // VIEW DETAILS
    // =====================================

    const viewMovie = (movie) => {

        setSelectedMovie(movie);

        setShowDetails(true);

    };
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

        <Container fluid className="mt-4">

            <Row>

                <Col>

                    <Card className="shadow border-0">

                        <Card.Header
                            className="bg-dark text-white">

                            <div className="d-flex justify-content-between align-items-center">

                                <div>

                                    <h3 className="mb-0">

                                        Movie Management

                                    </h3>

                                    <small>

                                        Total Movies :

                                        <strong className="ms-2">

                                            {

                                                filteredMovies.length

                                            }

                                        </strong>

                                    </small>

                                </div>

                                <Link to="/admin/addMovie">

                                    <Button
                                        variant="success">

                                        + Add Movie

                                    </Button>

                                </Link>

                            </div>

                        </Card.Header>

                        <Card.Body>

                            {/* =====================================
                                SEARCH BAR
                            ===================================== */}

                            <Row className="mb-4">

                                <Col lg={5} md={6}>

                                    <InputGroup>

                                        <InputGroup.Text>

                                            <FaSearch />

                                        </InputGroup.Text>

                                        <Form.Control

                                            placeholder="Search movie..."

                                            value={search}

                                            onChange={(e) =>
                                                setSearch(
                                                    e.target.value
                                                )
                                            }

                                        />

                                    </InputGroup>

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

                                                variant="primary"

                                            />

                                        </div>

                                    )

                                    :

                                    (

                                        <Table

                                            hover

                                            bordered

                                            responsive

                                            className="align-middle">

                                            <thead
                                                className="table-dark">

                                                <tr>

                                                    <th>#</th>

                                                    <th>Poster</th>

                                                    <th>Movie</th>

                                                    <th>Genre</th>

                                                    <th>Language</th>

                                                    <th>Year</th>

                                                    <th>Rating</th>

                                                    <th>Views</th>

                                                    <th>Status</th>

                                                    <th>Featured</th>

                                                    <th>Trending</th>

                                                    <th width="330">

                                                        Actions

                                                    </th>

                                                </tr>

                                            </thead>

                                            <tbody>

                                                {

                                                    currentMovies.length === 0 ?

                                                        (

                                                            <tr>

                                                                <td

                                                                    colSpan="12"

                                                                    className="text-center">

                                                                    No Movies Found

                                                                </td>

                                                            </tr>

                                                        )

                                                        :

                                                        currentMovies.map(

                                                            (

                                                                movie,

                                                                index

                                                            ) => (
                                                                <tr key={movie.id}>

                                                                    {/* =========================
                                                                        SERIAL NUMBER
                                                                    ========================= */}

                                                                    <td>

                                                                        {

                                                                            indexOfFirstMovie +

                                                                            index +

                                                                            1

                                                                        }

                                                                    </td>

                                                                    {/* =========================
                                                                        POSTER
                                                                    ========================= */}

                                                                    <td>

                                                                        <img

                                                                            src={MovieService.getPoster(movie.poster)}

                                                                            alt={movie.name}

                                                                            width="70"

                                                                            height="95"

                                                                            style={{

                                                                                objectFit: "cover",

                                                                                borderRadius: "8px"

                                                                            }}

                                                                        />

                                                                    </td>

                                                                    {/* =========================
                                                                        MOVIE NAME
                                                                    ========================= */}

                                                                    <td>

                                                                        <div>

                                                                            <strong>

                                                                                {movie.name}

                                                                            </strong>

                                                                            <br />

                                                                            <small className="text-muted">

                                                                                {movie.director}

                                                                            </small>

                                                                        </div>

                                                                    </td>

                                                                    {/* =========================
                                                                        GENRE
                                                                    ========================= */}

                                                                    <td>

                                                                        <Badge bg="secondary">

                                                                            {movie.genre}

                                                                        </Badge>

                                                                    </td>

                                                                    {/* =========================
                                                                        LANGUAGE
                                                                    ========================= */}

                                                                    <td>

                                                                        {

                                                                            movie.language ||

                                                                            "-"

                                                                        }

                                                                    </td>

                                                                    {/* =========================
                                                                        YEAR
                                                                    ========================= */}

                                                                    <td>

                                                                        {

                                                                            movie.year

                                                                        }

                                                                    </td>

                                                                    {/* =========================
                                                                        RATING
                                                                    ========================= */}

                                                                    <td>

                                                                        <Badge
                                                                            bg="warning"
                                                                            text="dark">

                                                                            ‚≠ê {

                                                                                movie.rating

                                                                                    ? Number(movie.rating).toFixed(1)

                                                                                    : "0.0"

                                                                            }

                                                                        </Badge>

                                                                    </td>

                                                                    {/* =========================
                                                                        TOTAL VIEWS
                                                                    ========================= */}

                                                                    <td>

                                                                        <Badge bg="info">

                                                                            {

                                                                                movie.totalViews ||

                                                                                0

                                                                            }

                                                                        </Badge>

                                                                    </td>

                                                                    {/* =========================
                                                                        ACTIVE STATUS
                                                                    ========================= */}

                                                                    <td>

                                                                        {

                                                                            movie.active ?

                                                                                (

                                                                                    <Badge bg="success">

                                                                                        Active

                                                                                    </Badge>

                                                                                )

                                                                                :

                                                                                (

                                                                                    <Badge bg="danger">

                                                                                        Inactive

                                                                                    </Badge>

                                                                                )

                                                                        }

                                                                    </td>

                                                                    {/* =========================
                                                                        FEATURED STATUS
                                                                    ========================= */}

                                                                    <td>

                                                                        {

                                                                            movie.featured ?

                                                                                (

                                                                                    <Badge bg="primary">

                                                                                        Yes


// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

                                                                                    </Badge>

                                                                                )

                                                                                :

                                                                                (

                                                                                    <Badge bg="secondary">

                                                                                        No

                                                                                    </Badge>

                                                                                )

                                                                        }

                                                                    </td>

                                                                    {/* =========================
                                                                        TRENDING STATUS
                                                                    ========================= */}

                                                                    <td>

                                                                        {

                                                                            movie.trending ?

                                                                                (

                                                                                    <Badge
                                                                                        bg="warning"
                                                                                        text="dark">

                                                                                        Yes

                                                                                    </Badge>

                                                                                )

                                                                                :

                                                                                (

                                                                                    <Badge
                                                                                        bg="secondary">

                                                                                        No

                                                                                    </Badge>

                                                                                )

                                                                        }

                                                                    </td>

                                                                    {/* =========================
                                                                        ACTIONS
                                                                    ========================= */}

                                                                    <td>

                                                                        <div className="d-flex flex-wrap gap-2">
                                                                                                                                                        {/* =====================================
                                                                                VIEW
                                                                            ===================================== */}

                                                                            <Button

                                                                                variant="info"

                                                                                size="sm"

                                                                                title="View Movie"

                                                                                onClick={() =>
                                                                                    viewMovie(movie)
                                                                                }

                                                                            >

                                                                                <FaEye />

                                                                            </Button>

                                                                            {/* =====================================
                                                                                EDIT
                                                                            ===================================== */}

                                                                            <Link
                                                                                to={`/admin/editMovie/${movie.id}`}>

                                                                                <Button

                                                                                    variant="primary"

                                                                                    size="sm"

                                                                                    title="Edit Movie"

                                                                                >

                                                                                    <FaEdit />

                                                                                </Button>

                                                                            </Link>

                                                                            {/* =====================================
                                                                                DELETE
                                                                            ===================================== */}

                                                                            <Button

                                                                                variant="danger"

                                                                                size="sm"

                                                                                title="Delete Movie"

                                                                                onClick={() =>
                                                                                    confirmDelete(movie)
                                                                                }

                                                                            >

                                                                                <FaTrash />

                                                                            </Button>

                                                                            {/* =====================================
                                                                                ACTIVATE / DEACTIVATE
                                                                            ===================================== */}

                                                                            {

                                                                                movie.active ?

                                                                                    (

                                                                                        <Button

                                                                                            variant="warning"

                                                                                            size="sm"

                                                                                            title="Deactivate"

                                                                                            onClick={() =>
                                                                                                deactivateMovie(movie.id)
                                                                                            }

                                                                                        >

                                                                                            <FaTimesCircle />

                                                                                        </Button>

                                                                                    )

                                                                                    :

                                                                                    (

                                                                                        <Button

                                                                                            variant="success"

                                                                                            size="sm"

                                                                                            title="Activate"

                                                                                            onClick={() =>
                                                                                                activateMovie(movie.id)
                                                                                            }

                                                                                        >

                                                                                            <FaCheckCircle />

                                                                                        </Button>

                                                                                    )

                                                                            }

                                                                            {/* =====================================
                                                                                FEATURE / UNFEATURE
                                                                            ===================================== */}

                                                                            {

                                                                                movie.featured ?

                                                                                    (

                                                                                        <Button

                                                                                            variant="secondary"

                                                                                            size="sm"

                                                                                            title="Remove Featured"

                                                                                            onClick={() =>
                                                                                                unFeatureMovie(movie.id)
                                                                                            }

                                                                                        >

                                                                                            <FaRegStar />

                                                                                        </Button>

                                                                                    )

                                                                                    :

                                                                                    (

                                                                                        <Button

                                                                                            variant="warning"

                                                                                            size="sm"

                                                                                            title="Make Featured"

                                                                                            onClick={() =>
                                                                                                featureMovie(movie.id)
                                                                                            }

                                                                                        >

                                                                                            <FaStar />

                                                                                        </Button>

                                                                                    )

                                                                            }

                                                                            {/* =====================================
                                                                                TRENDING / REMOVE TRENDING
                                                                            ===================================== */}

                                                                            {

                                                                                movie.trending ?

                                                                                    (

                                                                                        <Button

                                                                                            variant="dark"

                                                                                            size="sm"

                                                                                            title="Remove Trending"

                                                                                            onClick={async () => {

                                                                                                try {

                                                                                                    await MovieService.removeTrending(movie.id);

                                                                                                    loadMovies();

                                                                                                }

                                                                                                catch (error) {

                                                                                                    console.log(error);

                                                                                                }

                                                                                            }}

                                                                                        >

                                                                                            Remove

                                                                                        </Button>

                                                                                    )

                                                                                    :

                                                                                    (

                                                                                        <Button

                                                                                            variant="danger"

                                                                                            size="sm"

                                                                                            title="Make Trending"

                                                                                            onClick={async () => {

                                                                                                try {

                                                                                                    await MovieService.makeTrending(movie.id);

                                                                                                    loadMovies();

                                                                                                }

                                                                                                catch (error) {

                                                                                                    console.log(error);

                                                                                                }

                                                                                            }}

                                                                                        >

                                                                                            <FaFire />

                                                                                        </Button>

                                                                                    )

                                                                            }

                                                                        </div>

                                                                    </td>

                                                                </tr>

                                                            )

                                                        )

                                                }

                                            </tbody>

                                        </Table>

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

                        </Card.Body>

                    </Card>

                </Col>

            </Row>

            {/* =====================================
                MOVIE DETAILS MODAL
            ===================================== */}

            <Modal

                show={showDetails}

                onHide={() => setShowDetails(false)}

                centered

                size="lg"

            >

                <Modal.Header closeButton>

                    <Modal.Title>

                        Movie Details

                    </Modal.Title>

                </Modal.Header>

                <Modal.Body>

                    {

                        selectedMovie && (

                            <Row>

                                <Col md={4} className="text-center">

                                    <img

                                        src={
                                            MovieService.getPoster(
                                                selectedMovie.poster
                                            )
                                        }

                                        alt={selectedMovie.name}

                                        className="img-fluid rounded shadow"

                                    />

                                </Col>

                                <Col md={8}>

                                    <h3>

                                        {selectedMovie.name}

                                    </h3>

                                    <hr />

                                    <p>

                                        <strong>Genre :</strong>{" "}

                                        {selectedMovie.genre}

                                    </p>

                                    <p>

                                        <strong>Language :</strong>{" "}

                                        {selectedMovie.language}

                                    </p>

                                    <p>

                                        <strong>Director :</strong>{" "}

                                        {selectedMovie.director}

                                    </p>

                                    <p>

                                        <strong>Year :</strong>{" "}

                                        {selectedMovie.year}

                                    </p>

                                    <p>

                                        <strong>Duration :</strong>{" "}

                                        {selectedMovie.duration}

                                    </p>

                                    <p>

                                        <strong>Rating :</strong>{" "}

                                        {

                                            selectedMovie.rating

                                        }

                                    </p>

                                    <p>

                                        <strong>Total Views :</strong>{" "}

                                        {

                                            selectedMovie.totalViews

                                        }

                                    </p>

                                    <hr />

                                    <strong>

                                        Description

                                    </strong>

                                    <div
                                        className="border rounded p-3 bg-light mt-2">

                                        {

                                            selectedMovie.description

                                        }

                                    </div>

                                </Col>

                            </Row>

                        )

                    }

                </Modal.Body>

                <Modal.Footer>

                    <Button

                        variant="secondary"

                        onClick={() =>
                            setShowDetails(false)
                        }

                    >

                        Close

                    </Button>

                </Modal.Footer>

            </Modal>

            {/* =====================================
                DELETE CONFIRMATION MODAL
            ===================================== */}

            <Modal

                show={showDelete}

                onHide={() =>
                    setShowDelete(false)
                }

                centered

            >

                <Modal.Header closeButton>

                    <Modal.Title>

                        Delete Movie

                    </Modal.Title>

                </Modal.Header>

                <Modal.Body>

                    {

                        movieToDelete && (

                            <>

                                <h5>

                                    Are you sure?

                                </h5>

                                <p>

                                    This action will permanently delete

                                    <strong>

                                        {" "}

                                        {movieToDelete.name}

                                    </strong>

                                    .

                                </p>

                            </>

                        )

                    }

                </Modal.Body>

                <Modal.Footer>

                    <Button

                        variant="secondary"

                        onClick={() =>
                            setShowDelete(false)
                        }

                    >

                        Cancel

                    </Button>

                    <Button

                        variant="danger"

                        onClick={deleteMovie}

                    >

                        Delete Movie

                    </Button>

                </Modal.Footer>

            </Modal>        </Container>

    );

};

export default ManageMovies;
