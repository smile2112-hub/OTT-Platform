import axios from 'axios';

class LoginService{

    login(loginParams){
        return axios.post("http://localhost:8081/api/login", loginParams);
    }

    logout(){
        return axios.get("http://localhost:8081/api/logout");
    }

}
export default new LoginService();