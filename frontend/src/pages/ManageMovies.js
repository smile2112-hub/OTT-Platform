import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import MovieService from "../../services/MovieService";

const ManageMovies = () => {

    const [movies, setMovies] = useState([]);
    const [search, setSearch] = useState("");

    useEffect(() => {
        loadMovies();
    }, []);

    const loadMovies = async () => {
        try {

            const response = await MovieService.getAllMovies();

            setMovies(response.data || []);

        } catch (error) {

            console.log(error);

        }
    };

    const deleteMovie = async (id) => {

        if (!window.confirm("Delete this movie?")) return;

        try {

            await MovieService.deleteMovie(id);

            loadMovies();

        } catch (e) {

            alert("Unable to delete movie.");

        }

    };

    const filteredMovies = movies.filter(movie =>

        (movie.name || "")
            .toLowerCase()
            .includes(search.toLowerCase())

    );

    return (

        <div className="container mt-4">

            <div className="d-flex justify-content-between mb-4">

                <h2>Manage Movies</h2>

                <Link
                    to="/admin/add-movie"
                    className="btn btn-success"
                >
                    Add Movie
                </Link>

            </div>

            <input

                className="form-control mb-4"

                placeholder="Search movie..."

                value={search}

                onChange={(e) => setSearch(e.target.value)}

            />

            <div className="table-responsive">

                <table className="table table-bordered table-hover">

                    <thead className="table-dark">

                        <tr>

                            <th>ID</th>

                            <th>Poster</th>

                            <th>Name</th>

                            <th>Genre</th>

                            <th>Year</th>

                            <th>Rating</th>



                            <th>Views</th>

                            <th>Action</th>

                        </tr>

                    </thead>

                    <tbody>

                        {

                            filteredMovies.length === 0 ?

                                <tr>

                                    <td
                                        colSpan="8"
                                        className="text-center"
                                    >

                                        No Movies Found

                                    </td>

                                </tr>

                                :

                                filteredMovies.map(movie => (

                                    <tr key={movie.id}>

                                        <td>{movie.id}</td>

                                        <td>

                                            <img

                                                src={`http://127.0.0.1:8081/api/movie/poster/${movie.poster}`}

                                                alt="poster"

                                                width="70"

                                            />

                                        </td>

                                        <td>{movie.name}</td>

                                        <td>{movie.genre}</td>

                                        <td>{movie.year}</td>

                                        <td>{movie.rating}</td>

                                        <td>{movie.totalViews}</td>

                                        <td>

                                            <Link

                                                to={`/admin/edit-movie/${movie.id}`}

                                                className="btn btn-primary btn-sm me-2"

                                            >

                                                Edit

                                            </Link>

                                            <button

                                                className="btn btn-danger btn-sm"

                                                onClick={() => deleteMovie(movie.id)}

                                            >

                                                Delete

                                            </button>

                                        </td>

                                    </tr>

                                ))

                        }

                    </tbody>

                </table>

            </div>

        </div>

    );

};

export default ManageMovies;
