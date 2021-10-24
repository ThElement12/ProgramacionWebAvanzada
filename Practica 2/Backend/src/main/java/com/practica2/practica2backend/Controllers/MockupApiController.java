package com.practica2.practica2backend.Controllers;

import com.practica2.practica2backend.Models.HttpHeader;
import com.practica2.practica2backend.Models.Mockup;
import com.practica2.practica2backend.Services.MockupService;
import com.practica2.practica2backend.Services.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


@CrossOrigin()
@RequestMapping("/")
@RestController
public class MockupApiController {

    private final MockupService mockupService;

    public MockupApiController(MockupService mockupService, UserService userService) {
        this.mockupService = mockupService;
    }

    @RequestMapping(value = "/{resource}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = MediaType.ALL_VALUE)
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getResource(@PathVariable String resource, HttpServletResponse header,
                                         @RequestHeader(value = "token",required = false) String token)  {

        Map<String, Object> response = new HashMap<>();
        try{

            Mockup mockup = mockupService.findByUUID(resource);
            if(mockup.getMethod().equals("GET")){
                if(token == null && mockup.getAllowJWT()){
                    response.put("message","Error, permiso denegado, token no encontrado");
                    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
                }
                if (mockupService.isValidMockup(mockup,token)) {
                    if (!mockup.getHeaders().isEmpty()) {
                        for (HttpHeader aux : mockup.getHeaders()) {
                            header.addHeader(aux.getKey(), aux.getValue());
                        }
                    }
                    return ResponseEntity.status(mockup.getStatus())
                            .contentType(MediaType.valueOf(mockup.getContentType()))
                            .body(mockup.getBody());
                } else {
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
            }
            else{
                response.put("message","Recurso con método GET no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }catch (NoSuchElementException | NullPointerException e){
            response.put("message", "el mockup no fue encontrado ");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }



    }

    @PostMapping(value = "/{resource}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,MediaType.ALL_VALUE}, consumes = MediaType.ALL_VALUE)
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> postResource(@PathVariable String resource, HttpServletResponse header,
                                          @RequestHeader(value = "token",required = false) String token) {

        Map<String, Object> response = new HashMap<>();
        try{
            Mockup mockup = mockupService.findByUUID(resource);
            if(mockup.getMethod().equals("POST")){
                if(token == null && mockup.getAllowJWT()){
                    response.put("message","Error, permiso denegado, token no encontrado");
                    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
                }
                if (mockupService.isValidMockup(mockup,token)) {
                    if (!mockup.getHeaders().isEmpty()) {
                        for (HttpHeader aux : mockup.getHeaders()) {
                            header.addHeader(aux.getKey(), aux.getValue());
                        }
                    }
                    return ResponseEntity.status(mockup.getStatus())
                            .contentType(MediaType.valueOf(mockup.getContentType()))
                            .body(mockup.getBody());
                } else {
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
            }
            else{
                response.put("message","Recurso con método POST no encontrado");

                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }catch (NoSuchElementException | NullPointerException e){
            response.put("message", "el mockup no fue encontrado ");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping(value = "/{resource}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,MediaType.ALL_VALUE}, consumes = MediaType.ALL_VALUE)
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> putResource( @PathVariable String resource, HttpServletResponse header,
                                          @RequestHeader(value = "token",required = false) String token) {

        Map<String, Object> response = new HashMap<>();
        try{
            Mockup mockup = mockupService.findByUUID(resource);
            if(mockup.getMethod().equals("PUT")){
                if(token == null && mockup.getAllowJWT()){
                    response.put("message","Error, permiso denegado, token no encontrado");
                    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
                }
                if (mockupService.isValidMockup(mockup,token)) {
                    if (!mockup.getHeaders().isEmpty()) {
                        for (HttpHeader aux : mockup.getHeaders()) {
                            header.addHeader(aux.getKey(), aux.getValue());
                        }
                    }
                    return ResponseEntity.status(mockup.getStatus())
                            .contentType(MediaType.valueOf(mockup.getContentType()))
                            .body(mockup.getBody());
                } else {
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
            }
            else{
                response.put("message","Recurso con método PUT no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }catch (NoSuchElementException | NullPointerException e){
            response.put("message", "el mockup no fue encontrado ");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    @PatchMapping(value = "/{resource}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,MediaType.ALL_VALUE}, consumes = MediaType.ALL_VALUE)
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> patchResource(@PathVariable String resource,
                                           HttpServletResponse header,
                                           @RequestHeader(value = "token",required = false) String token) {
        Map<String, Object> response = new HashMap<>();
        try{
            Mockup mockup = mockupService.findByUUID(resource);
            if(mockup.getMethod().equals("PATCH")){
                if(token == null && mockup.getAllowJWT()){
                    response.put("message","Error, permiso denegado, token no encontrado");
                    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
                }
                if (mockupService.isValidMockup(mockup,token)) {
                    if (!mockup.getHeaders().isEmpty()) {
                        for (HttpHeader aux : mockup.getHeaders()) {
                            header.addHeader(aux.getKey(), aux.getValue());
                        }
                    }
                    return ResponseEntity.status(mockup.getStatus())
                            .contentType(MediaType.valueOf(mockup.getContentType()))
                            .body(mockup.getBody());
                } else {
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
            }
            else{
                response.put("message","Recurso con método PATCH no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }catch (NoSuchElementException | NullPointerException e){
            response.put("message", "el mockup no fue encontrado ");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{resource}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,MediaType.ALL_VALUE}, consumes = MediaType.ALL_VALUE)
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> deleteResource(@PathVariable String resource,
                                            HttpServletResponse header,
                                            @RequestHeader(value = "token",required = false) String token) {
        Map<String, Object> response = new HashMap<>();
        try{
            Mockup mockup = mockupService.findByUUID(resource);
            if(mockup.getMethod().equals("DELETE")){
                if( (token == null && mockup.getAllowJWT()
                        || (mockup.getToken().equals(token)) && mockup.getAllowJWT())){
                    response.put("message","Error, permiso denegado, token no válido");
                    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
                }
                if (mockupService.isValidMockup(mockup,token)) {
                    if (!mockup.getHeaders().isEmpty()) {
                        for (HttpHeader aux : mockup.getHeaders()) {
                            header.addHeader(aux.getKey(), aux.getValue());
                        }
                    }
                    return ResponseEntity.status(mockup.getStatus())
                            .contentType(MediaType.valueOf(mockup.getContentType()))
                            .body(mockup.getBody());
                } else {
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
            }
            else{
                response.put("message","Recurso con método DELETE no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }catch (NoSuchElementException | NullPointerException e){
            response.put("message", "el mockup no fue encontrado ");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{resource}", method = RequestMethod.OPTIONS)
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> optionResource() {

        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.OPTIONS)
                .build();
    }
}
