import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import MovieService from "../services/MovieService";

const AddMovie = () => {

    const navigate = useNavigate();

    const [movie, setMovie] = useState({
        name: "",
        director: "",
        description: "",
        genre: "",
        actors: "",
        distributor: "",
        year: "",
        duration: "",
        rating: 0,
        featured: false,
        trending: false,
        active: true
    });

    const [poster, setPoster] = useState(null);
    const [banner, setBanner] = useState(null);
    const [video, setVideo] = useState(null);
    const [trailer, setTrailer] = useState(null);

    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {

        const { name, value, type, checked } = e.target;

        setMovie({

            ...movie,

            [name]: type === "checkbox"
                ? checked
                : value

        });

    };

    const handlePoster = (e) => {

        setPoster(e.target.files[0]);

    };

    const handleBanner = (e) => {

        setBanner(e.target.files[0]);

    };

    const handleVideo = (e) => {

        setVideo(e.target.files[0]);

    };

    const handleTrailer = (e) => {

        setTrailer(e.target.files[0]);

    };
        const saveMovie = async (e) => {

        e.preventDefault();

        if (!movie.name.trim()) {

            alert("Movie Name is required");

            return;

        }

        if (!movie.director.trim()) {

            alert("Director is required");

            return;

        }

        if (!movie.genre.trim()) {

            alert("Genre is required");

            return;

        }

        if (!movie.description.trim()) {

            alert("Description is required");

            return;

        }

        if (!poster) {

            alert("Poster is required");

            return;

        }

        if (!video) {

            alert("Movie Video is required");

            return;

        }

        try {

            setLoading(true);

            const formData = new FormData();

            formData.append(

                "movie",

                JSON.stringify(movie)

            );

            formData.append(

                "poster",

                poster

            );

            if (banner) {

                formData.append(

                    "banner",

                    banner

                );

            }

            formData.append(

                "video",

                video

            );

            if (trailer) {

                formData.append(

                    "trailer",

                    trailer

                );

            }

            await MovieService.createMovie(

                formData

            );

            alert(

                "Movie Uploaded Successfully"

            );

            navigate("/admin");

        }

        catch (error) {

            console.log(error);

            alert(

                "Movie Upload Failed"

            );

        }

        finally {

            setLoading(false);

        }

    };
        return (

        <div className="container mt-4">

            <div className="card shadow">

                <div className="card-header bg-dark text-white">

                    <h3>

                        Add New Movie

                    </h3>

                </div>

                <div className="card-body">

                    <form onSubmit={saveMovie}>

                        <div className="row">

                            <div className="col-md-6 mb-3">

                                <label className="form-label">

                                    Movie Name

                                </label>

                                <input

                                    type="text"

                                    className="form-control"

                                    name="name"

                                    value={movie.name}

                                    onChange={handleChange}

                                    required

                                />

                            </div>

                            <div className="col-md-6 mb-3">

                                <label className="form-label">

                                    Director

                                </label>

                                <input

                                    type="text"

                                    className="form-control"

                                    name="director"

                                    value={movie.director}

                                    onChange={handleChange}

                                    required

                                />

                            </div>

                        </div>

                        <div className="row">

                            <div className="col-md-6 mb-3">

                                <label className="form-label">

                                    Genre

                                </label>

                                <input

                                    type="text"

                                    className="form-control"

                                    name="genre"

                                    value={movie.genre}

                                    onChange={handleChange}

                                />

                            </div>

                            <div className="col-md-6 mb-3">

                                <label className="form-label">

                                    Actors

                                </label>

                                <input

                                    type="text"

                                    className="form-control"

                                    name="actors"

                                    value={movie.actors}

                                    onChange={handleChange}

                                />

                            </div>

                        </div>

                        <div className="mb-3">

                            <label className="form-label">

                                Distributor

// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.


                            </label>

                            <input

                                type="text"

                                className="form-control"

                                name="distributor"

                                value={movie.distributor}

                                onChange={handleChange}

                            />

                        </div>

                        <div className="mb-3">

                            <label className="form-label">

                                Description

                            </label>

                            <textarea

                                className="form-control"

                                rows="5"

                                name="description"

                                value={movie.description}

                                onChange={handleChange}

                            />

                        </div>

                        <div className="row">

                            <div className="col-md-4 mb-3">

                                <label className="form-label">

                                    Release Year

                                </label>

                                <input

                                    type="number"

                                    className="form-control"

                                    name="year"

                                    value={movie.year}

                                    onChange={handleChange}

                                />

                            </div>

                            <div className="col-md-4 mb-3">

                                <label className="form-label">

                                    Duration (Minutes)

                                </label>

                                <input

                                    type="number"

                                    className="form-control"

                                    name="duration"

                                    value={movie.duration}

                                    onChange={handleChange}

                                />

                            </div>

                            <div className="col-md-4 mb-3">

                                <label className="form-label">

                                    Rating

                                </label>

                                <input

                                    type="number"

                                    className="form-control"

                                    step="0.1"

                                    min="0"

                                    max="10"

                                    name="rating"

                                    value={movie.rating}

                                    onChange={handleChange}

                                />

                            </div>

                        </div>

                        <div className="row">

                            <div className="col-md-6 mb-3">

                                <label className="form-label">

                                    Poster

                                </label>

                                <input

                                    type="file"

                                    accept="image/*"

                                    className="form-control"

                                    onChange={handlePoster}

                                />

                            </div>

                            <div className="col-md-6 mb-3">

                                <label className="form-label">

                                    Banner

                                </label>

                                <input

                                    type="file"

                                    accept="image/*"

                                    className="form-control"

                                    onChange={handleBanner}

                                />

                            </div>

                        </div>

                        <div className="row">

                            <div className="col-md-6 mb-3">

                                <label className="form-label">

                                    Movie Video

                                </label>

                                <input

                                    type="file"

                                    accept="video/*"

                                    className="form-control"

                                    onChange={handleVideo}

                                />

                            </div>

                            <div className="col-md-6 mb-3">

                                <label className="form-label">

                                    Trailer

                                </label>

                                <input

                                    type="file"

                                    accept="video/*"

                                    className="form-control"

                                    onChange={handleTrailer}

                                />

                            </div>

                        </div>

                        <div className="row mb-4">

                            <div className="col-md-4">

                                <div className="form-check">

                                    <input

                                        className="form-check-input"

                                        type="checkbox"

                                        name="featured"

                                        checked={movie.featured}

                                        onChange={handleChange}

                                    />

                                    <label className="form-check-label">

                                        Featured

                                    </label>

                                </div>

                            </div>

                            <div className="col-md-4">

                                <div className="form-check">

                                    <input

                                        className="form-check-input"

                                        type="checkbox"

                                        name="trending"

                                        checked={movie.trending}

                                        onChange={handleChange}

                                    />

                                    <label className="form-check-label">

                                        Trending

                                    </label>

                                </div>

                            </div>

                            <div className="col-md-4">

                                <div className="form-check">

                                    <input

                                        className="form-check-input"

                                        type="checkbox"

                                        name="active"

                                        checked={movie.active}

                                        onChange={handleChange}

                                    />

                                    <label className="form-check-label">

                                        Active

                                    </label>

                                </div>

                            </div>

                        </div>
                                                <div className="d-flex justify-content-between">

                            <button
                                type="submit"
                                className="btn btn-success btn-lg"
                                disabled={loading}
                            >

                                {
                                    loading
                                        ? "Uploading Movie..."
                                        : "Upload Movie"
                                }

                            </button>

                            <button
                                type="button"
                                className="btn btn-secondary btn-lg"
                                onClick={() => navigate("/admin")}
                            >

                                Cancel

                            </button>

                        </div>

                    </form>

                </div>

            </div>

        </div>

    );

};

export default AddMovie;
