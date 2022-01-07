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
    @Column()
    private String fullName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String mail;
    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    List<String> roles;
    @ElementCollection(targetClass = Integer.class)
    List<Integer> eventsId;

    public Boolean hasRole(String r) {
        for(var role : roles) {
            if(role.equalsIgnoreCase(r)) {
                return true;
            }
        }
        return false;
    }


}
