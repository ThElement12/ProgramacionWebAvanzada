package com.practica2.practica2backend.Controllers;

import com.practica2.practica2backend.Configuration.AuthTokenFilter;
import com.practica2.practica2backend.Models.LogInRequest;
import com.practica2.practica2backend.Models.User;
import com.practica2.practica2backend.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@CrossOrigin()
@RestController
public class AuthController {

    private final UserService userService;
    private final AuthTokenFilter jwtUtil;

    public AuthController(UserService userService, AuthTokenFilter jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> logIn( @RequestBody LogInRequest loginRequest){
        Map<String,Object> response = new HashMap<>();
        User user = null;
        try{
            user = userService.findByUsername(loginRequest.getCredential());
            BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
            if(bCryptPasswordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
                String jwt = jwtUtil.generateJwtToken(user);
                response.put("token",jwt);
            }
            else{
                response.put("message", "Nombre de usuario o contraseña incorrecta.");
                response.put("Error", "cannot access");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }
        catch (NoSuchElementException e) {

        response.put("message", "Nombre de usuario o contraseña incorrecta.");
        response.put("Error", e.getMessage().concat(": ").concat(e.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        } catch (NullPointerException exception){
                response.put("message", "Campos vacios.");
                response.put("Error", exception.getMessage().concat(": ").concat(exception.getMessage()));
                return new ResponseEntity<>(response, HttpStatus.OK);
        }




        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
