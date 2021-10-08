package com.practica2.practica2backend.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Getter
@Setter
@Data
public class Mockup {

    @Id
    private String uuid;

    @Column()
    private String name;

    @Column()
    private Long HttpStatus;

    @Column()
    private String method;

    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    Set<String> httpHeaders;

    @Column()
    private String contentType;

    @Column()
    private String body;

    @Column()
    private LocalDateTime creation;

    @Column()
    private String token;

    @Column()
    private String expiryTime;

}
