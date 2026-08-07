import React from 'react';
import { useNavigate } from 'react-router-dom';

const NotFound = () => {
  const navigate = useNavigate();
  return (
    <div
      className="d-flex flex-column align-items-center justify-content-center text-center"
      style={{ minHeight: '80vh' }}
    >
      <h1 style={{ fontSize: '6rem', fontWeight: 800, lineHeight: 1,
        background: 'linear-gradient(135deg,#e84c00,#c0392b)',
        WebkitBackgroundClip: 'text', WebkitTextFillColor: 'transparent', backgroundClip: 'text'
      }}>404</h1>
      <h3 style={{ color: '#fff', marginTop: 8 }}>Page Not Found</h3>
      <p style={{ color: '#888', margin: '12px 0 28px' }}>
        The page you're looking for doesn't exist.
      </p>
      <button className="btn btn-accent px-5 py-2" onClick={() => navigate('/')}>
        Back to Home
      </button>
    </div>
  );
};

export default NotFound;
