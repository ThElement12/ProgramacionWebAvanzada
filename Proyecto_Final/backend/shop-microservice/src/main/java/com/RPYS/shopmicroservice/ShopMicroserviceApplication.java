package com.RPYS.shopmicroservice;

import com.RPYS.shopmicroservice.entities.Plan;
import com.RPYS.shopmicroservice.entities.Product;
import com.RPYS.shopmicroservice.repositories.PlanRepository;
import com.RPYS.shopmicroservice.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class ShopMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopMicroserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(PlanRepository planRepository, ProductRepository productRepository){
		System.out.println("Inicializando los planes.");
		return args -> {
			//declarando los productos
			if(productRepository.findAll().isEmpty() || planRepository.findAll().isEmpty()) {

				Product product = new Product(), product1 = new Product(), product2 = new Product(), product3 = new Product(),
						product4 = new Product(), product5 = new Product(), product6 = new Product(), product7 = new Product(), product8 = new Product(),
						product9 = new Product(), product10 = new Product(),product11 = new Product(),product12 = new Product();

				product.setName("Mesa para 2");
				product.setStock(250);
				product.setPrice(999.99);
				productRepository.save(product);

				product1.setName("Mesa para 4");
				product1.setStock(250);
				product1.setPrice(1299.99);
				productRepository.save(product1);

				product2.setName("Mesa para 8");
				product2.setStock(250);
				product2.setPrice(2499.99);
				productRepository.save(product2);

				product3.setName("Silla");
				product3.setStock(400);
				product3.setPrice(99.99);
				productRepository.save(product3);

				product4.setName("Bocina");
				product4.setStock(125);
				product4.setPrice(1999.99);
				productRepository.save(product4);

				product5.setName("Pantalla led");
				product5.setStock(75);
				product5.setPrice(2499.99);
				productRepository.save(product5);

				product6.setName("Camara");
				product6.setStock(75);
				product6.setPrice(499.99);
				productRepository.save(product6);

				product7.setName("Pantalla verde");
				product7.setStock(75);
				product7.setPrice(499.99);
				productRepository.save(product7);

				product8.setName("Arreglos florales");
				product8.setStock(25);
				product8.setPrice(2499.99);
				productRepository.save(product8);

				product9.setName("Luces");
				product9.setStock(200);
				product9.setPrice(499.99);
				productRepository.save(product9);

				product10.setName("Maquina de humo");
				product10.setStock(200);
				product10.setPrice(499.99);
				productRepository.save(product10);

				Plan plan1 = new Plan(),plan2 = new Plan(),plan3 = new Plan(),plan4 = new Plan();
				plan1.setName("Pre-Boda");
				List<Product> products = Arrays.asList(product3,product4,product5,product6,product7,product9,product10);
				plan1.setProducts(new HashSet<>(products));
				planRepository.save(plan1);

				plan2.setName("Boda");
				products = Arrays.asList(product,product1,product2,product3,product4,product6,product8);
				plan2.setProducts(new HashSet<>(products));
				planRepository.save(plan2);
				plan3.setName("Birthday");
				products = Arrays.asList(product,product1,product2,product3,product4,product5,product6,product9,product10);
				plan3.setProducts(new HashSet<>(products));
				planRepository.save(plan3);
				plan4.setName("Video Evento");
				products = Arrays.asList(product4,product5,product6,product7,product9,product10);
				plan4.setProducts(new HashSet<>(products));
				planRepository.save(plan4);
			}


		};
	}
}
