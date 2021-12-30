package com.RPYS.shopmicroservice.repositories;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-microservice")
public interface UserClient {
    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyAuthority('admin','empleado','cliente')")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> findByUsername(@PathVariable String username);

    @GetMapping("/user/events/{username}")
    @PreAuthorize("hasAnyAuthority('admin','empleado','cliente')")
    @ResponseStatus(HttpStatus.OK)
    List<Integer> findAllEventsIdByUsername(@PathVariable String username,
                                            @RequestHeader(value = "Authorization", required = true) String authorizationHeader);

    @PostMapping("/user/events/{username}/{eventId}")
    @PreAuthorize("hasAnyAuthority('admin','empleado','cliente')")
    @ResponseStatus(HttpStatus.OK)
    Boolean saveEventIdByUsername(@PathVariable String username, @PathVariable Integer eventId,
                                  @RequestHeader(value = "Authorization") String authorizationHeader);

}
