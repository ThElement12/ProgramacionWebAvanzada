package com.practica2.practica2backend.Models;

import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@Table(name = "user_app")
public class User implements Serializable{
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String username;
    @Column()
    private String password;
    @Column()
    private String name;

    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();*/

    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    List<String> roles;

    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name = "id_mockup")
    private Set<Mockup> mockups;

}
