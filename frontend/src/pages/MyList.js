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

import { useAuth } from "../context/AuthContext";
import FavoriteService
    from "../services/FavoriteService";

import "./MyList.css";


function MyList() {

    const { user } = useAuth();
    const username = user?.username;


    const [favorites, setFavorites] =
        useState([]);

    const [loading, setLoading] =
        useState(true);


    useEffect(() => {

        loadMyList();

    }, [username]);


    const loadMyList = async () => {

        if (!username) {

            setLoading(false);

            return;

        }


        try {

            const favoriteResponse =
                await FavoriteService
                    .getFavorites();


            setFavorites(
                favoriteResponse.data || []
            );


        } catch (error) {

            console.error(
                "My List error:",
                error
            );

        } finally {

            setLoading(false);

        }

    };


    const removeMovie = async (
        movieId
    ) => {

        try {

            await FavoriteService
                .removeFavorite(
                    movieId
                );


            setFavorites(
                favorites.filter(
                    item =>
                        Number(item.movieId) !==
                        Number(movieId)
                )
            );


        } catch (error) {

            console.error(
                "Remove favorite error:",
                error
            );

        }

    };


    if (!username) {

        return (

            <div className="my-list-page">

                <div className="my-list-message">

                    <h2>
                        Please login to view
                        your My List.
                    </h2>


                    <Link
                        to="/login"
                        className="my-list-login"
                    >

                        Login

                    </Link>

                </div>

            </div>

        );

    }


    if (loading) {

        return (

            <div className="my-list-page">

                <div className="my-list-message">

                    Loading My List...

                </div>

            </div>

        );

// � FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.


    }


    return (

        <div className="my-list-page">


            <div className="my-list-container">


                <h1>
                    Favorites
                </h1>


                {favorites.length === 0 && (

                    <div className="empty-list">

                        <h2>
                            Your favorites list is empty.
                        </h2>

                        <p>
                            Add movies to your favorites list.
                        </p>


                        <Link
                            to="/"
                            className="browse-button"
                        >

                            Browse Movies

                        </Link>

                    </div>

                )}


                <div className="my-list-grid">


                    {favorites.map(
                        favorite => {

                            // FavoriteDTO has: favoriteId, movieId, movieName, poster, genre, rating
                            const movieId = favorite.movieId;
                            const movieName = favorite.movieName;
                            const poster = favorite.poster;
                            const genre = favorite.genre;
                            const rating = favorite.rating;

                            if (!movieId) {
                                return null;
                            }

                            return (

                                <div
                                    className="my-list-card"
                                    key={favorite.favoriteId || favorite.id}
                                >


                                    <Link
                                        to={`/movie/${movieId}`}
                                    >

                                        <img

                                            src={
                                                poster
                                                    ? `http://127.0.0.1:8081/api/movie/poster/${poster}`
                                                    : "/logo192.png"
                                            }

                                            alt={movieName}

                                            className="my-list-poster"

                                        />

                                    </Link>


                                    <div className="my-list-content">


                                        <h3>{movieName}</h3>


                                        <div className="movie-info">

                                            {genre && (
                                                <span>{genre}</span>
                                            )}

                                            {rating && (
                                                <span>⭐ {rating}</span>
                                            )}

                                        </div>


                                        <div className="my-list-buttons">


                                            <Link
                                                to={`/watch/${movieId}`}
                                                className="watch-button"
                                            >

                                                ▶ Watch

                                            </Link>


                                            <button
                                                className="remove-button"
                                                onClick={() =>
                                                    removeMovie(movieId)
                                                }
                                            >

                                                ✕ Remove

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


export default MyList;
