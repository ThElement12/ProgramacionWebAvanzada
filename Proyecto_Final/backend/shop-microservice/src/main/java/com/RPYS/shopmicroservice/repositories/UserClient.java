package com.RPYS.shopmicroservice.repositories;

import com.RPYS.shopmicroservice.models.User;
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
    User findByUsername(@PathVariable String username,@RequestHeader(value = "Authorization") String authorizationHeader);

    @GetMapping("/events/{username}")
    @PreAuthorize("hasAnyAuthority('admin','empleado','cliente')")
    @ResponseStatus(HttpStatus.OK)
    List<Integer> findAllEventsIdByUsername(@PathVariable String username,
                                            @RequestHeader(value = "Authorization") String authorizationHeader);

    @PostMapping("/events/{username}/{eventId}")
    @PreAuthorize("hasAnyAuthority('admin','empleado','cliente')")
    @ResponseStatus(HttpStatus.OK)
    Boolean saveEventIdByUsername(@PathVariable String username, @PathVariable Integer eventId,
                                  @RequestHeader(value = "Authorization") String authorizationHeader);

}
