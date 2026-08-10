import React, { useState, useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import authService from '../services/authService';
import Swal from 'sweetalert2';

const Login = () => {
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState({ username: '', password: '' });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setError('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!form.username || !form.password) { setError('Please fill in all fields.'); return; }
    setLoading(true);
    try {
      // MOCK LOGIN — remove once backend is connected
      if (form.username === 'admin' && form.password === 'admin123') {
        const fakeToken = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.' +
          btoa(JSON.stringify({ sub: form.username, roles: ['ADMIN'], exp: Math.floor(Date.now()/1000) + 86400 })) +
          '.fake-signature';
        login(fakeToken);
        Swal.fire({ icon: 'success', title: 'Welcome back!', timer: 1200, showConfirmButton: false, background: '#111', color: '#fff' });
        navigate('/');
        return;
      }
      // END MOCK
      const response = await authService.login(form);
      const token = response.data.token;
      if (!token) { setError(response.data.messageInvalidUsernameOrPassword || 'Invalid credentials.'); return; }
      login(token);
      Swal.fire({ icon: 'success', title: 'Welcome back!', timer: 1200, showConfirmButton: false, background: '#111', color: '#fff' });
      navigate('/');
    } catch (err) {
      setError(err.response?.data?.message || 'Invalid username or password.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{
      minHeight: '100vh', display: 'flex', flexDirection: 'column',
      alignItems: 'center', justifyContent: 'center', textAlign: 'center', padding: '20px',
    }}>
      {!showForm ? (
        /* ── Landing splash ── */
        <div style={{ maxWidth: 600 }}>
          <h1 style={{ fontSize: '4.2rem', fontWeight: 800, color: '#fff', marginBottom: 24, lineHeight: 1.1 }}>
            Welcome back!
          </h1>
          <p style={{ fontSize: '1.38rem', color: '#ccc', marginBottom: 48, lineHeight: 1.7 }}>
            To get started, please{' '}
            <span style={{ background: 'linear-gradient(135deg,#e84c00,#c0392b)', WebkitBackgroundClip: 'text', WebkitTextFillColor: 'transparent', backgroundClip: 'text', fontWeight: 600 }}>
              click the button
            </span>{' '}
            below to log in to your account!
          </p>
          <button
            className="btn btn-accent"
            style={{ fontSize: '1.1rem', padding: '16px 72px', borderRadius: 50 }}
            onClick={() => setShowForm(true)}
          >
            Log In
          </button>
          <p style={{ marginTop: 28, color: '#666', fontSize: '0.95rem' }}>
            New here?{' '}
            <Link to="/register" style={{ background: 'linear-gradient(135deg,#e84c00,#c0392b)', WebkitBackgroundClip: 'text', WebkitTextFillColor: 'transparent', backgroundClip: 'text', fontWeight: 600 }}>
              Create an account
            </Link>
          </p>
        </div>
      ) : (
        /* ── Login form ── */
        <div className="auth-card">
          <h2>FlowFlix</h2>
          <p>Sign in to your account</p>

          {error && <div className="alert-dark-err mb-3">{error}</div>}

          <form onSubmit={handleSubmit}>
            <div className="mb-3 text-start">
              <label className="form-label">Username</label>
              <input type="text" name="username" className="form-control form-control-dark"
                placeholder="Enter your username" value={form.username} onChange={handleChange} />
            </div>
            <div className="mb-4 text-start">
              <label className="form-label">Password</label>
              <input type="password" name="password" className="form-control form-control-dark"
                placeholder="Enter your password" value={form.password} onChange={handleChange} />
            </div>
            <button type="submit" className="btn btn-accent w-100 py-3" style={{ fontSize: '1rem' }} disabled={loading}>
              {loading ? <span className="spinner-border spinner-border-sm me-2" /> : null}
              {loading ? 'Signing in...' : 'Sign In'}
            </button>
          </form>

          <div className="d-flex justify-content-between mt-4" style={{ fontSize: '0.9rem' }}>
            <button onClick={() => setShowForm(false)} style={{ background: 'none', border: 'none', color: '#666', cursor: 'pointer' }}>
              Back
            </button>
            <Link to="/register" style={{ background: 'linear-gradient(135deg,#e84c00,#c0392b)', WebkitBackgroundClip: 'text', WebkitTextFillColor: 'transparent', backgroundClip: 'text', fontWeight: 600 }}>
              Register instead
            </Link>
          </div>
        </div>
      )}
    </div>
  );
};

export default Login;
