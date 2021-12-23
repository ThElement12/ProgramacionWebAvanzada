package com.RYPS.usermicroservice.models;

import lombok.Data;

@Data
public class LogInRequest {
    private String credential;

    private String password;
}
