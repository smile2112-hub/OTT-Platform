import React, { useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";

import AuthService from "../services/AuthService";


function Login() {

    const navigate = useNavigate();

    const location = useLocation();

    const [formData, setFormData] = useState({
        username: "",
        password: ""
    });

    const [error, setError] = useState("");

    const [loading, setLoading] = useState(false);


    const handleChange = (e) => {

        setFormData({

            ...formData,

            [e.target.name]: e.target.value

        });

    };


    const handleSubmit = async (e) => {

        e.preventDefault();

        setError("");


        if (!formData.username.trim()) {

            setError("Please enter username.");

            return;

        }


        if (!formData.password) {

            setError("Please enter password.");

            return;

        }


        try {

            setLoading(true);


            await AuthService.login({

                username: formData.username,

                password: formData.password

            });


            /*
             * If user was redirected to login from
             * another protected page, send them back.
             *
             * Otherwise go to Home.
             */

            const redirectPath =
                location.state?.from?.pathname || "/";


            navigate(
                redirectPath,
                {
                    replace: true
                }
            );


        } catch (err) {

            console.error(
                "Login error:",
                err
            );


            if (
                err.response &&
                err.response.data
            ) {

                if (
                    typeof err.response.data ===
                    "string"
                ) {

                    setError(
                        err.response.data
                    );

                } else if (
                    err.response.data.message
                ) {

                    setError(
                        err.response.data.message
                    );

                } else {

                    setError(
                        "Invalid username or password."
                    );

                }

            } else {

                setError(
                    "Unable to connect to backend server."
                );

            }

        } finally {

            setLoading(false);

        }

    };


    return (

        <div style={styles.page}>

            <div style={styles.card}>

                <div style={styles.logo}>
                    🎬 FlowFlix
                </div>


                <h2 style={styles.title}>
                    Welcome Back
                </h2>


                <p style={styles.subtitle}>
                    Login to continue watching FlowFlix.
                </p>


                {error && (

                    <div style={styles.error}>

                        {error}

                    </div>

                )}


                <form onSubmit={handleSubmit}>

                    <div style={styles.formGroup}>

                        <label style={styles.label}>
                            Username
                        </label>


                        <input

                            type="text"

                            name="username"

                            value={
                                formData.username
                            }

                            onChange={
                                handleChange
                            }

                            placeholder="Enter username"

                            autoComplete="username"

                            disabled={loading}

                            style={styles.input}

                        />

                    </div>


                    <div style={styles.formGroup}>

                        <label style={styles.label}>
                            Password
                        </label>


                        <input

                            type="password"

                            name="password"

                            value={
                                formData.password
                            }

                            onChange={
                                handleChange
                            }

                            placeholder="Enter password"

                            autoComplete="current-password"

                            disabled={loading}

                            style={styles.input}

                        />

                    </div>


                    <button

                        type="submit"



                        disabled={loading}

                        style={styles.loginButton}

                    >

                        {loading
                            ? "Logging in..."
                            : "Login"
                        }

                    </button>

                </form>


                <div style={styles.registerText}>

                    Don't have an account?


                    <Link

                        to="/register"

                        style={styles.registerLink}

                    >

                        Register

                    </Link>

                </div>


                <button

                    onClick={() =>
                        navigate("/")
                    }

                    style={styles.homeButton}

                >

                    ← Back to Home

                </button>

            </div>

        </div>

    );

}


const styles = {

    page: {

        minHeight: "100vh",

        backgroundColor: "#141414",

        display: "flex",

        justifyContent: "center",

        alignItems: "center",

        padding: "30px",

        color: "white"

    },


    card: {

        width: "100%",

        maxWidth: "430px",

        backgroundColor: "#1f1f1f",

        padding: "35px",

        borderRadius: "10px",

        boxShadow:
            "0 10px 30px rgba(0,0,0,0.5)"

    },


    logo: {

        textAlign: "center",

        fontSize: "32px",

        fontWeight: "bold",

        color: "#e50914",

        marginBottom: "20px"

    },


    title: {

        textAlign: "center",

        marginBottom: "8px"

    },


    subtitle: {

        textAlign: "center",

        color: "#aaa",

        marginBottom: "25px"

    },


    formGroup: {

        display: "flex",

        flexDirection: "column",

        marginBottom: "20px"

    },


    label: {

        fontSize: "15px",

        marginBottom: "7px"

    },


    input: {

        padding: "13px",

        borderRadius: "5px",

        border:
            "1px solid #555",

        backgroundColor: "#2b2b2b",

        color: "white",

        fontSize: "15px",

        outline: "none"

    },


    loginButton: {

        width: "100%",

        padding: "13px",

        backgroundColor: "#e50914",

        color: "white",

        border: "none",

        borderRadius: "5px",

        fontSize: "16px",

        fontWeight: "bold",

        cursor: "pointer",

        marginTop: "5px"

    },


    error: {

        backgroundColor: "#5c1010",

        color: "#ffb3b3",

        padding: "12px",

        borderRadius: "5px",

        marginBottom: "18px"

    },


    registerText: {

        textAlign: "center",

        marginTop: "25px",

        color: "#aaa"

    },


    registerLink: {

        color: "#e50914",

        marginLeft: "8px",

        textDecoration: "none",

        fontWeight: "bold"

    },


    homeButton: {

        display: "block",

        margin: "20px auto 0",

        backgroundColor: "transparent",

        border: "none",

        color: "#aaa",

        cursor: "pointer"

    }

};


export default Login;
