package com.RPYS.shopmicroservice.entities;

import com.RPYS.shopmicroservice.models.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name = "id_plan")
    private Plan plan;

    @Column(name = "user_username")
    private String username;

    @Column()
    private Double totalPrice;

    @OneToMany()
    private List<ProductRequest> productRequests;

    @Transient()
    private User user;

    @Column()
    private Boolean active = true;

}
