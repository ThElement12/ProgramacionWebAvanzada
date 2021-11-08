package com.example.backend;

import org.apache.activemq.broker.BrokerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BackendApplication.class, args);

    }

}

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enable", matchIfMissing = true)
class SpringConfig {

}

