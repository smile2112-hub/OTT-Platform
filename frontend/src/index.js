import React from "react";
import ReactDOM from "react-dom/client";

import "bootstrap/dist/css/bootstrap.min.css";

import App from "./App";
import { AuthProvider } from "./context/AuthContext";

import "./index.css";
import "./assets/styles/index.css";


// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

const root = ReactDOM.createRoot(
    document.getElementById("root")
);

root.render(

    <React.StrictMode>

        <AuthProvider>
            <App />
        </AuthProvider>

    </React.StrictMode>

);
