import React from "react";
import {
    BrowserRouter,
    Routes,
    Route,
    Navigate
} from "react-router-dom";

import Login from "../pages/Login";
import Register from "../pages/Register";
import Home from "../pages/Home";
import MovieDetails from "../pages/MovieDetails";
import WatchMovie from "../pages/WatchMovie";

import UploadMovie from "../pages/UploadMovie";
import ManageMovies from "../pages/ManageMovies";
import AdminDashboard from "../pages/AdminDashboard";

import Subscription from "../pages/Subscription";
import Payment from "../pages/Payment";

import Favorite from "../pages/Favorite";
import MyList from "../pages/MyList";
import WatchHistory from "../pages/WatchHistory";
import Wishlist from "../pages/Wishlist";

import PrivateRoute from "../components/PrivateRoute";
import AdminRoute from "../components/AdminRoute";
import Layout from "../components/Layout";

const AppRoutes = () => {

    return (

        <BrowserRouter>

            <Routes>

                {/* ==========================
                    PUBLIC ROUTES
                ========================== */}

                <Route
                    path="/login"
                    element={<Login />}
                />

                <Route
                    path="/register"
                    element={<Register />}
                />

                {/* ==========================
                    USER ROUTES
                ========================== */}

                <Route
                    path="/"
                    element={
                        <PrivateRoute>
                            <Layout>
                                <Home />
                            </Layout>
                        </PrivateRoute>
                    }
                />

                <Route
                    path="/movie/:id"
                    element={
                        <PrivateRoute>
                            <Layout>
                                <MovieDetails />
                            </Layout>
                        </PrivateRoute>
                    }
                />

                <Route
                    path="/watch/:id"
                    element={
                        <PrivateRoute>
                            <Layout>
                                <WatchMovie />
                            </Layout>
                        </PrivateRoute>
                    }
                />

                <Route
                    path="/favorites"
                    element={
                        <PrivateRoute>
                            <Layout>
                                <Favorite />
                            </Layout>
                        </PrivateRoute>
                    }
                />

                <Route
                    path="/my-list"
                    element={
                        <PrivateRoute>
                            <Layout>


                                <MyList />
                            </Layout>
                        </PrivateRoute>
                    }
                />

                <Route
                    path="/watch-history"
                    element={
                        <PrivateRoute>
                            <Layout>
                                <WatchHistory />
                            </Layout>
                        </PrivateRoute>
                    }
                />

                <Route
                    path="/watchlist"
                    element={
                        <PrivateRoute>
                            <Layout>
                                <Wishlist />
                            </Layout>
                        </PrivateRoute>
                    }
                />

                <Route
                    path="/subscription"
                    element={
                        <PrivateRoute>
                            <Layout>
                                <Subscription />
                            </Layout>
                        </PrivateRoute>
                    }
                />

                <Route
                    path="/payment"
                    element={
                        <PrivateRoute>
                            <Layout>
                                <Payment />
                            </Layout>
                        </PrivateRoute>
                    }
                />

                {/* ==========================
                    ADMIN ROUTES
                ========================== */}

                <Route
                    path="/admin"
                    element={
                        <AdminRoute>
                            <Layout>
                                <AdminDashboard />
                            </Layout>
                        </AdminRoute>
                    }
                />

                <Route
                    path="/admin/upload"
                    element={
                        <AdminRoute>
                            <Layout>
                                <UploadMovie />
                            </Layout>
                        </AdminRoute>
                    }
                />

                <Route
                    path="/admin/manage"
                    element={
                        <AdminRoute>
                            <Layout>
                                <ManageMovies />
                            </Layout>
                        </AdminRoute>
                    }
                />

                {/* ==========================
                    INVALID ROUTE
                ========================== */}

                <Route
                    path="*"
                    element={
                        <Navigate
                            to="/"
                            replace
                        />
                    }
                />

            </Routes>

        </BrowserRouter>

    );

};

export default AppRoutes;
