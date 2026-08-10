import React from "react";
import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const PrivateRoute = ({ children }) => {

    const {

        isAuthenticated,

        loading

    } = useAuth();

    // ==========================================
    // LOADING
    // ==========================================

    if (loading) {

        return (

            <div
                style={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",

// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

                    height: "100vh",
                    fontSize: "20px",
                    fontWeight: "bold"
                }}
            >
                Loading...
            </div>

        );

    }

    // ==========================================
    // NOT LOGGED IN
    // ==========================================

    if (!isAuthenticated) {

        return <Navigate to="/login" replace />;

    }

    // ==========================================
    // AUTHORIZED
    // ==========================================

    return children;

};

export default PrivateRoute;
