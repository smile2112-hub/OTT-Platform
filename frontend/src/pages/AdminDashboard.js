/**
 * Author:  Aakanksha Ashok Hedau
 * Email:   aakanksha0499@gmail.com
 * Project: FlowFlix OTT Platform
 */
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import MovieService from "../services/MovieService";

const AdminDashboard = () => {

    const [movies, setMovies] = useState([]);
    const [featured, setFeatured] = useState([]);
    const [trending, setTrending] = useState([]);
    const [topViewed, setTopViewed] = useState([]);
    const [totalViews, setTotalViews] = useState(0);
    const [mostViewedMovie, setMostViewedMovie] = useState("");

    useEffect(() => {

        loadDashboard();

    }, []);

    const loadDashboard = async () => {

        try {

            const movieRes = await MovieService.getAllMovies();
            setMovies(movieRes.data);

            const featureRes =
                await MovieService.getFeaturedMovies();

            setFeatured(featureRes.data);

            const trendingRes =
                await MovieService.getTrendingMovies();

            setTrending(trendingRes.data);

            const topRes =
                await MovieService.getTopViewedMovies();

            setTopViewed(topRes.data);

            const totalViewRes =
                await MovieService.getTotalViews();

            setTotalViews(totalViewRes.data);

            const mostViewRes =
                await MovieService.getMostViewedMovie();

            setMostViewedMovie(mostViewRes.data);

        } catch (error) {

            console.log(error);

        }

    };

    return (

        <div className="container mt-4">

            <h2 className="mb-4">

                Admin Dashboard

            </h2>

            <div className="row">

                <div className="col-md-3 mb-3">

                    <div className="card bg-primary text-white">

                        <div className="card-body">

                            <h5>Total Movies</h5>

                            <h2>

                                {movies.length}

                            </h2>

                        </div>

                    </div>

                </div>

                <div className="col-md-3 mb-3">

                    <div className="card bg-success text-white">

                        <div className="card-body">

                            <h5>Featured Movies</h5>

                            <h2>

                                {featured.length}

                            </h2>

                        </div>

                    </div>

                </div>

                <div className="col-md-3 mb-3">

                    <div className="card bg-warning text-dark">

                        <div className="card-body">

                            <h5>Trending Movies</h5>

                            <h2>

                                {trending.length}

                            </h2>

                        </div>

                    </div>

                </div>

                <div className="col-md-3 mb-3">

                    <div className="card bg-danger text-white">

                        <div className="card-body">

                            <h5>Total Views</h5>

                            <h2>

                                {totalViews}

                            </h2>

                        </div>

                    </div>

                </div>

            </div>

            <div className="card mt-4">

                <div className="card-header">

                    <strong>

                        Most Viewed Movie

                    </strong>

                </div>

                <div className="card-body">

                    <h4>

                        {mostViewedMovie}


// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

                    </h4>

                </div>

            </div>

            <div className="card mt-4">

                <div className="card-header">

                    <strong>

                        Top Viewed Movies

                    </strong>

                </div>

                <div className="card-body">

                    <table className="table table-bordered">

                        <thead>

                            <tr>

                                <th>Poster</th>

                                <th>Name</th>

                                <th>Views</th>

                                <th>Rating</th>

                            </tr>

                        </thead>

                        <tbody>

                            {

                                topViewed.map(movie => (

                                    <tr key={movie.id}>

                                        <td>

                                            <img

                                                src={`http://127.0.0.1:8081/api/movie/poster/${movie.poster}`}

                                                alt=""

                                                width="70"

                                                height="90"

                                                style={{

                                                    objectFit: "cover"

                                                }}

                                            />

                                        </td>

                                        <td>

                                            {movie.name}

                                        </td>

                                        <td>

                                            {movie.totalViews}

                                        </td>

                                        <td>

                                            ‚≠ê {movie.rating}

                                        </td>

                                    </tr>

                                ))

                            }

                        </tbody>

                    </table>

                </div>

            </div>

            <div className="row mt-4">

                <div className="col-md-3">

                    <Link

                        to="/manageMovies"

                        className="btn btn-primary w-100"

                    >

                        Manage Movies

                    </Link>

                </div>

                <div className="col-md-3">

                    <Link

                        to="/manageUsers"

                        className="btn btn-success w-100"

                    >

                        Manage Users

                    </Link>

                </div>

                <div className="col-md-3">

                    <Link

                        to="/manageReviews"

                        className="btn btn-warning w-100"

                    >

                        Reviews

                    </Link>

                </div>

                <div className="col-md-3">

                    <Link

                        to="/subscriptions"

                        className="btn btn-danger w-100"

                    >

                        Subscriptions

                    </Link>

                </div>

            </div>

        </div>

    );

};

export default AdminDashboard;
