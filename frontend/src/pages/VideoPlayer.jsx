import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import MovieService from "../services/MovieService";

function VideoPlayer() {

    const { id } = useParams();
    const navigate = useNavigate();

    const [movie, setMovie] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {

        loadMovie();

    }, [id]);

    const loadMovie = async () => {

        try {

            setLoading(true);
            setError("");

            const response = await MovieService.getMovie(id);

            setMovie(response.data);

        } catch (err) {

            console.error("Movie loading error:", err);

            setError(
                err.response?.data?.message ||
                "Unable to load movie."
            );

        } finally {

            setLoading(false);

        }

    };

    if (loading) {

        return (
            <div style={styles.page}>
                <h2>Loading movie...</h2>
            </div>
        );

    }

    if (error) {

        return (
            <div style={styles.page}>

                <h2>Unable to Play Movie</h2>

                <p>{error}</p>

                <button
                    style={styles.button}
                    onClick={() => navigate("/")}
                >
                    Back to Home
                </button>

            </div>
        );

    }

    if (!movie) {

        return (
            <div style={styles.page}>

                <h2>Movie Not Found</h2>

                <button
                    style={styles.button}
                    onClick={() => navigate("/")}
                >
                    Back to Home
                </button>

            </div>
        );

    }

    /*
     * Backend should return the video path as:
     *
     * movie.video
     *
     * Example:
     * /uploads/videos/movie.mp4
     */

    const videoUrl = movie.video;

    return (

        <div style={styles.page}>

            <div style={styles.header}>

                <button
                    style={styles.backButton}
                    onClick={() => navigate(-1)}
                >
                    ‚Üê Back
                </button>

                <h2>{movie.title || "FlowFlix Movie"}</h2>

            </div>

            <div style={styles.playerContainer}>


// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

                {videoUrl ? (

                    <video
                        controls
                        autoPlay
                        style={styles.video}
                        poster={movie.banner || movie.poster || ""}
                    >

                        <source
                            src={videoUrl}
                            type="video/mp4"
                        />

                        Your browser does not support HTML5 video.

                    </video>

                ) : (

                    <div style={styles.noVideo}>

                        <h3>Video Not Available</h3>

                        <p>
                            The video file for this movie is not available.
                        </p>

                    </div>

                )}

            </div>

            <div style={styles.details}>

                <h1>{movie.title}</h1>

                {movie.genre && (
                    <p>
                        <strong>Genre:</strong> {movie.genre}
                    </p>
                )}

                {movie.description && (
                    <p>
                        {movie.description}
                    </p>
                )}

                {movie.duration && (
                    <p>
                        <strong>Duration:</strong> {movie.duration}
                    </p>
                )}

            </div>

        </div>

    );

}

const styles = {

    page: {
        minHeight: "100vh",
        backgroundColor: "#141414",
        color: "white",
        padding: "20px"
    },

    header: {
        display: "flex",
        alignItems: "center",
        gap: "20px",
        marginBottom: "20px"
    },

    backButton: {
        backgroundColor: "#e50914",
        color: "white",
        border: "none",
        padding: "10px 18px",
        borderRadius: "5px",
        cursor: "pointer",
        fontSize: "15px"
    },

    playerContainer: {
        width: "100%",
        maxWidth: "1200px",
        margin: "0 auto",
        backgroundColor: "#000",
        borderRadius: "8px",
        overflow: "hidden"
    },

    video: {
        width: "100%",
        maxHeight: "75vh",
        display: "block",
        backgroundColor: "#000"
    },

    noVideo: {
        minHeight: "400px",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        color: "#ccc"
    },

    details: {
        maxWidth: "1200px",
        margin: "30px auto",
        lineHeight: "1.6"
    },

    button: {
        backgroundColor: "#e50914",
        color: "white",
        border: "none",
        padding: "12px 25px",
        borderRadius: "5px",
        cursor: "pointer"
    }

};

export default VideoPlayer;
