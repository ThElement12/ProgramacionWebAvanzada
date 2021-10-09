import axios from "axios";

import authHeader from './auth-header';


const API_URL = 'http://localhost:8082/';

class AuthService {
    login(credential, password) {
        return axios
            .post(API_URL + 'auth/login', {
                credential,
                password
            })
            .then(res => res.data)
            .then(res => {
                console.log(res.token)
                if (res.token) {
                    sessionStorage.setItem("jwt", res.token)
                }
                return res.token
            })
    }
    logout() {
        sessionStorage.clear();
    }
    register(username, password, mail) {
        return axios.post(API_URL + 'user', {
            username,
            password,
            mail
        },
        {headers: authHeader()});
    }
}

export default new AuthService();