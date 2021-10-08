package com.practica2.practica2backend.Controllers;

import com.practica2.practica2backend.Models.Mockup;
import com.practica2.practica2backend.Models.User;
import com.practica2.practica2backend.Services.MockupService;
import com.practica2.practica2backend.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
public class MockupController {

    private final MockupService mockupService;
    private final UserService userService;

    public MockupController(MockupService mockupService, UserService userService) {
        this.mockupService = mockupService;
        this.userService = userService;
    }

    @PostMapping("/mockup/{username}")
    @PreAuthorize("hasAnyAuthority('admin','cliente')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> create(@PathVariable String username, @RequestBody Mockup mockup){
        Map<String,Object> response = new HashMap<>();
            try{
                User user = userService.findByUsername(username);
                String jwt = mockupService.generateJwtToken(mockup,username);
                mockup.setToken(jwt);
                mockup = mockupService.generateUUID(mockup);
                mockupService.save(mockup);
                user.getMockups().add(mockup);
                userService.save(user);


                return new ResponseEntity<>(mockup, HttpStatus.OK);
            }catch (NoSuchElementException e){
                response.put("message","no se encontró el usuario");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
    }

    @GetMapping("/mockup/{username}/{resource}")
    @PreAuthorize("hasAnyAuthority('admin','cliente')")
    public ResponseEntity<?> getResource(@PathVariable String username,@PathVariable String resource){
        Map<String,Object> response = new HashMap<>();
        User user = userService.findByUsername(username);
        if(user.hasMockup(resource)){
            Mockup mockup = mockupService.findByUUID(resource);
            if(mockupService.validateJwtToken(mockup.getToken())){
                return new ResponseEntity<>(mockup, HttpStatus.OK);
            }
            else {
                response.put("message",mockupService.validateMessageJwtToken(mockup.getToken()));
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        }
        response.put("message","este recurso no está permitido para ti");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/mockup/{resource}")
    @PreAuthorize("hasAnyAuthority('admin')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getResourceAdmin(@PathVariable String resource){
        Map<String,Object> response = new HashMap<>();
        Mockup mockup = mockupService.findByUUID(resource);
        if(mockupService.validateJwtToken(mockup.getToken())){
            return new ResponseEntity<>(mockup, HttpStatus.OK);
        }
        else {
            response.put("message",mockupService.validateMessageJwtToken(mockup.getToken()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

    @GetMapping("/mockup")
    @PreAuthorize("hasAuthority('admin')")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Mockup> findAll(){
        return mockupService.findAll();
    }




}
