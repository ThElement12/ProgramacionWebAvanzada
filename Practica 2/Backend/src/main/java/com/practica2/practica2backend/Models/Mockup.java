package com.practica2.practica2backend.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.util.Map;

@Entity
@Getter
@Setter

public class Mockup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column()
    private String url;

    @Column()
    private Long status;

    @Column()
    private String method;

    @ElementCollection
    @CollectionTable(name = "mockup_header_mapping",
            joinColumns = {@JoinColumn(name = "mockup_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "header_name")
    @Column(name = "header")
    private Map<String, String> httpHeader;

    @Column()
    private String contentType;

    @Column()
    private String body;


}
