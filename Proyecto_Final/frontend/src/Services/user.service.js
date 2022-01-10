import axios from "axios";

import authHeader from './auth-header.service';

import User from "../Models/User";

class UserService {
  login(credential, password) {
    return axios
      .post(process.env.REACT_APP_API_URL + 'user/auth/login', {
        credential,
        password
      }, { withCredentials: true })
      .then(res => res.data)
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
  register(user) {
    console.log(user)
    return axios.post(process.env.REACT_APP_API_URL + 'user/auth/user',user);
  }
}

export default new UserService();
