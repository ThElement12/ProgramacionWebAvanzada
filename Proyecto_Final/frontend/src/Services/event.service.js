import axios from "axios";

import authHeader from './auth-header.service';

class EventService{
  getAllEvents(){
    return axios.get(process.env.REACT_APP_API_URL + 'shop/event/', {headers: authHeader()});
  }
  getPlans(){
    return axios.get(process.env.REACT_APP_API_URL + 'shop/plan/', {headers: authHeader()});
  }
  postEvent(event){
    return axios.post(process.env.REACT_APP_API_URL + 'shop/event/', event, {headers: authHeader()});
  }
  getProducts(){
    return axios.get(process.env.REACT_APP_API_URL + 'shop/product/', {headers: authHeader()});
  }
  getEvent(username){
    return axios.get(process.env.REACT_APP_API_URL + `shop/event/${username}`,{headers: authHeader()})
  }


}
export default new EventService();
