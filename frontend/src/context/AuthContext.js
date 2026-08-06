import React, { createContext, useState, useEffect } from 'react';
import jwtDecode from 'jwt-decode';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(localStorage.getItem('token') || null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [loading, setLoading] = useState(true);

  // On app load — check if token exists and is still valid
  useEffect(() => {
    const storedToken = localStorage.getItem('token');
    if (storedToken) {
      try {
        const decoded = jwtDecode(storedToken);
        const isExpired = decoded.exp * 1000 < Date.now();
        if (!isExpired) {
          setToken(storedToken);
          setUser({ username: decoded.sub, roles: decoded.roles || [] });
          setIsAuthenticated(true);
        } else {
          // Token expired — clear it
          localStorage.removeItem('token');
        }
      } catch (e) {
        localStorage.removeItem('token');
      }
    }
    setLoading(false);
  }, []);

  const login = (jwtToken) => {
    localStorage.setItem('token', jwtToken);
    try {
      const decoded = jwtDecode(jwtToken);
      setToken(jwtToken);
      setUser({ username: decoded.sub, roles: decoded.roles || [] });
      setIsAuthenticated(true);
    } catch (e) {
      console.error('Invalid token received');
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    setToken(null);
    setUser(null);
    setIsAuthenticated(false);
  };

  return (
    <AuthContext.Provider value={{ user, token, isAuthenticated, loading, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
