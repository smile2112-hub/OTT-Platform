import api from "./api";

// © FlowFlix OTT Platform | Author: Aakanksha Ashok Hedau <aakanksha0499@gmail.com> | Unauthorized use prohibited.


// Re-export the primary axios instance so older imports of
// `axiosConfig.js` that expect `API` keep working and all
// services share the same instance and interceptors.
export default api;
