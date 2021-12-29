package com.RPYS.shopmicroservice.repositories;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "user-microservice")
@RequestMapping("/user")
public interface UserClient {
    @GetMapping("/{username}")
    @PreAuthorize("hasAnyAuthority('admin','empleado','cliente')")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> findByUsername(@PathVariable String username);

    @GetMapping("/events/{username}")
    @PreAuthorize("hasAnyAuthority('admin','empleado','cliente')")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> findAllEventsIdByUsername(@PathVariable String username);

}
