/**
 * Author:  Aakanksha Ashok Hedau
 * Email:   aakanksha0499@gmail.com
 * Project: FlowFlix OTT Platform
 */
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const Login = () => {

    const navigate = useNavigate();
    const { login } = useAuth();

    const [formData, setFormData] = useState({ username: "", password: "" });
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        setLoading(true);

        try {
            const response = await login(formData);

            if (response.messageInvalidUsernameOrPassword === "yes") {
                setError("Invalid Username or Password");
                setLoading(false);
                return;
            }

            if (response.messageInvalidUsernameOrPassword === "deactivatedUser") {
                setError("Your Account Is Deactivated");
                setLoading(false);
                return;
            }

            const role = localStorage.getItem("role");
            navigate(role === "ADMIN" ? "/admin" : "/");

        } catch (err) {
            console.log(err);
            setError("Login Failed");
        }

        setLoading(false);
    };

    return (
        <div style={styles.page}>
            <div style={styles.card}>

                {/* Header */}
                <div style={styles.header}>
                    <div style={styles.logo}>FlowFlix</div>
                    <h2 style={styles.title}>Welcome Back</h2>
                    <p style={styles.subtitle}>Sign in to continue streaming</p>
                </div>

                {/* Error */}
                {error && <div style={styles.alertError}>{error}</div>}

                {/* Form */}
                <form onSubmit={handleSubmit} style={styles.form}>

                    <div style={styles.field}>
                        <label style={styles.label}>Username</label>
                        <input
                            type="text"
                            name="username"
                            value={formData.username}
                            onChange={handleChange}
                            required
                            placeholder="Enter your username"
                            style={styles.input}
                            onFocus={e => e.target.style.borderColor = "#e50914"}
                            onBlur={e => e.target.style.borderColor = "#333"}
                        />
                    </div>

                    <div style={styles.field}>
                        <label style={styles.label}>Password</label>
                        <input
                            type="password"
                            name="password"
                            value={formData.password}
                            onChange={handleChange}
                            required
                            placeholder="Enter your password"
                            style={styles.input}
                            onFocus={e => e.target.style.borderColor = "#e50914"}
                            onBlur={e => e.target.style.borderColor = "#333"}
                        />
                    </div>

                    <button
                        type="submit"
                        disabled={loading}
                        style={{
                            ...styles.button,
                            opacity: loading ? 0.7 : 1,
                            cursor: loading ? "not-allowed" : "pointer"
                        }}
                    >
                        {loading ? "Signing In..." : "Login"}
                    </button>


// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

                </form>

                {/* Footer */}
                <p style={styles.footer}>
                    Don't have an account?{" "}
                    <Link to="/register" style={styles.footerLink}>Register Here</Link>
                </p>

            </div>
        </div>
    );
};

const styles = {
    page: {
        minHeight: "100vh",
        background: "#141414",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        padding: "40px 20px",
    },
    card: {
        width: "100%",
        maxWidth: "650px",
        background: "#1a1a2e",
        border: "1px solid #2a2a3e",
        borderRadius: "12px",
        padding: "40px 50px",
        boxShadow: "0 15px 45px rgba(0,0,0,0.6)",
    },
    header: {
        textAlign: "center",
        marginBottom: "28px",
    },
    logo: {
        fontSize: "32px",
        fontWeight: "800",
        color: "#e50914",
        marginBottom: "12px",
        letterSpacing: "0.05em",
    },
    title: {
        margin: "0 0 6px",
        fontSize: "28px",
        fontWeight: "700",
        color: "#fff",
    },
    subtitle: {
        margin: 0,
        color: "#aaa",
        fontSize: "20px",
    },
    alertError: {
        background: "#3d1212",
        color: "#ffb8b8",
        padding: "12px",
        borderRadius: "6px",
        marginBottom: "16px",
        fontSize: "20px",
    },
    form: {
        display: "flex",
        flexDirection: "column",
        gap: "18px",
    },
    field: {
        display: "flex",
        flexDirection: "column",
        gap: "6px",
    },
    label: {
        color: "#ccc",
        fontSize: "20px",
        fontWeight: "600",
    },
    input: {
        padding: "13px 14px",
        background: "#0f0f1a",
        border: "1px solid #333",
        borderRadius: "6px",
        color: "#fff",
        fontSize: "20px",
        outline: "none",
        transition: "border-color 0.2s",
    },
    button: {
        marginTop: "6px",
        padding: "16px",
        background: "#e50914",
        color: "#fff",
        border: "none",
        borderRadius: "6px",
        fontSize: "20px",
        fontWeight: "700",
        transition: "background 0.2s",
    },
    footer: {
        textAlign: "center",
        marginTop: "24px",
        color: "#aaa",
        fontSize: "25px",
    },
    footerLink: {
        color: "#e50914",
        fontWeight: "700",
        textDecoration: "none",
    },
};

export default Login;
