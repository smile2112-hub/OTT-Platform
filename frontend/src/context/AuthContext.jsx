import React, { createContext, useContext, useEffect, useState } from "react";
import AuthService from "../services/AuthService";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {

    const [isAuthenticated, setIsAuthenticated] = useState(false);

    const [loading, setLoading] = useState(true);

    const [user, setUser] = useState(null);

    const [role, setRole] = useState(null);

    // ==========================================
    // INITIALIZE AUTH
    // ==========================================

    useEffect(() => {

        const token = AuthService.getToken();

        if (token) {

            setIsAuthenticated(true);

            setUser(AuthService.getUser());

            setRole(AuthService.getRole());

        }

        setLoading(false);

    }, []);

    // ==========================================
    // LOGIN
    // ==========================================

    const login = async (credentials) => {

        const response = await AuthService.login(credentials);

        if (response.token) {

            setIsAuthenticated(true);

            setUser(AuthService.getUser());

            setRole(AuthService.getRole());

        }



        return response;

    };

    // ==========================================
    // LOGOUT
    // ==========================================

    const logout = async () => {

        await AuthService.logout();

        setIsAuthenticated(false);

        setUser(null);

        setRole(null);

    };

    return (

        <AuthContext.Provider

            value={{

                isAuthenticated,

                loading,

                user,

                role,

                login,

                logout

            }}

        >

            {children}

        </AuthContext.Provider>

    );

};

// ==========================================
// CUSTOM HOOK
// ==========================================

export const useAuth = () => {

    return useContext(AuthContext);

};

export default AuthContext;
