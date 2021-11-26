import axios from "axios";

class ReservationService {
  API_URL = "https://96oj1beo19.execute-api.us-east-1.amazonaws.com/default/practica6-serverless"

  getReservation(){
    return axios.get(this.API_URL);
  }
  postReservation(reservation){
    return axios.post(this.API_URL, reservation);
  }

}

export default new ReservationService();