import React from "react";
import { Navigate } from "react-router-dom";

const ProtectedRoute = ({ children }) => {

    const token = localStorage.getItem("token");


// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

    if (!token) {

        return <Navigate to="/login" replace />;

    }

    return children;

};

export default ProtectedRoute;
