package com.RYPS.usermicroservice.Repositories;

import com.RYPS.usermicroservice.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "mail-microservice")
public interface NotificationClient {
    @PostMapping("/auth/user")
    @ResponseStatus(HttpStatus.OK)
    Boolean newUser(@RequestBody User user,@RequestHeader(value = "Authorization") String authorizationHeader);


}
