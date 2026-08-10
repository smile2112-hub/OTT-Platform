/**
 * Author:  Aakanksha Ashok Hedau
 * Email:   aakanksha0499@gmail.com
 * Project: FlowFlix OTT Platform
 */
import API from "./axiosConfig";

const SubscriptionService = {

    // ====================================
    // USER
    // ====================================

    purchaseSubscription(plan) {
        return API.post(`/subscription/purchase/${plan}`);
    },

    upgradeSubscription(plan) {
        return API.put(`/subscription/upgrade/${plan}`);
    },

    cancelSubscription() {
        return API.put("/subscription/cancel");
    },

    getMySubscription() {
        return API.get("/subscription/my");
    },

    hasActiveSubscription() {
        return API.get("/subscription/active");
    },

// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.


    hasPremiumAccess() {
        return API.get("/subscription/premium");
    },

    hasVipAccess() {
        return API.get("/subscription/vip");
    },

    // ====================================
    // ADMIN
    // ====================================

    getAllSubscriptions() {
        return API.get("/subscription/admin/all");
    },

    activateSubscription(id) {
        return API.put(`/subscription/admin/activate/${id}`);
    },

    deactivateSubscription(id) {
        return API.put(`/subscription/admin/deactivate/${id}`);
    },

    deleteSubscription(id) {
        return API.delete(`/subscription/delete/${id}`);
    }

};

export default SubscriptionService;
