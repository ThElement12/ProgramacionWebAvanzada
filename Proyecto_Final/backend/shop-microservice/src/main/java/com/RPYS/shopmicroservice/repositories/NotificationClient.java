package com.RPYS.shopmicroservice.repositories;

import com.RPYS.shopmicroservice.entities.Event;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "mail-microservice")
public interface NotificationClient {
    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    Boolean broadcast(@RequestBody Event event,
                                @RequestHeader(value = "Authorization", required = false) String token);
}
