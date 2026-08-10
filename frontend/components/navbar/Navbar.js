import React, { useContext } from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';
import Swal from 'sweetalert2';

const Navbar = () => {
  const { isAuthenticated, user, logout } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    Swal.fire({
      title: 'Logging out?',
      icon: 'question',
      background: '#1a1a2e',
      color: '#e0e0e0',
      showCancelButton: true,
      confirmButtonColor: '#e94560',
      cancelButtonColor: '#0f3460',
      confirmButtonText: 'Yes, logout',
    }).then((result) => {
      if (result.isConfirmed) {
        logout();
        navigate('/login');
      }
    });
  };

  return (
    <nav className="navbar navbar-expand-lg flowflix-nav sticky-top">
      <div className="container-fluid">
        <NavLink className="navbar-brand" to="/">FlowFlix</NavLink>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navMenu"
          style={{ borderColor: '#0f3460' }}
        >
          <span className="navbar-toggler-icon" style={{ filter: 'invert(1)' }} />
        </button>

        <div className="collapse navbar-collapse" id="navMenu">
          {isAuthenticated && (
            <ul className="navbar-nav me-auto">
              <li className="nav-item">
                <NavLink className="nav-link" to="/">Home</NavLink>
              </li>
              <li className="nav-item">
                <NavLink className="nav-link" to="/movies">Movies</NavLink>
              </li>
              <li className="nav-item">
                <NavLink className="nav-link" to="/watchlist">Watchlist</NavLink>
              </li>
              <li className="nav-item">
                <NavLink className="nav-link" to="/my-ratings">My Ratings</NavLink>
              </li>
            </ul>
          )}

          <div className="ms-auto d-flex align-items-center gap-3">
            {isAuthenticated ? (
              <>
                <span className="user-label">{user?.username}</span>
                <button className="btn btn-sm btn-accent px-4" onClick={handleLogout}>
                  Logout
                </button>
              </>
            ) : (
              <>
                <NavLink className="btn btn-sm btn-outline-accent px-4" to="/login">Login</NavLink>
                <NavLink className="btn btn-sm btn-accent px-4" to="/register">Register</NavLink>
              </>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
