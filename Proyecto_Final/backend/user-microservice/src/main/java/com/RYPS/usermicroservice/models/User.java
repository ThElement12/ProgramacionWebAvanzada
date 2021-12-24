package com.RYPS.usermicroservice.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user_app")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String username;
    @Column(nullable = true)
    private String password;
    @Column(nullable = true)
    private String mail;
    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    List<String> roles;

}