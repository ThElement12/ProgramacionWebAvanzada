package com.practica2.practica2backend.Controllers;

import com.practica2.practica2backend.Models.User;
import com.practica2.practica2backend.Services.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = { "https://www.noisewatcher.systems","http://localhost:3000","*"})
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    @PreAuthorize("hasAuthority('admin')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> createUser( @RequestBody User user){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        Map<String,Object> response = new HashMap<>();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try{
            userService.save(user);
        }catch (DataAccessException e) {
            response.put("message", "No se pudo crear el usuario en la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('admin')")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<User> findAll() {
        return userService.findAll();
    }


}
