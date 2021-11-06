package com.example.backend.Services;

import com.example.backend.Models.Sensor;
import com.example.backend.Repositories.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Sensor save(Sensor sensor){
        return sensorRepository.save(sensor);
    }

    public List<Sensor> findAll(){
        return sensorRepository.findAll();
    }

}
