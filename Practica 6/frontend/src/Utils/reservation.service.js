import axios from "axios";

class ReservationService {
  API_URL = "https://h5znosa7l8.execute-api.us-east-1.amazonaws.com/fase-prod/reservation"

  getReservation(){
    return axios.get(this.API_URL);
  }
  postReservation(reservation){
    return axios.post(this.API_URL, reservation);
  }

}

export default new ReservationService();