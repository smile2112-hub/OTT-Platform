import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import NavigationBar from "./components/Navbar";
import Footer from "./components/Footer";

import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import SearchMovie from "./pages/SearchMovie";
import MovieDetails from "./pages/MovieDetails";
import WatchMovie from "./pages/WatchMovie";
import Profile from "./pages/Profile";
import MyList from "./pages/MyList";
import Wishlist from "./pages/Wishlist";
import Subscription from "./pages/Subscription";
import Payment from "./pages/Payment";
import MyReviews from "./pages/MyReviews";

import ProtectedRoute from "./components/ProtectedRoute";
import AdminRoute from "./components/AdminRoute";

import NotFound from "./pages/NotFound";

import AdminDashboard from "./pages/AdminDashboard";
import AddMovie from "./pages/AddMovie";

import ManageMovies from "./pages/Admin/ManageMovies";
import EditMovie from "./pages/Admin/EditMovie";
import ManageUsers from "./pages/Admin/ManageUsers";
import ManageReviews from "./pages/Admin/ManageReviews";
import Subscriptions from "./pages/Admin/Subscriptions";

function App() {
    return (
        <BrowserRouter>

            <NavigationBar />

            <Routes>

                {/* Public Routes */}

                <Route path="/" element={<Home />} />
                <Route path="/home" element={<Home />} />

                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />

                <Route path="/movies" element={<SearchMovie />} />
                <Route path="/search" element={<SearchMovie />} />
                <Route path="/movie/:id" element={<MovieDetails />} />

                <Route
                    path="/watch/:id"
                    element={
                        <ProtectedRoute>
                            <WatchMovie />
                        </ProtectedRoute>
                    }
                />

                {/* Protected User Routes */}

                <Route
                    path="/profile"
                    element={
                        <ProtectedRoute>
                            <Profile />
                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/favorite"
                    element={
                        <ProtectedRoute>
                            <MyList />
                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/favorites"
                    element={
                        <ProtectedRoute>
                            <MyList />
                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/watchlist"
                    element={
                        <ProtectedRoute>
                            <Wishlist />
                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/my-watchlist"
                    element={
                        <ProtectedRoute>
                            <Wishlist />

// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/subscription"
                    element={
                        <ProtectedRoute>
                            <Subscription />
                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/payment"
                    element={
                        <ProtectedRoute>
                            <Payment />
                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/my-reviews"
                    element={
                        <ProtectedRoute>
                            <MyReviews />
                        </ProtectedRoute>
                    }
                />

                {/* Admin Routes */}

                <Route
                    path="/admin"
                    element={
                        <AdminRoute>
                            <AdminDashboard />
                        </AdminRoute>
                    }
                />

                <Route
                    path="/admin/movies"
                    element={
                        <AdminRoute>
                            <ManageMovies />
                        </AdminRoute>
                    }
                />

                <Route
                    path="/admin/add-movie"
                    element={
                        <AdminRoute>
                            <AddMovie />
                        </AdminRoute>
                    }
                />

                <Route
                    path="/admin/edit-movie/:id"
                    element={
                        <AdminRoute>
                            <EditMovie />
                        </AdminRoute>
                    }
                />

                <Route
                    path="/admin/users"
                    element={
                        <AdminRoute>
                            <ManageUsers />
                        </AdminRoute>
                    }
                />

                <Route
                    path="/admin/reviews"
                    element={
                        <AdminRoute>
                            <ManageReviews />
                        </AdminRoute>
                    }
                />

                <Route
                    path="/admin/subscriptions"
                    element={
                        <AdminRoute>
                            <Subscriptions />
                        </AdminRoute>
                    }
                />

                <Route
                    path="*"
                    element={<NotFound />}
                />

            </Routes>

            <Footer />

        </BrowserRouter>
    );
}

export default App;
