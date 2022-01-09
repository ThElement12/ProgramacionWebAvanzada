package com.RPYS.notificationmicroservice.repositories;

import com.RPYS.notificationmicroservice.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-microservice")
public interface UserClient {
    @GetMapping("/{username}")
    @PreAuthorize("hasAnyAuthority('admin','empleado','cliente')")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> findByUsername(@PathVariable String username);

    @GetMapping("/employments")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    List<User> findAllEmployments(@RequestHeader(value = "Authorization") String authorizationHeader);
}
