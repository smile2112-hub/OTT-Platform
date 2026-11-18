import React, {
    useState
} from "react";

import {
    useNavigate
} from "react-router-dom";

import MovieService
    from "../services/MovieService";

import "./UploadMovie.css";


function UploadMovie() {

    const navigate =
        useNavigate();


    const [
        name,
        setName
    ] = useState("");


    const [
        director,
        setDirector
    ] = useState("");


    const [
        description,
        setDescription
    ] = useState("");


    const [
        genre,
        setGenre
    ] = useState("");


    const [
        actors,
        setActors
    ] = useState("");


    const [
        distributor,
        setDistributor
    ] = useState("");


    const [
        year,
        setYear
    ] = useState("");


    const [
        duration,
        setDuration
    ] = useState("");


    const [
        rating,
        setRating
    ] = useState("");


    const [
        featured,
        setFeatured
    ] = useState(false);


    const [
        active,
        setActive
    ] = useState(true);


    const [
        trending,
        setTrending
    ] = useState(false);


    const [
        poster,
        setPoster
    ] = useState(null);


    const [
        banner,
        setBanner
    ] = useState(null);


    const [
        video,
        setVideo
    ] = useState(null);


    const [
        trailer,
        setTrailer
    ] = useState(null);


    const [
        loading,
        setLoading
    ] = useState(false);


    const [
        message,
        setMessage
    ] = useState("");


    const [
        error,
        setError
    ] = useState("");


    const handleSubmit = async (
        event
    ) => {

        event.preventDefault();


        setMessage("");
        setError("");


        if (!poster) {

            setError(
                "Please select a poster."
            );

            return;

        }


        if (!video) {

            setError(
                "Please select a movie video."
            );

            return;

        }


        try {

            setLoading(true);


            const movieData = {

                name: name,

                director: director,

                description: description,

                genre: genre,

                actors: actors,

                distributor: distributor,

                year: Number(year),

                duration: Number(duration),

                rating:
                    rating === ""
                        ? null
                        : Number(rating),

                featured: featured,

                active: active,

                trending: trending,

                totalViews: 0

            };


            await MovieService.createMovie(

                movieData,

                poster,

                banner,

                video,

                trailer

            );


            setMessage(
                "Movie uploaded successfully!"
            );


            setTimeout(() => {

                navigate(
                    "/admin/manage-movies"
                );

            }, 1000);


        } catch (error) {

            console.error(
                "Upload error:",
                error
            );


            if (
                error.response &&
                error.response.data
            ) {

                setError(
                    typeof error.response.data ===
                    "string"
                        ? error.response.data
                        : "Movie upload failed."
                );

            } else {

                setError(
                    "Movie upload failed. Check the backend."
                );

            }


        } finally {

            setLoading(false);

        }

    };


    return (

        <div className="upload-movie-page">


            <div className="upload-movie-container">


                <h1>
                    Upload Movie
                </h1>


                <p className="upload-subtitle">

                    Add a new movie to FlowFlix

                </p>


                {message && (

                    <div className="success-message">

                        {message}

                    </div>

                )}


                {error && (

                    <div className="error-message">

                        {error}

                    </div>

                )}


                <form
                    onSubmit={handleSubmit}
                >


                    {/* BASIC INFORMATION */}

                    <h2>
                        Movie Information
                    </h2>


                    <div className="form-group">

                        <label>
                            Movie Name *
                        </label>

                        <input

                            type="text"

                            value={name}

                            onChange={
                                e =>
                                    setName(
                                        e.target.value
                                    )
                            }

                            required

                        />

                    </div>


                    <div className="form-group">

                        <label>
                            Director
                        </label>

                        <input

                            type="text"

                            value={director}

                            onChange={
                                e =>
                                    setDirector(
                                        e.target.value
                                    )
                            }

                        />

                    </div>


                    <div className="form-group">

                        <label>
                            Description
                        </label>

                        <textarea

                            value={description}

                            onChange={
                                e =>
                                    setDescription(
                                        e.target.value
                                    )
                            }



                            rows="5"

                        />

                    </div>


                    <div className="form-row">


                        <div className="form-group">

                            <label>
                                Genre
                            </label>

                            <input

                                type="text"

                                value={genre}

                                onChange={
                                    e =>
                                        setGenre(
                                            e.target.value
                                        )
                                }

                                placeholder="Action"

                            />

                        </div>


                        <div className="form-group">

                            <label>
                                Year
                            </label>

                            <input

                                type="number"

                                value={year}

                                onChange={
                                    e =>
                                        setYear(
                                            e.target.value
                                        )
                                }

                                min="1900"

                                max="2100"

                            />

                        </div>


                    </div>


                    <div className="form-row">


                        <div className="form-group">

                            <label>
                                Duration (minutes)
                            </label>

                            <input

                                type="number"

                                value={duration}

                                onChange={
                                    e =>
                                        setDuration(
                                            e.target.value
                                        )
                                }

                                min="1"

                            />

                        </div>


                        <div className="form-group">

                            <label>
                                Rating
                            </label>

                            <input

                                type="number"

                                value={rating}

                                onChange={
                                    e =>
                                        setRating(
                                            e.target.value
                                        )
                                }

                                min="0"

                                max="10"

                                step="0.1"

                            />

                        </div>


                    </div>


                    <div className="form-group">

                        <label>
                            Actors
                        </label>

                        <input

                            type="text"

                            value={actors}

                            onChange={
                                e =>
                                    setActors(
                                        e.target.value
                                    )
                            }

                            placeholder="Actor 1, Actor 2"

                        />

                    </div>


                    <div className="form-group">

                        <label>
                            Distributor
                        </label>

                        <input

                            type="text"

                            value={distributor}

                            onChange={
                                e =>
                                    setDistributor(
                                        e.target.value
                                    )
                            }

                        />

                    </div>


                    {/* FILES */}

                    <h2>
                        Movie Files
                    </h2>


                    <div className="form-group">

                        <label>
                            Poster *
                        </label>

                        <input

                            type="file"

                            accept="image/*"

                            onChange={
                                e =>
                                    setPoster(
                                        e.target.files[0]
                                    )
                            }

                            required

                        />

                    </div>


                    <div className="form-group">

                        <label>
                            Banner
                        </label>

                        <input

                            type="file"

                            accept="image/*"

                            onChange={
                                e =>
                                    setBanner(
                                        e.target.files[0]
                                    )
                            }

                        />

                    </div>


                    <div className="form-group">

                        <label>
                            Movie Video *
                        </label>

                        <input

                            type="file"

                            accept="video/*"

                            onChange={
                                e =>
                                    setVideo(
                                        e.target.files[0]
                                    )
                            }

                            required

                        />

                    </div>


                    <div className="form-group">

                        <label>
                            Trailer
                        </label>

                        <input

                            type="file"

                            accept="video/*"

                            onChange={
                                e =>
                                    setTrailer(
                                        e.target.files[0]
                                    )
                            }

                        />

                    </div>


                    {/* FLAGS */}

                    <h2>
                        Movie Settings
                    </h2>


                    <div className="checkbox-group">


                        <label>

                            <input

                                type="checkbox"

                                checked={active}

                                onChange={
                                    e =>
                                        setActive(
                                            e.target.checked
                                        )
                                }

                            />

                            Active

                        </label>


                        <label>

                            <input

                                type="checkbox"

                                checked={featured}

                                onChange={
                                    e =>
                                        setFeatured(
                                            e.target.checked
                                        )
                                }

                            />

                            Featured

                        </label>


                        <label>

                            <input

                                type="checkbox"

                                checked={trending}

                                onChange={
                                    e =>
                                        setTrending(
                                            e.target.checked
                                        )
                                }

                            />

                            Trending

                        </label>


                    </div>


                    <button

                        type="submit"

                        disabled={loading}

                        className="upload-button"

                    >

                        {
                            loading
                                ? "Uploading..."
                                : "Upload Movie"
                        }

                    </button>


                </form>

            </div>

        </div>

    );

}


export default UploadMovie;
