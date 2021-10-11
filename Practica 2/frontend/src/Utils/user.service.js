import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8082/';

class UserService{
  getUsers(){
    return axios.get(API_URL + 'user', {headers: authHeader()});
  }
  deleteUser(username){
    return axios.delete(API_URL + `user/${username}`, {headers: authHeader()});
  }
  editUser(user){
    return axios.put(API_URL + `user/${user.id}`, user, {headers: authHeader()});

  }
  getUserMockups(username){
    return axios.get(API_URL + `user/${username}/mockups`, {headers:authHeader()});
  }
  getAllMockups(){
    return axios.get(API_URL + 'mockup', {headers: authHeader()})

  }
  createNewMockup(mockup, username){
    return axios.post(API_URL + `mockup/${username}/${mockup.allowJWT}`, mockup, {headers: authHeader()})
  }
  deleteMockup(mockupId){
    return axios.delete(API_URL + `mockup/delete/${mockupId}`, {headers: authHeader()})

  }
  editMockup(mockup){
    return axios.put(API_URL + 'mockup', mockup, {headers: authHeader()});

  }
}

export default new UserService();