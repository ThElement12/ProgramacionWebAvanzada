package com.example.backend.Configuration;

import org.apache.activemq.broker.BrokerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@EnableJms
@Configuration
public class ActiveMQConfiguration {

    public static final String SENSOR_SUB = "sensor-sub";


    public static final String SENSOR_PUB = "sensor-sub";

    @Bean
    public BrokerService broker() throws Exception {
        BrokerService broker = new BrokerService();
        broker.addConnector("mqtt://localhost:1883");
        broker.setPersistent(false);
        broker.setBrokerName("default");
        broker.start();
        return broker;
    }


}
