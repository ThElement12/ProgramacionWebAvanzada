import axios from "axios";

class ReservationService {
  API_URL = "https://p60qm66ujb.execute-api.us-east-1.amazonaws.com/fase-prod/practica6"

  getReservation(){
    return axios.get(this.API_URL);
  }
  postReservation(reservation){
    return axios.post(this.API_URL, reservation);
  }

}

export default new ReservationService();