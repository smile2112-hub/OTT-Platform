/**
 * Author:  Aakanksha Ashok Hedau
 * Email:   aakanksha0499@gmail.com
 * Project: FlowFlix OTT Platform
 */
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../services/api";

const Register = () => {

    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        nameSurname: "",
        username: "",
        email: "",
        password: ""
    });

    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError("");
        setSuccess("");

        try {
            const response = await api.post("/user/register", formData);
            setSuccess(response.data);
        } catch (err) {
            setError(err.response ? err.response.data : "Registration Failed");
        }

        setLoading(false);
    };

    return (
        <div style={styles.page}>
            <div style={styles.card}>

                {/* Header */}
                <div style={styles.header}>
                    <div style={styles.logo}>FlowFlix</div>
                    <h2 style={styles.title}>Create Account</h2>
                    <p style={styles.subtitle}>Join FlowFlix and start streaming</p>
                </div>

                {/* Alerts */}
                {success && (
                    <div style={styles.alertSuccess}>
                        <div>{success}</div>
                        <Link to="/login" style={styles.loginBtn}>Go to Login â†’</Link>
                    </div>
                )}
                {error && <div style={styles.alertError}>{error}</div>}

                {/* Form */}
                <form onSubmit={handleSubmit} style={styles.form}>

                    <div style={styles.field}>
                        <label style={styles.label}>Full Name</label>
                        <input
                            type="text"
                            name="nameSurname"
                            value={formData.nameSurname}
                            onChange={handleChange}
                            required
                            placeholder="Enter your full name"
                            style={styles.input}
                            onFocus={e => e.target.style.borderColor = "#e50914"}
                            onBlur={e => e.target.style.borderColor = "#333"}
                        />
                    </div>

                    <div style={styles.field}>
                        <label style={styles.label}>Username</label>
                        <input
                            type="text"
                            name="username"
                            value={formData.username}
                            onChange={handleChange}
                            required
                            placeholder="Choose a username"
                            style={styles.input}
                            onFocus={e => e.target.style.borderColor = "#e50914"}
                            onBlur={e => e.target.style.borderColor = "#333"}
                        />
                    </div>

                    <div style={styles.field}>
                        <label style={styles.label}>Email</label>
                        <input
                            type="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                            placeholder="Enter your email"
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
                            placeholder="Create a password"
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

// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

                        {loading ? "Creating Account..." : "Register"}
                    </button>

                </form>

                {/* Footer */}
                <p style={styles.footer}>
                    Already have an account?{" "}
                    <Link to="/login" style={styles.footerLink}>Login Here</Link>
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
    alertSuccess: {
        background: "#123d22",
        color: "#b8ffcb",
        padding: "16px",
        borderRadius: "6px",
        marginBottom: "16px",
        fontSize: "20px",
        display: "flex",
        flexDirection: "column",
        gap: "12px",
        alignItems: "flex-start",
    },
    loginBtn: {
        display: "inline-block",
        marginTop: "4px",
        background: "#e50914",
        color: "#fff",
        padding: "10px 20px",
        borderRadius: "6px",
        fontWeight: "700",
        fontSize: "18px",
        textDecoration: "none",
    },
    alertError: {
        background: "#3d1212",
        color: "#ffb8b8",
        padding: "12px",
        borderRadius: "6px",
        marginBottom: "16px",
        fontSize: "15px",
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

export default Register;
