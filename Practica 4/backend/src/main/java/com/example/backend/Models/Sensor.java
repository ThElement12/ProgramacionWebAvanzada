package com.example.backend.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column()
    private Integer idDevice;

    @Column()
    private String generationDate;

    @Column()
    private Double temperature;

    @Column()
    private Double humidity;

}
