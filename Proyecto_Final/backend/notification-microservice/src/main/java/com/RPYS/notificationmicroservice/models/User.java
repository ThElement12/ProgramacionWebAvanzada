package com.RPYS.notificationmicroservice.models;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private Integer id;
    private String username;
    private String fullName;
    private String password;
    private String mail;
    private List<String> roles;
}
