package com.RYPS.usermicroservice.Controllers;

import com.RYPS.usermicroservice.Service.UserService;
import com.RYPS.usermicroservice.models.User;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@CrossOrigin()
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/user")
    @PreAuthorize("hasAuthority('admin')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Map<String, Object> response = new HashMap<>();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            userService.save(user);
        } catch (DataAccessException e) {
            response.put("message", "No se pudo crear el usuario en la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/user/employments")
    @PreAuthorize("hasAnyAuthority('admin','empleado')")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyAuthority('admin','empleado','cliente')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("user",userService.findByUsername(username));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (NoSuchElementException e) {

            response.put("message", "el mockup no fue encontrado ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{username}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> delete(@PathVariable String username) {

        Map<String, Object> response = new HashMap<>();
        try {
            userService.deleteUser(userService.findByUsername(username));
            response.put("message", "usuario eliminado con éxito");
        } catch (NoSuchElementException e) {
            response.put("message", "el usuario no fue encontrado ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    @PreAuthorize("hasAnyAuthority('admin','empleado','cliente')")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Integer id) {

        Map<String, Object> response = new HashMap<>();
        try {
            User old = userService.findByid(id).get();
            old.setUsername(user.getUsername());
            old.setMail(user.getMail());
            userService.save(old);
        } catch (NoSuchElementException e) {
            response.put("message", "el usuario no fue encontrado ");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
