package com.practica2.practica2backend.Controllers;

import com.practica2.practica2backend.Models.HttpHeader;
import com.practica2.practica2backend.Models.Mockup;
import com.practica2.practica2backend.Models.User;
import com.practica2.practica2backend.Services.MockupService;
import com.practica2.practica2backend.Services.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@CrossOrigin(origins = {"https://www.noisewatcher.systems", "http://localhost:3000", "*"})
@RestController
public class MockupController {

    private final MockupService mockupService;
    private final UserService userService;

    public MockupController(MockupService mockupService, UserService userService) {
        this.mockupService = mockupService;
        this.userService = userService;
    }

    @PostMapping("/mockup/{username}/{allowToken}")
    @PreAuthorize("hasAnyAuthority('admin','cliente')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> create(@PathVariable String username, @PathVariable Boolean allowToken, @RequestBody Mockup mockup) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = userService.findByUsername(username);
            mockup.calculateExpiryTime();
            if (allowToken) {
                String jwt = mockupService.generateJwtToken(mockup, username);
                mockup.setToken(jwt);
            }

            mockup = mockupService.generateUUID(mockup);
            mockupService.save(mockup);
            user.getMockups().add(mockup);
            userService.save(user);
            return new ResponseEntity<>(mockup, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            response.put("message", "no se encontró el usuario");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/mockup/{resource}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,MediaType.ALL_VALUE}, consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasAnyAuthority('admin','cliente')")
    public ResponseEntity<?> getResource(@PathVariable String resource, HttpServletResponse header) {

        Map<String, Object> response = new HashMap<>();
        try{
            Mockup mockup = mockupService.findByUUID(resource);
            if(mockup.getMethod().equals("GET")){
                if (mockupService.isValidMockup(mockup)) {
                    if (!mockup.getHeaders().isEmpty()) {
                        for (HttpHeader aux : mockup.getHeaders()) {
                            header.addHeader(aux.getKey(), aux.getValue());
                        }
                    }
                    return new ResponseEntity<>(mockup.getBody(), HttpStatus.valueOf(mockup.getStatus()));
                } else {
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
            }
            else{
                response.put("message","Recurso con método GET no encontrado");
                return new ResponseEntity<>(resource, HttpStatus.NOT_FOUND);
            }
        }catch (NoSuchElementException e){
            response.put("message", "el mockup no fue encontrado ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }



    }

    @PostMapping(value = "/mockup/{resource}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,MediaType.ALL_VALUE}, consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasAnyAuthority('admin','cliente')")
    public ResponseEntity<?> postResource(@PathVariable String resource, HttpServletResponse header) {

        Map<String, Object> response = new HashMap<>();
        try{
            Mockup mockup = mockupService.findByUUID(resource);
            if(mockup.getMethod().equals("GET")){
                if (mockupService.isValidMockup(mockup)) {
                    if (!mockup.getHeaders().isEmpty()) {
                        for (HttpHeader aux : mockup.getHeaders()) {
                            header.addHeader(aux.getKey(), aux.getValue());
                        }
                    }
                    return new ResponseEntity<>(mockup.getBody(), HttpStatus.valueOf(mockup.getStatus()));
                } else {
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
            }
            else{
                response.put("message","Recurso con método POST no encontrado");
                return new ResponseEntity<>(resource, HttpStatus.NOT_FOUND);
            }
        }catch (NoSuchElementException e){
            response.put("message", "el mockup no fue encontrado ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping(value = "/mockup/{resource}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,MediaType.ALL_VALUE}, consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasAnyAuthority('admin','cliente')")
    public ResponseEntity<?> putResource( @PathVariable String resource, HttpServletResponse header) {

        Map<String, Object> response = new HashMap<>();
        try{
            Mockup mockup = mockupService.findByUUID(resource);
            if(mockup.getMethod().equals("GET")){
                if (mockupService.isValidMockup(mockup)) {
                    if (!mockup.getHeaders().isEmpty()) {
                        for (HttpHeader aux : mockup.getHeaders()) {
                            header.addHeader(aux.getKey(), aux.getValue());
                        }
                    }
                    return new ResponseEntity<>(mockup.getBody(), HttpStatus.valueOf(mockup.getStatus()));
                } else {
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
            }
            else{
                response.put("message","Recurso con método PUT no encontrado");
                return new ResponseEntity<>(resource, HttpStatus.NOT_FOUND);
            }
        }catch (NoSuchElementException e){
            response.put("message", "el mockup no fue encontrado ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    @PatchMapping(value = "/mockup/{username}/{resource}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,MediaType.ALL_VALUE}, consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasAnyAuthority('admin','cliente')")
    public ResponseEntity<?> patchResource(@PathVariable String username, @PathVariable String resource, HttpServletResponse header) {
        Map<String, Object> response = new HashMap<>();
        try{
            Mockup mockup = mockupService.findByUUID(resource);
            if(mockup.getMethod().equals("GET")){
                if (mockupService.isValidMockup(mockup)) {
                    if (!mockup.getHeaders().isEmpty()) {
                        for (HttpHeader aux : mockup.getHeaders()) {
                            header.addHeader(aux.getKey(), aux.getValue());
                        }
                    }
                    return new ResponseEntity<>(mockup.getBody(), HttpStatus.valueOf(mockup.getStatus()));
                } else {
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
            }
            else{
                response.put("message","Recurso con método PATCH no encontrado");
                return new ResponseEntity<>(resource, HttpStatus.NOT_FOUND);
            }
        }catch (NoSuchElementException e){
            response.put("message", "el mockup no fue encontrado ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/mockup/{username}/{resource}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,MediaType.ALL_VALUE}, consumes = MediaType.ALL_VALUE)
    @PreAuthorize("hasAnyAuthority('admin','cliente')")
    public ResponseEntity<?> deleteResource(@PathVariable String username, @PathVariable String resource, HttpServletResponse header) {
        Map<String, Object> response = new HashMap<>();
        try{
            Mockup mockup = mockupService.findByUUID(resource);
            if(mockup.getMethod().equals("GET")){
                if (mockupService.isValidMockup(mockup)) {
                    if (!mockup.getHeaders().isEmpty()) {
                        for (HttpHeader aux : mockup.getHeaders()) {
                            header.addHeader(aux.getKey(), aux.getValue());
                        }
                    }
                    return new ResponseEntity<>(mockup.getBody(), HttpStatus.valueOf(mockup.getStatus()));
                } else {
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
            }
            else{
                response.put("message","Recurso con método DELETE no encontrado");
                return new ResponseEntity<>(resource, HttpStatus.NOT_FOUND);
            }
        }catch (NoSuchElementException e){
            response.put("message", "el mockup no fue encontrado ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/mockup/{username}/{resource}", method = RequestMethod.OPTIONS)
    @PreAuthorize("hasAnyAuthority('admin','cliente')")
    public ResponseEntity<?> optionResource() {

        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.OPTIONS)
                .build();
    }


    @GetMapping("/mockup")
    @PreAuthorize("hasAuthority('admin')")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Mockup> findAll() {
        return mockupService.findAll();
    }


    @DeleteMapping("/mockup/{uuid}")
    @PreAuthorize("hasAnyAuthority('admin','cliente')")
    public ResponseEntity<?> delete(@PathVariable String uuid) {

        Map<String, Object> response = new HashMap<>();
        try {
            Mockup mockup = mockupService.findByUUID(uuid);
            mockupService.delete(mockup);
            response.put("message", "mockup eliminado con éxito");
        } catch (NoSuchElementException e) {
            response.put("message", "el mockup no fue encontrado ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/mockup")
    @PreAuthorize("hasAnyAuthority('admin','cliente')")
    public ResponseEntity<?> update(@RequestBody Mockup mockup) {

        Map<String, Object> response = new HashMap<>();
        try {
            Mockup old = mockupService.findByUUID(mockup.getUuid());
            mockupService.save(old);
        } catch (NoSuchElementException e) {
            response.put("message", "el mockup no fue encontrado ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mockup, HttpStatus.OK);
    }

}
