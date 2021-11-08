package com.example.backend.Controllers;

import com.example.backend.Models.Sensor;
import com.example.backend.Models.SensorInfoDTO;
import com.example.backend.Services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin()
@RestController
public class SensorController {

    @Autowired
    SimpMessagingTemplate template;

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @SendTo("/topic/sensor")
    public ResponseEntity<?> broadcastSensors(@Payload List<SensorInfoDTO> sensors) {
        Map<String,Object> response = new HashMap<>();
        System.out.println("ola como esta");
        response.put("sensors",sensors);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Scheduled(fixedRate = 10000)
    public void greeting() {
        List<Sensor> sensors = sensorService.findAll();
        this.template.convertAndSend("/topic/sensor", sensors);
    }
}
