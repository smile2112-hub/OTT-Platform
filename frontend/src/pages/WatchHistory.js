/**
 * Author:  Aakanksha Ashok Hedau
 * Email:   aakanksha0499@gmail.com
 * Project: FlowFlix OTT Platform
 */
import React, {
    useEffect,
    useState
} from "react";

import {
    Link
} from "react-router-dom";

import WatchHistoryService
    from "../services/WatchHistoryService";

import MovieService
    from "../services/MovieService";

import "./WatchHistory.css";


function WatchHistory() {

    const username =
        localStorage.getItem("username");


    const [history, setHistory] =
        useState([]);

    const [movies, setMovies] =
        useState([]);

    const [loading, setLoading] =
        useState(true);


    useEffect(() => {

        loadHistory();

    }, []);


    const loadHistory = async () => {

        if (!username) {

            setLoading(false);

            return;

        }


        try {

            const historyResponse =

                await WatchHistoryService
                    .getUserHistory(
                        username
                    );


            const historyData =
                historyResponse.data || [];


            setHistory(historyData);


            const movieResponse =
                await MovieService
                    .getAllMovies();


            setMovies(
                movieResponse.data || []
            );


        } catch (error) {

            console.error(
                "Watch history error:",
                error
            );

        } finally {

            setLoading(false);

        }

    };


    const getMovie = (movieId) => {

        return movies.find(
            movie =>
                Number(movie.id) ===
                Number(movieId)
        );

    };


    const deleteItem = async (
        movieId
    ) => {

        try {

            await WatchHistoryService
                .deleteHistory(
                    username,
                    movieId
                );


            setHistory(
                history.filter(
                    item =>
                        Number(item.movieId) !==
                        Number(movieId)
                )
            );


        } catch (error) {

            console.error(
                "Delete history error:",
                error
            );

        }

    };


    if (!username) {

        return (

            <div className="history-page">

                <h2>
                    Please login to see
                    your watch history.
                </h2>


                <Link
                    to="/login"
                    className="history-login"
                >

                    Login

                </Link>

            </div>

        );

    }


    if (loading) {

        return (

            <div className="history-page">

                <h2>
                    Loading watch history...
                </h2>

            </div>

        );

    }


    return (

        <div className="history-page">


// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.


            <div className="history-container">


                <h1>

                    Continue Watching

                </h1>


                {history.length === 0 && (

                    <div className="empty-history">

                        <h2>
                            No watch history yet.
                        </h2>

                        <p>

                            Start watching movies
                            to see them here.

                        </p>


                        <Link
                            to="/"
                            className="browse-button"
                        >

                            Browse Movies

                        </Link>

                    </div>

                )}


                <div className="history-grid">


                    {history.map(
                        item => {

                            const movie =
                                getMovie(
                                    item.movieId
                                );


                            if (!movie) {

                                return null;

                            }


                            const progress =

                                Math.min(
                                    100,
                                    Math.max(
                                        0,
                                        item.progress ||
                                        0
                                    )
                                );


                            return (

                                <div
                                    className="history-card"
                                    key={item.id}
                                >


                                    <Link
                                        to={`/watch/${movie.id}`}
                                        className="history-image-link"
                                    >

                                        <img

                                            src={
                                                movie.poster
                                                    ? "http://127.0.0.1:8081/uploads/posters/"
                                                        + movie.poster
                                                    : "/logo192.png"
                                            }

                                            alt={
                                                movie.name
                                            }

                                            className="history-image"

                                        />


                                        <div className="progress-container">

                                            <div
                                                className="progress-bar"
                                                style={{
                                                    width:
                                                        progress
                                                        + "%"
                                                }}
                                            />

                                        </div>


                                    </Link>


                                    <div className="history-content">


                                        <h3>

                                            {movie.name}

                                        </h3>


                                        <p>

                                            {Math.round(
                                                progress
                                            )}

                                            % watched

                                        </p>


                                        <div className="history-actions">


                                            <Link
                                                to={`/watch/${movie.id}`}
                                                className="continue-button"
                                            >

                                                Continue

                                            </Link>


                                            <button
                                                onClick={() =>
                                                    deleteItem(
                                                        movie.id
                                                    )
                                                }
                                                className="remove-button"
                                            >

                                                Remove

                                            </button>


                                        </div>


                                    </div>


                                </div>

                            );

                        }

                    )}


                </div>

            </div>

        </div>

    );

}


export default WatchHistory;
