package com.practica2.practica2backend.Models;

import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@Getter
@Setter
public class User implements Serializable{
    @Id
    @GeneratedValue
    private Long id;
    @Column()
    private String username;
    @Column()
    private String pass;
    @Column()
    private String rol;
}
