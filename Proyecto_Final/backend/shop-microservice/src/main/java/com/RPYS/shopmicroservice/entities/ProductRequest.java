package com.RPYS.shopmicroservice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ProductRequest {
    @Id
    private String uuid;

    @Column()
    private Integer productId;

    @Column()
    private String name;

    @Column()
    private Integer requested;

}
