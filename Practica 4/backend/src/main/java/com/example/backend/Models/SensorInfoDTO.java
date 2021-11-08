package com.example.backend.Models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SensorInfoDTO {
    private String device;
    private String date;
    private List<Double> temperature = new ArrayList<>();
    private List<Double> humidity = new ArrayList<>();
    private List<String> time = new ArrayList<>();

}
