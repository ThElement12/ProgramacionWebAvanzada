import axios from 'axios';
import authHeader from './auth-header';


class UserService{
  getUsers(){
    return axios.get(process.env.REACT_APP_API_URL + 'user', {headers: authHeader()});
  }
  deleteUser(username){
    return axios.delete(process.env.REACT_APP_API_URL + `user/${username}`, {headers: authHeader()});
  }
  editUser(user){
    return axios.put(process.env.REACT_APP_API_URL + `user/${user.id}`, user, {headers: authHeader()});

  }
  getUserMockups(username){
    return axios.get(process.env.REACT_APP_API_URL + `user/${username}/mockups`, {headers:authHeader()});
  }
  getAllMockups(){
    return axios.get(process.env.REACT_APP_API_URL + 'mockup', {headers: authHeader()})

  }
  createNewMockup(mockup, username){
    return axios.post(process.env.REACT_APP_API_URL + `mockup/${username}/${mockup.allowJWT}`, mockup, {headers: authHeader()})
  }
  deleteMockup(mockupId){
    return axios.delete(process.env.REACT_APP_API_URL + `mockup/delete/${mockupId}`, {headers: authHeader()})

  }
  editMockup(mockup){
    return axios.put(process.env.REACT_APP_API_URL + 'mockup', mockup, {headers: authHeader()});

  }
}

export default new UserService();