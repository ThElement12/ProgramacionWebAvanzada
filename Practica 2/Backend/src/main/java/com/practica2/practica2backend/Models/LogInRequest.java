package com.practica2.practica2backend.Models;

import lombok.Data;

@Data
public class LogInRequest {
    private String credential;

    private String password;
}
