import api from "./api";



// Re-export the primary axios instance so older imports of
// `axiosConfig.js` that expect `API` keep working and all
// services share the same instance and interceptors.
export default api;
