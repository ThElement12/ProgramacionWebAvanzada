package com.RYPS.usermicroservice;

import com.RYPS.usermicroservice.Repositories.UserRepository;
import com.RYPS.usermicroservice.models.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;

@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
public class UserMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMicroserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(UserRepository usuarioRepository){
		System.out.println("Inicializando los datos. ->");
		return args -> {
			//
			BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
			//Cargando la información.
			User admin = new User();
			admin.setUsername("admin");
			admin.setFullName("Robert García");
			admin.setPassword(bCryptPasswordEncoder.encode("admin"));
			admin.setMail("r_rokstar@hotmail.com");
			admin.setRoles(Collections.singletonList("admin"));
			admin.setEventsId(new ArrayList<>());
			if(usuarioRepository.findAll().isEmpty())
				usuarioRepository.save(admin);
		};
	}

}
