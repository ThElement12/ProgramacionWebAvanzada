package com.RPYS.notificationmicroservice.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Event {

    private Integer id;

    private String name;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Plan plan;

    private String username;

    private Double totalPrice;

    private List<ProductRequest> productRequests;

    private User user;

    private Boolean active = true;
}
