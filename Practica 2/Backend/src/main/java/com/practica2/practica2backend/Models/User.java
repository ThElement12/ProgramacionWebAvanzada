package com.practica2.practica2backend.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String username;
    @Column()
    private String password;
    @Column()
    private String mail;

    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    List<String> roles;

    @OneToMany( cascade = CascadeType.ALL)
    private List<Mockup> mockups;

    public Boolean removeMockup(String uuid){
        int num = -1;

        for (Mockup aux : mockups) {
            num++;
            if (aux.getUuid().equals(uuid)) {
                break;
            }
        }
        if(num > -1)
            mockups.remove(num);
        return false;
    }

}
