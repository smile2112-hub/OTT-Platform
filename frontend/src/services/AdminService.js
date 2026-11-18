import API from "./axiosConfig";

const AdminService = {



    dashboard() {
        return API.get("/admin/dashboard");
    }

};

export default AdminService;
