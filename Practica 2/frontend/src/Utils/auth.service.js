import axios from "axios";

import authHeader from './auth-header';

class AuthService {
    login(credential, password) {
        return axios
            .post(process.env.REACT_APP_API_URL + 'auth/login', {
                credential,
                password
            })
            .then(res =>res.data)
            .then(res => {
                if (res.token) {
                    sessionStorage.setItem("jwt", res.token)
                }
                return res.token
            })
            .catch(err => console.error(err))
    }
    logout() {
        sessionStorage.clear();
    }
    register(username, password, mail, roles) {
        return axios.post(process.env.REACT_APP_API_URL + 'user', {
            username,
            password,
            mail,
            roles
        },
        {headers: authHeader()});
    }
}

export default new AuthService();