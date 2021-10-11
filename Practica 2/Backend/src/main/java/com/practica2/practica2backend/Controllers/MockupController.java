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

@CrossOrigin(origins = {"http://localhost:3000", "*"})
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
            mockup.setAllowJWT(allowToken);
            mockup = mockupService.generateUUID(mockup);
            mockup.setOwner(user.getUsername());
            mockupService.save(mockup);
            user.getMockups().add(mockup);

            userService.save(user);
            return new ResponseEntity<>(mockup, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            response.put("message", "no se encontró el usuario");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/mockup")
    @PreAuthorize("hasAuthority('admin')")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Mockup> findAll() {
        return mockupService.findAll();
    }


    @DeleteMapping("/mockup/delete/{uuid}")
    @PreAuthorize("hasAnyAuthority('admin','cliente')")
    public ResponseEntity<?> delete(@PathVariable String uuid) {

        Map<String, Object> response = new HashMap<>();
        try {
            Mockup mockup = mockupService.findByUUID(uuid);
            userService.delete(userService.findByUsername(mockup.getOwner()));
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
