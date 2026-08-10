import React, { useEffect, useState, useCallback } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import MovieService from "../services/MovieService";

const SearchMovie = () => {

    const [searchParams] = useSearchParams();

    const keyword = searchParams.get("keyword") || "";

    const navigate = useNavigate();

    const [movies, setMovies] = useState([]);

    const loadMovies = useCallback(async () => {

        try {

            let response;

            if (keyword.trim() === "") {

                response = await MovieService.getAllMovies();

            } else {

                response = await MovieService.searchMovies(keyword);

            }

            setMovies(response.data);

        }

        catch (error) {

            console.log(error);

        }

    }, [keyword]);

    useEffect(() => {

        loadMovies();

    }, [loadMovies]);

    return (

        <div className="container mt-4">

            <h2 className="mb-4">

                Search Results for "{keyword}"

            </h2>

            <div className="row">

                {

                    movies.length === 0 ?

                        (

                            <div className="col-12">

                                <h4>

                                    No Movies Found

                                </h4>

                            </div>

                        )

                        :

                        (


// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

                            movies.map(movie => (

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

                                                {movie.genre}

                                            </p>

                                            <p>

                                                ‚≠ê {movie.rating}

                                            </p>

                                            <button

                                                className="btn btn-primary w-100"

                                                onClick={() =>

                                                    navigate(`/movie/${movie.id}`)

                                                }

                                            >

                                                View Details

                                            </button>

                                        </div>

                                    </div>

                                </div>

                            ))

                        )

                }

            </div>

        </div>

    );

};

export default SearchMovie;
