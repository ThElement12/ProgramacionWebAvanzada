import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8082/';

class UserService{
  getUsers(){
    return axios.get(API_URL + 'user', {headers: authHeader()})
  }
  deleteUser(username){

  }
  editUser(user){

  }
  getUserMockups(user){
    return axios.get(API_URL + 'mockup/' + user, {headers:authHeader()})
  }
  getAllMockups(){

  }
  createNewMockup(mockup){

  }
  deleteMockup(mockup){

  }
  editMockup(mockup){

  }
}

export default new UserService();