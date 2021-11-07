package com.example.backend.MessageQueue;

import com.example.backend.Configuration.ActiveMQConfiguration;
import com.example.backend.Models.Sensor;
import com.example.backend.Services.SensorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.command.ActiveMQBytesMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Component
public class SensorSubscriber {

    private static final Logger log = LoggerFactory.getLogger(SensorSubscriber.class);
    private final SensorService sensorService;

    public SensorSubscriber(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @JmsListener(destination = "sensor-sub")
    public void receiveSensor(ActiveMQBytesMessage sensorMessage) throws JMSException, JsonProcessingException {

        //BytesMessage bm = (BytesMessage) textMessage;
        byte data[] = new byte[(int) sensorMessage.getBodyLength()];
        sensorMessage.readBytes(data);
        String s = new String(data);
        Sensor sensor = new ObjectMapper().readValue(s,Sensor.class);
        System.out.println("Recibiendo informacion desde -> ".concat(sensor.getIdDevice().toString()));
        sensorService.save(sensor);

        //log.info("Receive text message: " + textMessage.getText());
        //System.out.println("Receive object message: " + ((Sensor)objectMessage.getObject()).getIdDevice());
    }


}
