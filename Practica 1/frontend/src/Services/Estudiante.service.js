import axios from "axios";

export class EstudianteService {
    // TODO: mi loco usa esta clase como servicio pa todas las entidades pa no complicarnos, gracias tqmnohomo
    url = 'http://localhost:8082/student/';
    //TODO: Return para todos
    registrarEstudiante(estudiante){
        return axios.post(this.url, estudiante);
    }
    listarEstudiantes(){
        return axios.get(this.url);
    }
    modificarEstudiante(estudiante){
        return axios.put(this.url, estudiante);
    }
    borrarEstudiante(matricula){
        return axios.delete(this.url + matricula);
    }
}
