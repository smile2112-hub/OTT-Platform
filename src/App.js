import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import PrivateRoute from './util/PrivateRoute';
import Navbar from './components/navbar/Navbar';
import Home from './pages/Home';
import Movies from './pages/Movies';
import MovieDetails from './pages/MovieDetails';
import Watchlist from './pages/Watchlist';
import MyRatings from './pages/MyRatings';
import Login from './pages/Login';
import Register from './pages/Register';
import NotFound from './pages/NotFound';
import './App.css';

function App() {
  return (
    <AuthProvider>
      <Router>
        <Navbar />
        <Routes>
          {/* Public routes */}
          <Route path="/login"    element={<Login />} />
          <Route path="/register" element={<Register />} />

          {/* Protected routes */}
          <Route element={<PrivateRoute />}>
            <Route path="/"           element={<Home />} />
            <Route path="/movies"     element={<Movies />} />
            <Route path="/movies/:id" element={<MovieDetails />} />
            <Route path="/watchlist"  element={<Watchlist />} />
            <Route path="/my-ratings" element={<MyRatings />} />
          </Route>

          {/* Fallback */}
          <Route path="*" element={<NotFound />} />
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
