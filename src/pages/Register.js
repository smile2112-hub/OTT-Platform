import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import authService from '../services/authService';
import Swal from 'sweetalert2';

const Register = () => {
  const navigate = useNavigate();
  const [form, setForm] = useState({ username: '', email: '', password: '', confirmPassword: '' });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleChange = (e) => { setForm({ ...form, [e.target.name]: e.target.value }); setError(''); };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!form.username || !form.email || !form.password) { setError('All fields are required.'); return; }
    if (form.password !== form.confirmPassword) { setError('Passwords do not match.'); return; }
    if (form.password.length < 6) { setError('Password must be at least 6 characters.'); return; }
    setLoading(true);
    try {
      await authService.register({ username: form.username, email: form.email, password: form.password });
      Swal.fire({ icon: 'success', title: 'Account Created!', text: 'You can now sign in.', confirmButtonColor: '#e84c00', background: '#111', color: '#fff' });
      navigate('/login');
    } catch (err) {
      setError(err.response?.data?.message || 'Registration failed. Try again.');
    } finally { setLoading(false); }
  };

  return (
    <div style={{ minHeight: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '20px' }}>
      <div className="auth-card">
        <h2>FlowFlix</h2>
        <p>Create your account</p>

        {error && <div className="alert-dark-err mb-3">{error}</div>}

        <form onSubmit={handleSubmit}>
          {[
            { label: 'Username', name: 'username', type: 'text', placeholder: 'Choose a username' },
            { label: 'Email',    name: 'email',    type: 'email', placeholder: 'Your email address' },
            { label: 'Password', name: 'password', type: 'password', placeholder: 'Min 6 characters' },
            { label: 'Confirm Password', name: 'confirmPassword', type: 'password', placeholder: 'Repeat password' },
          ].map((f) => (
            <div className="mb-3 text-start" key={f.name}>
              <label className="form-label">{f.label}</label>
              <input type={f.type} name={f.name} className="form-control form-control-dark"
                placeholder={f.placeholder} value={form[f.name]} onChange={handleChange} />
            </div>
          ))}
          <button type="submit" className="btn btn-accent w-100 py-3 mt-1" style={{ fontSize: '1rem' }} disabled={loading}>
            {loading ? <span className="spinner-border spinner-border-sm me-2" /> : null}
            {loading ? 'Creating account...' : 'Create Account'}
          </button>
        </form>

        <p className="text-center mt-4" style={{ color: '#666', fontSize: '0.92rem' }}>
          Already have an account?{' '}
          <Link to="/login" style={{ background: 'linear-gradient(135deg,#e84c00,#c0392b)', WebkitBackgroundClip: 'text', WebkitTextFillColor: 'transparent', backgroundClip: 'text', fontWeight: 600 }}>
            Sign in
          </Link>
        </p>
      </div>
    </div>
  );
};

export default Register;
