import api from './api';

// POST /api/login  → { username, password } → returns { token, ... }
const login = (credentials) => api.post('/login', credentials);

// POST /api/user/registration → { username, email, password }
const register = (userData) => api.post('/user/registration', userData);

// GET /api/logout
const logout = () => api.get('/logout');

const authService = { login, register, logout };
export default authService;
