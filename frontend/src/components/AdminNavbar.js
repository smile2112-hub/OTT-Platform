/**
 * Author:  Aakanksha Ashok Hedau
 * Email:   aakanksha0499@gmail.com
 * Project: FlowFlix OTT Platform
 */
import React from "react";
import { Link, useNavigate } from "react-router-dom";
import AuthService from "../services/AuthService";

const AdminNavbar = () => {

    const navigate = useNavigate();

    const logout = () => {

        AuthService.logout();

        localStorage.clear();

        navigate("/login");

        window.location.reload();

    };

    return (

        <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow">

            <div className="container-fluid">

                <Link
                    className="navbar-brand fw-bold"
                    to="/admin"
                >

                    ðŸŽ¬ FlowFlix Admin

                </Link>

                <button
                    className="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#adminNavbar"
                >

                    <span className="navbar-toggler-icon"></span>

                </button>

                <div
                    className="collapse navbar-collapse"
                    id="adminNavbar"
                >

                    <ul className="navbar-nav me-auto">

                        <li className="nav-item">

                            <Link
                                className="nav-link"
                                to="/admin"
                            >

                                Dashboard

                            </Link>

                        </li>

                        <li className="nav-item">

                            <Link
                                className="nav-link"
                                to="/manageMovies"
                            >

                                Movies

                            </Link>

                        </li>

                        <li className="nav-item">

                            <Link
                                className="nav-link"
                                to="/addMovie"
                            >

                                Add Movie

                            </Link>

                        </li>

// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.


                        <li className="nav-item">

                            <Link
                                className="nav-link"
                                to="/manageUsers"
                            >

                                Users

                            </Link>

                        </li>

                        <li className="nav-item">

                            <Link
                                className="nav-link"
                                to="/manageReviews"
                            >

                                Reviews

                            </Link>

                        </li>

                        <li className="nav-item">

                            <Link
                                className="nav-link"
                                to="/subscriptions"
                            >

                                Subscriptions

                            </Link>

                        </li>

                    </ul>

                    <ul className="navbar-nav">

                        <li className="nav-item">

                            <Link
                                className="nav-link"
                                to="/profile"
                            >

                                My Profile

                            </Link>

                        </li>

                        <li className="nav-item">

                            <Link
                                className="nav-link"
                                to="/"
                            >

                                User Site

                            </Link>

                        </li>

                        <li className="nav-item">

                            <button
                                className="btn btn-danger ms-3"
                                onClick={logout}
                            >

                                Logout

                            </button>

                        </li>

                    </ul>

                </div>

            </div>

        </nav>

    );

};

export default AdminNavbar;
