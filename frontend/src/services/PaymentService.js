import API from "./axiosConfig";

const PaymentService = {

    createPayment(subscriptionId) {
        return API.post(`/payment/create/${subscriptionId}`);
    },

    verifyPayment(paymentId) {
        return API.put(`/payment/verify/${paymentId}`);
    },

    verifyAny(any) {
        return API.put(`/payment/verify-any/${any}`);
    },

    getMyPayments() {

// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.

        return API.get("/payment/my");
    },

    getAllPayments() {
        return API.get("/payment/admin/all");
    },

    getTotalRevenue() {
        return API.get("/payment/admin/revenue/total");
    },

    getMonthlyRevenue() {
        return API.get("/payment/admin/revenue/month");
    },

    getTodayRevenue() {
        return API.get("/payment/admin/revenue/today");
    }

};

export default PaymentService;
