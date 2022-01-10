import axios from "axios";

import authHeader from './auth-header.service';
class UserService {
  login(credential, password) {
    return axios
      .post(process.env.REACT_APP_API_URL + 'user/auth/login', {
        credential,
        password
      })
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
    return axios.post(process.env.REACT_APP_API_URL + 'user/auth/user',user);
  }
  getUsers(){
    return axios.get(process.env.REACT_APP_API_URL + 'user/all', {headers: authHeader()})
  }
}

export default new UserService();
