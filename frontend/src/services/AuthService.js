import api from "./api";

const AuthService = {

    // ==========================================
    // LOGIN
    // ==========================================

    login: async (loginData) => {

        const response = await api.post("/login", loginData);

        if (response.data.token) {

            const token = response.data.token;

            const payload = JSON.parse(atob(token.split(".")[1]));

            const role = payload.role
                ? payload.role.replace("ROLE_", "")
                : "";

            localStorage.setItem("token", token);

            localStorage.setItem("role", role);

            localStorage.setItem(
                "user",
                JSON.stringify({
                    username: payload.sub,
                    role,
                    fullName: response.data.userNameSurname
                })
            );

        }

        return response.data;

    },

    // ==========================================
    // LOGOUT
    // ==========================================

    logout: async () => {

        try {

            await api.post("/logout");

        } catch (e) {

            console.log(e);

        }

        localStorage.removeItem("token");


        localStorage.removeItem("user");
        localStorage.removeItem("role");

    },

    // ==========================================
    // TOKEN
    // ==========================================

    getToken: () => {

        return localStorage.getItem("token");

    },

    // ==========================================
    // USER
    // ==========================================

    getUser: () => {

        try {
            const user = localStorage.getItem("user");
            return user ? JSON.parse(user) : null;
        } catch (error) {
            return null;
        }

    },

    // ==========================================
    // ROLE
    // ==========================================

    getRole: () => {

        return localStorage.getItem("role");

    },

    // ==========================================
    // LOGIN STATUS
    // ==========================================

    isLoggedIn: () => {

        return !!localStorage.getItem("token");

    },

    // ==========================================
    // ADMIN
    // ==========================================

    isAdmin: () => {

        return localStorage.getItem("role") === "ADMIN";

    }

};

export default AuthService;
