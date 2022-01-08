package com.RPYS.notificationmicroservice.models;

import lombok.Data;

@Data
public class ProductRequest {

    private String uuid;

    private Integer productId;

    private String name;

    private Integer requested;
}
