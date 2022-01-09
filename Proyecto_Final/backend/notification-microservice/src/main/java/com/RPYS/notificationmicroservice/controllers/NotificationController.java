package com.RPYS.notificationmicroservice.controllers;

import com.RPYS.notificationmicroservice.models.Event;
import com.RPYS.notificationmicroservice.models.User;
import com.RPYS.notificationmicroservice.repositories.UserClient;
import com.RPYS.notificationmicroservice.services.EmailService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin()
@RestController
@RequestMapping("/")
public class NotificationController {

    private final UserClient userClient;
    private final EmailService emailService;

    public NotificationController(UserClient userClient, EmailService emailService) {
        this.userClient = userClient;
        this.emailService = emailService;
    }

    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public Boolean broadcast(@RequestBody Event event,
                                       @RequestHeader(value = "Authorization",required = false) String token) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<User> users = userClient.findAllEmployments(token);
            emailService.sendBroadcastEmail("send-email",users,event);

        } catch (DataAccessException e) {
            response.put("message", "No se pudo crear el evento en la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return false;
        }

        response.put("message", "Los correos se enviaron con éxito...");
        return true;

    }
}
