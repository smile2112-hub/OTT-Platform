import API from "./axiosConfig";

const AdminService = {

// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.


    dashboard() {
        return API.get("/admin/dashboard");
    }

};

export default AdminService;
