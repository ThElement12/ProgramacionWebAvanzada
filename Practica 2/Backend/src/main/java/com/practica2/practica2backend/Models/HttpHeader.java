package com.practica2.practica2backend.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

@Entity
@Getter
@Setter
public class HttpHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


}
