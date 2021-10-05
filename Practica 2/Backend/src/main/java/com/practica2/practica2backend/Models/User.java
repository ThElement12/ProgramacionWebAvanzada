package com.practica2.practica2backend.Models;

import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
    private String password;

    @ManyToOne()
    private Role UserRole;

    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name = "id_mockup")
    private Set<Mockup> mockups;

}
