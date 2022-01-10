package com.RYPS.usermicroservice.Controllers;

import com.RYPS.usermicroservice.Configurations.AuthTokenFilter;
import com.RYPS.usermicroservice.Repositories.NotificationClient;
import com.RYPS.usermicroservice.Service.UserService;
import com.RYPS.usermicroservice.models.LogInRequest;
import com.RYPS.usermicroservice.models.User;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@CrossOrigin()
@RestController
@RequestMapping("/auth")

public class AuthController {

    private final UserService userService;
    private final AuthTokenFilter jwtUtil;
    private final NotificationClient notificationClient;

    public AuthController(UserService userService, AuthTokenFilter jwtUtil, NotificationClient notificationClient) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.notificationClient = notificationClient;
    }

    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody LogInRequest loginRequest) {
        Map<String, Object> response = new HashMap<>();
        User user = null;
        try {
            user = userService.findByUsername(loginRequest.getCredential());
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if (bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                String jwt = jwtUtil.generateJwtToken(user);
                response.put("token", jwt);
            } else {
                response.put("message", "Nombre de usuario o contraseña incorrecta.");
                response.put("Error", "cannot access");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (NoSuchElementException e) {

            response.put("message", "Nombre de usuario o contraseña incorrecta.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        } catch (NullPointerException exception) {
            response.put("message", "Campos vacios.");
            response.put("Error", exception.getMessage().concat(": ").concat(exception.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Map<String, Object> response = new HashMap<>();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            user = userService.save(user);
            String token = "Bearer ";
            token += jwtUtil.generateJwtToken(user);

            notificationClient.newUser(user,token);
        } catch (DataAccessException e) {
            response.put("message", "No se pudo crear el usuario en la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }
}
