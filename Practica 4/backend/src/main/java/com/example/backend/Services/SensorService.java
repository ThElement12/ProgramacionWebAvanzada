package com.example.backend.Services;

import com.example.backend.Models.Sensor;
import com.example.backend.Models.SensorInfoDTO;
import com.example.backend.Repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    SimpMessagingTemplate template;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Sensor save(Sensor sensor){
        return sensorRepository.save(sensor);
    }

    public List<Sensor> findAll(){
        return sensorRepository.findAll();
    }

    public void publishSensor(){
        List<SensorInfoDTO> sensors = new ArrayList<>();
        SensorInfoDTO sensorInfo;
        Boolean sensorExist = true;
        int ind = 1;
        /*
        * TODO crear un objeto nuevo para saber cuantos sensores hay registrados*/
        while (sensorExist){
            if(!sensorRepository.findLastStatusByDevice(ind).isEmpty()){
                sensorInfo = new SensorInfoDTO();
                List<Sensor> sensorList = sensorRepository.findLastStatusByDevice(ind);
                for(Sensor sensor: sensorList){
                    sensorInfo.getTemperature().add(sensor.getTemperature());
                    sensorInfo.getHumidity().add(sensor.getHumidity());
                    sensorInfo.getTime().add(sensor.getGenerationDate().substring(10));
                    sensorInfo.setDate(sensor.getGenerationDate().substring(0,9));
                    sensorInfo.setDevice(sensor.getIdDevice().toString());
                }
                sensors.add(sensorInfo);
                ind++;
            }
            else {
                sensorExist = false;
            }
        }
        this.template.convertAndSend("/topic/sensor", sensors);


    }

}
