package com.RPYS.notificationmicroservice.models;

import lombok.Data;

import java.util.Set;

@Data
public class Plan {
    private Integer id;

    private String name;

    private Set<Product> products;
}
