package com.practica1.practica1backend.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Student {


    private int enrollment;
    private String name;
    private String lastName;
    private String phone;

}
