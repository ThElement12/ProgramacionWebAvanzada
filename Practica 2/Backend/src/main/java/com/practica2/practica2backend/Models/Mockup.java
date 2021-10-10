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
    private Integer status;

    @Column()
    private String method;

/*    @ElementCollection
    @CollectionTable(name = "mockup_header_mapping",
            joinColumns = {@JoinColumn(name = "mockup_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "header_key")
    @Column(name = "header_value")
    private Map<String, String> httpHeaders;*/

    @OneToMany( cascade = CascadeType.ALL)
    private List<HttpHeader> headers;

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
