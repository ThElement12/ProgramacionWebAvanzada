package com.practica2.practica2backend;

import com.practica2.practica2backend.Models.User;
import com.practica2.practica2backend.Repositories.UserRepository;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class Practica2BackendApplication implements CommandLineRunner {

    @Autowired
    private Environment environment;

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
            if(usuarioRepository.findAll().isEmpty())
                usuarioRepository.save(admin);
        };

    }

    /*@Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "8082");
    }*/

    @Override
    public void run(String... args) throws Exception {
        String db_nombre = environment.getProperty("APP_NAME");
        String direccionDb = environment.getProperty("DB_HOST");
        System.out.println("Nombre de la Aplicación = "+db_nombre);
        System.out.println("Dirección de la Aplicación = "+direccionDb);
    }
}
