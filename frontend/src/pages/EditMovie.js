import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import MovieService from "../services/MovieService";

const EditMovie = () => {

    const { id } = useParams();

    const navigate = useNavigate();

    const [movie, setMovie] = useState({

        id: "",

        name: "",

        director: "",

        description: "",

        genre: "",

        actors: "",

        distributor: "",

        year: "",

        duration: "",

        rating: "",

        featured: false,

        trending: false,

        active: true

    });

    const [poster, setPoster] = useState(null);

    const [banner, setBanner] = useState(null);

    const [video, setVideo] = useState(null);

    const [trailer, setTrailer] = useState(null);

    const [loading, setLoading] = useState(false);

    useEffect(() => {

        loadMovie();

    }, []);

    const loadMovie = async () => {

        try {

            const response = await MovieService.getMovie(id);

            setMovie(response.data);

        }

        catch (error) {

            console.log(error);

        }

    };

    const handleChange = (e) => {

        const {

            name,

            value,

            checked,

            type

        } = e.target;

        setMovie({

            ...movie,

            [name]:

                type === "checkbox"

                    ? checked

                    : value

        });

    };

    const updateMovie = async (e) => {

        e.preventDefault();

        try {

            setLoading(true);

            const formData = new FormData();

            formData.append(

                "movie",

                JSON.stringify(movie)

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

            alert(

                "Movie Updated Successfully"

            );

            navigate("/admin");

        }

        catch (error) {

            console.log(error);

            alert(

                "Movie Update Failed"

            );

        }

        finally {

            setLoading(false);

        }

    };
        return (

        <div className="container mt-4">

            <div className="card shadow">

                <div className="card-header bg-warning">

                    <h3>

                        Edit Movie

                    </h3>

                </div>

                <div className="card-body">

                    <form onSubmit={updateMovie}>

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

                                rows="5"

                                className="form-control"

                                name="description"

                                value={movie.description}

                                onChange={handleChange}

                            />



                        </div>

                        <div className="row">

                            <div className="col-md-4 mb-3">

                                <label>

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

                                <label>

                                    Duration

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

                                <label>

                                    Rating

                                </label>

                                <input

                                    type="number"

                                    step="0.1"

                                    className="form-control"

                                    name="rating"

                                    value={movie.rating}

                                    onChange={handleChange}

                                />

                            </div>

                        </div>

                        <hr />

                        <h5>

                            Current Poster

                        </h5>

                        {

                            movie.poster &&

                            <img

                                src={`http://127.0.0.1:8081/api/movie/poster/${movie.poster}`}

                                alt="Poster"

                                width="180"

                                className="mb-3"

                            />

                        }

                        <input

                            type="file"

                            className="form-control mb-4"

                            accept="image/*"

                            onChange={(e) =>

                                setPoster(

                                    e.target.files[0]

                                )

                            }

                        />

                        <h5>

                            Current Banner

                        </h5>

                        {

                            movie.banner &&

                            <img

                                src={`http://127.0.0.1:8081/api/movie/banner/${movie.banner}`}

                                alt="Banner"

                                width="250"

                                className="mb-3"

                            />

                        }

                        <input

                            type="file"

                            className="form-control mb-4"

                            accept="image/*"

                            onChange={(e) =>

                                setBanner(

                                    e.target.files[0]

                                )

                            }

                        />

                        <div className="mb-3">

                            <label>

                                Replace Movie Video

                            </label>

                            <input

                                type="file"

                                className="form-control"

                                accept="video/*"

                                onChange={(e) =>

                                    setVideo(

                                        e.target.files[0]

                                    )

                                }

                            />

                        </div>

                        <div className="mb-4">

                            <label>

                                Replace Trailer

                            </label>

                            <input

                                type="file"

                                className="form-control"

                                accept="video/*"

                                onChange={(e) =>

                                    setTrailer(

                                        e.target.files[0]

                                    )

                                }

                            />

                        </div>

                        <div className="row">

                            <div className="col-md-4">

                                <div className="form-check">

                                    <input

                                        type="checkbox"

                                        className="form-check-input"

                                        name="featured"

                                        checked={movie.featured}

                                        onChange={handleChange}

                                    />

                                    <label>

                                        Featured

                                    </label>

                                </div>

                            </div>

                            <div className="col-md-4">

                                <div className="form-check">

                                    <input

                                        type="checkbox"

                                        className="form-check-input"

                                        name="trending"

                                        checked={movie.trending}

                                        onChange={handleChange}

                                    />

                                    <label>

                                        Trending

                                    </label>

                                </div>

                            </div>

                            <div className="col-md-4">

                                <div className="form-check">

                                    <input

                                        type="checkbox"

                                        className="form-check-input"

                                        name="active"

                                        checked={movie.active}

                                        onChange={handleChange}

                                    />

                                    <label>

                                        Active

                                    </label>

                                </div>

                            </div>

                        </div>

                        <hr />

                        <div className="d-flex justify-content-between">

                            <button

                                className="btn btn-warning btn-lg"

                                disabled={loading}

                                type="submit"

                            >

                                {

                                    loading

                                    ?

                                    "Updating..."

                                    :

                                    "Update Movie"

                                }

                            </button>

                            <button

                                type="button"

                                className="btn btn-secondary btn-lg"

                                onClick={() =>

                                    navigate("/admin")

                                }

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

export default EditMovie;
