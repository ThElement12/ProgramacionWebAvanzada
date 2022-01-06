package com.RPYS.notificationmicroservice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Email {
    private String fullName;
    private String to;
    private String from;
    private String message;
    private String title;
}
