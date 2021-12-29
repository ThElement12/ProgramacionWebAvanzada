package com.RPYS.shopmicroservice.entities;

import com.RPYS.shopmicroservice.models.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column()
    private String name;

    @Column()
    private LocalDateTime startTime;

    @Column()
    private LocalDateTime endTime;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Plan plan;

    @Column(name = "user_username")
    private String username;

    @Transient()
    private User user;

}
