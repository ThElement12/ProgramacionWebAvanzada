package com.practica1.practica1backend.Contollers;

import com.practica1.practica1backend.Models.Student;
import com.practica1.practica1backend.Services.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    private final Principal principal;

    public StudentController(Principal principal) {
        this.principal = principal;
    }

    @PostMapping("/student")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Student student){

        Map<String,Object> response = new HashMap<>();
        if(principal.existByEnrollment(student.getEnrollment())){
            response.put("message","El estudiante ya existe");
            response.put("error",true);
            response.put("student",student);
        }
        else{
            principal.save(student);
            response.put("message","Estudiante borrado con éxito");
            response.put("error",false);
            response.put("student",student);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/student")
    @ResponseStatus(HttpStatus.OK)
    public List<Student> findAll(){
        /*Student student = new Student();
        student.setEnrollment(20171086);
        student.setName("Robert");
        student.setLastName("Garcia");
        student.setPhone("8296956471");
        Principal principal = new Principal();
        principal.save(student);*/
        return principal.findAll();
    }

    @GetMapping("/student/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findByEnrrollment(@PathVariable int id){
        Map<String,Object> response = new HashMap<>();
        if(principal.existByEnrollment(id)){
            response.put("student",principal.findByEnrollment(id));
        }
        else{
            response.put("message","Estudiante no encontrado");
        }
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @PutMapping("/student")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> update(@RequestBody Student student){

        Map<String,Object> response = new HashMap<>();
        if(!principal.existByEnrollment(student.getEnrollment())){
            response.put("message","El estudiante no existe");
            response.put("error",true);
            response.put("student",student);
        }
        else{
            principal.update(student);
            response.put("message","Estudiante creado con éxito");
            response.put("error",false);
            response.put("student",student);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/student/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> delete(@PathVariable int id){
        Map<String,Object> response = new HashMap<>();
        if(!principal.existByEnrollment(id)){
            response.put("message","El estudiante no existe");
            response.put("error",true);
        }
        else{
            principal.deleteByEnrrollment(id);
            response.put("message","Estudiante creado con éxito");
            response.put("error",false);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
