/**
 * Author:  Aakanksha Ashok Hedau
 * Email:   aakanksha0499@gmail.com
 * Project: FlowFlix OTT Platform
 */
import React, { useEffect, useState, useCallback } from "react";
import { useNavigate, useParams } from "react-router-dom";
import MovieService from "../services/MovieService";

const WatchMovie = () => {

    const { id } = useParams();

    const navigate = useNavigate();

    const [movie, setMovie] = useState(null);

    const [recommendedMovies, setRecommendedMovies] = useState([]);

    const loadMovie = useCallback(async () => {

        try {

            const response = await MovieService.getMovie(id);

            setMovie(response.data);

            await MovieService.incrementView(id);

            const recommendResponse =
                await MovieService.recommendMovies(id);

            setRecommendedMovies(recommendResponse.data);

        }

        catch (error) {

            console.log(error);

        }

    }, [id]);

    useEffect(() => {

        loadMovie();

    }, [loadMovie]);

    if (!movie) {

        return (

            <div className="container mt-5 text-center">

                <h3>Loading Movie...</h3>

            </div>

        );

    }

    return (

        <div className="container mt-4">

            <div className="row">

                <div className="col-lg-8">

                    <video

                        width="100%"

                        height="500"

                        controls

                        autoPlay

                        controlsList="nodownload"

                    >

                        <source

                            src={`http://127.0.0.1:8081/api/movie/video/${movie.video}`}

                            type="video/mp4"

                        />

                        Your browser does not support video.

                    </video>

                </div>

                <div className="col-lg-4">

                    <img

                        src={`http://127.0.0.1:8081/api/movie/poster/${movie.poster}`}

                        alt={movie.name}

                        className="img-fluid rounded shadow"

                    />

                </div>

            </div>

            <div className="row mt-4">

                <div className="col-md-12">

                    <h2>

                        {movie.name}

                    </h2>

                    <hr />

                    <p>

                        <strong>Genre :</strong>

                        {" "}

                        {movie.genre}

                    </p>

                    <p>

                        <strong>Director :</strong>

                        {" "}

                        {movie.director}

                    </p>


// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

                    <p>

                        <strong>Actors :</strong>

                        {" "}

                        {movie.actors}

                    </p>

                    <p>

                        <strong>Duration :</strong>

                        {" "}

                        {movie.duration}

                        {" "}Minutes

                    </p>

                    <p>

                        <strong>Year :</strong>

                        {" "}

                        {movie.year}

                    </p>

                    <p>

                        <strong>Rating :</strong>

                        ‚≠ê {movie.rating}

                    </p>

                    <p>

                        <strong>Total Views :</strong>

                        {movie.totalViews}

                    </p>

                    <hr />

                    <h5>Description</h5>

                    <p>

                        {movie.description}

                    </p>

                </div>

            </div>

            <hr />

            <h3>

                Recommended Movies

            </h3>

            <div className="row">

                {

                    recommendedMovies.map(movie => (

                        <div

                            className="col-md-3 mt-3"

                            key={movie.id}

                        >

                            <div className="card shadow h-100">

                                <img

                                    src={`http://127.0.0.1:8081/api/movie/poster/${movie.poster}`}

                                    className="card-img-top"

                                    alt={movie.name}

                                    style={{

                                        height: "300px",

                                        objectFit: "cover"

                                    }}

                                />

                                <div className="card-body">

                                    <h6>

                                        {movie.name}

                                    </h6>

                                    <button

                                        className="btn btn-primary w-100"

                                        onClick={() =>

                                            navigate(`/watch/${movie.id}`)

                                        }

                                    >

                                        Watch

                                    </button>

                                </div>

                            </div>

                        </div>

                    ))

                }

            </div>

        </div>

    );

};

export default WatchMovie;
