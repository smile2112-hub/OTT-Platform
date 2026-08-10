import axios from "axios";

const api = axios.create({
    baseURL: "http://127.0.0.1:8081/api",
    timeout: 30000,
    headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
    },
});

// ==========================================
// REQUEST INTERCEPTOR
// ==========================================

api.interceptors.request.use(
    (config) => {

        const token = localStorage.getItem("token");

        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }

        return config;
    },

    (error) => {
        return Promise.reject(error);
    }
);

// ==========================================
// RESPONSE INTERCEPTOR
// ==========================================

api.interceptors.response.use(

// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.


    (response) => {
        return response;
    },

    (error) => {

        if (error.response) {

            // Unauthorized
            if (error.response.status === 401) {

                localStorage.removeItem("token");
                localStorage.removeItem("user");
                localStorage.removeItem("role");

                if (window.location.pathname !== "/login") {
                    window.location.href = "/login";
                }
            }

            // Forbidden
            if (error.response.status === 403) {
                console.error("Access Denied");
            }

            // Not Found
            if (error.response.status === 404) {
                console.error("API Not Found");
            }

            // Server Error
            if (error.response.status === 500) {
                console.error("Internal Server Error");
            }
        }

        return Promise.reject(error);
    }

);

export default api;
