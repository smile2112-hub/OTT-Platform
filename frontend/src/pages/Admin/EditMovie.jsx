/**
 * Author:  Aakanksha Ashok Hedau
 * Email:   aakanksha0499@gmail.com
 * Project: FlowFlix OTT Platform
 */
import React, { useEffect, useState, useCallback } from "react";

import {
    Button,
    Card,
    Col,
    Container,
    Form,
    Row,
    Spinner,
    Alert
} from "react-bootstrap";

import { useNavigate, useParams } from "react-router-dom";

import MovieService from "../../services/MovieService";

const EditMovie = () => {

    const { id } = useParams();

    const navigate = useNavigate();

    // =====================================
    // STATES
    // =====================================

    const [loading, setLoading] = useState(true);

    const [saving, setSaving] = useState(false);

    const [success, setSuccess] = useState("");

    const [error, setError] = useState("");

    const [movie, setMovie] = useState({

        name: "",

        description: "",

        genre: "",

        language: "",

        director: "",

        cast: "",

        duration: "",

        year: "",

        rating: "",

        active: true,

        featured: false,

        trending: false

    });

    // =====================================
    // FILE STATES
    // =====================================

    const [poster, setPoster] = useState(null);

    const [banner, setBanner] = useState(null);

    const [video, setVideo] = useState(null);

    const [trailer, setTrailer] = useState(null);

    // =====================================
    // PREVIEW STATES
    // =====================================

    const [posterPreview, setPosterPreview] = useState("");

    const [bannerPreview, setBannerPreview] = useState("");

    // =====================================
    // LOAD MOVIE
    // =====================================

    const loadMovie = useCallback(async () => {

        try {

            setLoading(true);

            const response =
                await MovieService.getMovieById(id);

            setMovie(response.data);

            if (response.data.poster) {

                setPosterPreview(

                    MovieService.getPoster(

                        response.data.poster

                    )

                );

            }

            if (response.data.banner) {

                setBannerPreview(

                    MovieService.getBanner(

                        response.data.banner

                    )

                );

            }

        }

        catch (e) {

            console.log(e);

            setError("Unable to load movie.");

        }

        finally {

            setLoading(false);

        }

    }, [id]);

    useEffect(() => {

        loadMovie();

    }, [loadMovie]);

    // =====================================
    // INPUT CHANGE
    // =====================================

    const handleChange = (e) => {

        const { name, value, type, checked } = e.target;

        setMovie({

            ...movie,

            [name]: type === "checkbox" ? checked : value

        });

    };

    // =====================================
    // POSTER
    // =====================================

    const handlePoster = (e) => {

        if (!e.target.files.length) {

            return;

        }

        const file = e.target.files[0];

        setPoster(file);

        setPosterPreview(

            URL.createObjectURL(file)

        );

    };

    // =====================================
    // BANNER
    // =====================================

    const handleBanner = (e) => {

        if (!e.target.files.length) {

            return;

        }

        const file = e.target.files[0];

        setBanner(file);

        setBannerPreview(

            URL.createObjectURL(file)

        );

    };

    // =====================================
    // VIDEO
    // =====================================

    const handleVideo = (e) => {

        if (!e.target.files.length) {

            return;

        }

        setVideo(

            e.target.files[0]

        );

    };

    // =====================================
    // TRAILER
    // =====================================

    const handleTrailer = (e) => {

        if (!e.target.files.length) {

            return;

        }

        setTrailer(

            e.target.files[0]

        );

    };

    // =====================================
    // VALIDATION
    // =====================================

    const validate = () => {

        if (!movie.name.trim()) {

            setError("Movie name is required.");

            return false;

        }

        if (!movie.genre.trim()) {

            setError("Genre is required.");

            return false;

        }

        if (!movie.language.trim()) {

            setError("Language is required.");

            return false;

        }

        if (!movie.director.trim()) {

            setError("Director is required.");

            return false;

        }

        if (!movie.description.trim()) {

            setError("Description is required.");

            return false;

        }

        if (!movie.year) {

            setError("Release year is required.");

            return false;

        }

        if (!movie.duration) {

            setError("Duration is required.");

            return false;

        }

        return true;

    };

    // =====================================
    // UPDATE MOVIE
    // =====================================

    const updateMovie = async (e) => {

        e.preventDefault();

        setError("");

        setSuccess("");

        if (!validate()) {

            return;

        }

        try {

            setSaving(true);

            const formData = new FormData();

            formData.append(

                "movie",

                new Blob(

                    [

                        JSON.stringify(movie)

                    ],

                    {

                        type: "application/json"

                    }

                )

            );

            if (poster) {

                formData.append(

                    "poster",

                    poster

                );

            }

            if (banner) {

                formData.append(

                    "banner",

                    banner

                );

            }

            if (video) {

                formData.append(

                    "video",

                    video

                );

            }

            if (trailer) {

                formData.append(

                    "trailer",

                    trailer

                );

            }

            await MovieService.updateMovie(

                id,

                formData

            );

            setSuccess(

                "Movie updated successfully."

            );

            setTimeout(() => {

                navigate("/admin/manageMovies");

            }, 1500);

        }

        catch (err) {

            console.log(err);

            setError(

                "Unable to update movie."

            );

        }

        finally {

            setSaving(false);

        }

    };    // =====================================
    // JSX
    // =====================================

    if (loading) {

        return (

            <Container className="mt-5 text-center">

                <Spinner

                    animation="border"

                    variant="primary"

                />

            </Container>

        );

    }

    return (

        <Container className="mt-4 mb-5">

            <Row className="justify-content-center">

                <Col lg={10}>

                    <Card className="shadow">

                        <Card.Header className="bg-primary text-white">

                            <h3 className="mb-0">

                                Edit Movie

                            </h3>

                        </Card.Header>

                        <Card.Body>

                            {

                                success &&

                                <Alert variant="success">

                                    {success}

                                </Alert>

                            }

                            {

                                error &&

                                <Alert variant="danger">

                                    {error}

                                </Alert>

                            }

                            <Form onSubmit={updateMovie}>

                                <Row>

                                    <Col md={6}>

                                        <Form.Group className="mb-3">

                                            <Form.Label>

                                                Movie Name

                                            </Form.Label>

                                            <Form.Control

                                                type="text"

                                                name="name"

                                                value={movie.name}

                                                onChange={handleChange}

                                                placeholder="Movie Name"

                                            />

                                        </Form.Group>

                                    </Col>

                                    <Col md={6}>

                                        <Form.Group className="mb-3">

                                            <Form.Label>

                                                Genre

                                            </Form.Label>

                                            <Form.Control

// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.


                                                type="text"

                                                name="genre"

                                                value={movie.genre}

                                                onChange={handleChange}

                                                placeholder="Genre"

                                            />

                                        </Form.Group>

                                    </Col>

                                </Row>

                                <Row>

                                    <Col md={6}>

                                        <Form.Group className="mb-3">

                                            <Form.Label>

                                                Language

                                            </Form.Label>

                                            <Form.Control

                                                type="text"

                                                name="language"

                                                value={movie.language}

                                                onChange={handleChange}

                                                placeholder="Language"

                                            />

                                        </Form.Group>

                                    </Col>

                                    <Col md={6}>

                                        <Form.Group className="mb-3">

                                            <Form.Label>

                                                Director

                                            </Form.Label>

                                            <Form.Control

                                                type="text"

                                                name="director"

                                                value={movie.director}

                                                onChange={handleChange}

                                                placeholder="Director"

                                            />

                                        </Form.Group>

                                    </Col>

                                </Row>

                                <Row>

                                    <Col md={12}>

                                        <Form.Group className="mb-3">

                                            <Form.Label>

                                                Cast

                                            </Form.Label>

                                            <Form.Control

                                                type="text"

                                                name="cast"

                                                value={movie.cast}

                                                onChange={handleChange}

                                                placeholder="Movie Cast"

                                            />

                                        </Form.Group>

                                    </Col>

                                </Row>

                                <Row>

                                    <Col>

                                        <Form.Group className="mb-3">

                                            <Form.Label>

                                                Description

                                            </Form.Label>

                                            <Form.Control

                                                as="textarea"

                                                rows={5}

                                                name="description"

                                                value={movie.description}

                                                onChange={handleChange}

                                                placeholder="Movie Description"

                                            />

                                        </Form.Group>

                                    </Col>

                                </Row>
                                                                <Row>

                                    <Col md={4}>

                                        <Form.Group className="mb-3">

                                            <Form.Label>

                                                Release Year

                                            </Form.Label>

                                            <Form.Control

                                                type="number"

                                                name="year"

                                                value={movie.year}

                                                onChange={handleChange}

                                                placeholder="2026"

                                            />

                                        </Form.Group>

                                    </Col>

                                    <Col md={4}>

                                        <Form.Group className="mb-3">

                                            <Form.Label>

                                                Duration (Minutes)

                                            </Form.Label>

                                            <Form.Control

                                                type="number"

                                                name="duration"

                                                value={movie.duration}

                                                onChange={handleChange}

                                                placeholder="120"

                                            />

                                        </Form.Group>

                                    </Col>

                                    <Col md={4}>

                                        <Form.Group className="mb-3">

                                            <Form.Label>

                                                Rating

                                            </Form.Label>

                                            <Form.Control

                                                type="number"

                                                step="0.1"

                                                min="0"

                                                max="10"

                                                name="rating"

                                                value={movie.rating}

                                                onChange={handleChange}

                                                placeholder="8.5"

                                            />

                                        </Form.Group>

                                    </Col>

                                </Row>

                                <hr />

                                <h5 className="mb-3">

                                    Movie Media

                                </h5>

                                <Row>

                                    <Col md={6}>

                                        <Form.Group className="mb-3">

                                            <Form.Label>

                                                Poster

                                            </Form.Label>

                                            <Form.Control

                                                type="file"

                                                accept="image/*"

                                                onChange={handlePoster}

                                            />

                                        </Form.Group>

                                    </Col>

                                    <Col md={6}>

                                        <Form.Group className="mb-3">

                                            <Form.Label>

                                                Banner

                                            </Form.Label>

                                            <Form.Control

                                                type="file"

                                                accept="image/*"

                                                onChange={handleBanner}

                                            />

                                        </Form.Group>

                                    </Col>

                                </Row>

                                <Row className="mb-4">

                                    <Col md={6} className="text-center">

                                        <h6>

                                            Poster Preview

                                        </h6>

                                        {

                                            posterPreview &&

                                            <img

                                                src={posterPreview}

                                                alt="Poster"

                                                className="img-fluid rounded shadow"

                                                style={{

                                                    maxHeight: "280px",

                                                    objectFit: "cover"

                                                }}

                                            />

                                        }

                                    </Col>

                                    <Col md={6} className="text-center">

                                        <h6>

                                            Banner Preview

                                        </h6>

                                        {

                                            bannerPreview &&

                                            <img

                                                src={bannerPreview}

                                                alt="Banner"

                                                className="img-fluid rounded shadow"

                                                style={{

                                                    width: "100%",

                                                    maxHeight: "220px",

                                                    objectFit: "cover"

                                                }}

                                            />

                                        }

                                    </Col>

                                </Row>

                                <Row>

                                    <Col md={6}>

                                        <Form.Group className="mb-3">

                                            <Form.Label>

                                                Movie Video

                                            </Form.Label>

                                            <Form.Control

                                                type="file"

                                                accept="video/*"

                                                onChange={handleVideo}

                                            />

                                        </Form.Group>

                                    </Col>

                                    <Col md={6}>

                                        <Form.Group className="mb-3">

                                            <Form.Label>

                                                Trailer Video

                                            </Form.Label>

                                            <Form.Control

                                                type="file"

                                                accept="video/*"

                                                onChange={handleTrailer}

                                            />

                                        </Form.Group>

                                    </Col>

                                </Row>
                                                                <hr />

                                <h5 className="mb-3">

                                    Movie Status

                                </h5>

                                <Row className="mb-4">

                                    <Col md={4}>

                                        <Form.Check

                                            type="checkbox"

                                            label="Active"

                                            name="active"

                                            checked={movie.active}

                                            onChange={handleChange}

                                        />

                                    </Col>

                                    <Col md={4}>

                                        <Form.Check

                                            type="checkbox"

                                            label="Featured"

                                            name="featured"

                                            checked={movie.featured}

                                            onChange={handleChange}

                                        />

                                    </Col>

                                    <Col md={4}>

                                        <Form.Check

                                            type="checkbox"

                                            label="Trending"

                                            name="trending"

                                            checked={movie.trending}

                                            onChange={handleChange}

                                        />

                                    </Col>

                                </Row>

                                <hr />

                                <div className="d-flex justify-content-end gap-3">

                                    <Button

                                        variant="secondary"

                                        onClick={() =>

                                            navigate("/admin/manageMovies")

                                        }

                                    >

                                        Cancel

                                    </Button>

                                    <Button

                                        variant="primary"

                                        type="submit"

                                        disabled={saving}

                                    >

                                        {

                                            saving ?

                                                (

                                                    <>

                                                        <Spinner

                                                            animation="border"

                                                            size="sm"

                                                            className="me-2"

                                                        />

                                                        Updating...

                                                    </>

                                                )

                                                :

                                                (

                                                    "Update Movie"

                                                )

                                        }

                                    </Button>

                                </div>

                            </Form>

                        </Card.Body>

                    </Card>

                </Col>

            </Row>

        </Container>

    );

};

export default EditMovie;
