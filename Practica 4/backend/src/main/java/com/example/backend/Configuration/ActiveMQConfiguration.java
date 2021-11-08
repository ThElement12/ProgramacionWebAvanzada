package com.example.backend.Configuration;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@Configuration
public class ActiveMQConfiguration {

    public static final String SENSOR_SUB = "notificacion_sensores";

    @Bean(initMethod = "start", destroyMethod = "stop")
    public BrokerService broker() throws Exception {
        BrokerService broker = new BrokerService();
        broker.addConnector("mqtt://0.0.0.0:1883");
        //broker.addConnector("stomp://localhost:61613");

        //broker.addConnector("ws://localhost:1884");
        broker.setPersistent(false);
        broker.setBrokerName("default");
        broker.start();
        return broker;
    }






}
