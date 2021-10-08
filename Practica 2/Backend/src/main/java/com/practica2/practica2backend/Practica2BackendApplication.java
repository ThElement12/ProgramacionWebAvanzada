package com.practica2.practica2backend;

import com.practica2.practica2backend.Models.User;
import com.practica2.practica2backend.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class Practica2BackendApplication {

    public static void main(String[] args) {


        SpringApplication.run(Practica2BackendApplication.class, args);


    }

    @Bean
    public CommandLineRunner loadData(UserRepository usuarioRepository){
        System.out.println("Inicializando los datos.");
        return args -> {
            //
            BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
            //Cargando la información.
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(bCryptPasswordEncoder.encode("admin"));
            admin.setMail("Administrador");
            admin.setRoles(Collections.singletonList("admin"));
            usuarioRepository.save(admin);
        };
    }

}
