import API from "./axiosConfig";

const UserService = {

    // ============================
    // AUTH
    // ============================

    login(data) {
        return API.post("/user/login", data);
    },

    register(data) {
        return API.post("/user/register", data);
    },

    profile() {
        return API.get("/user/profile");
    },

    logout() {
        return API.post("/user/logout");
    },

    // ============================


    // ADMIN USERS
    // ============================

    getAllUsers() {
        return API.get("/user/getAllUsers");
    },

    deleteUser(id) {
        return API.delete(`/user/deleteUser/${id}`);
    },

    activateUser(id) {
        return API.put(`/user/activate/${id}`);
    },

    deactivateUser(id) {
        return API.put(`/user/deactivate/${id}`);
    },

    makeAdmin(id) {
        return API.put(`/user/makeAdmin/${id}`);
    },

    removeAdmin(id) {
        return API.put(`/user/removeAdmin/${id}`);
    }

};

export default UserService;
