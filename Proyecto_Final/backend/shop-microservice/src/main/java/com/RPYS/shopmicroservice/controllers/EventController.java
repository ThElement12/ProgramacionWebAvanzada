package com.RPYS.shopmicroservice.controllers;

import com.RPYS.shopmicroservice.entities.Event;
import com.RPYS.shopmicroservice.repositories.UserClient;
import com.RPYS.shopmicroservice.services.EventService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@CrossOrigin()
@RestController
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;
    private final UserClient userClient;

    public EventController(EventService eventService, UserClient userClient) {
        this.eventService = eventService;
        this.userClient = userClient;
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('admin','empleado')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> create(@RequestBody Event event) {
        Map<String, Object> response = new HashMap<>();

        try {
            eventService.save(event);
            // se le envia una notificacion al usuario de que se acaba de crear un evento

        } catch (DataAccessException e) {
            response.put("message", "No se pudo crear el evento en la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(event, HttpStatus.CREATED);

    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('admin','empleado')")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Event> findAll() {
        return eventService.findAll();
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAnyAuthority('admin','empleado','cliente')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>(),request = new HashMap<>();
        try {
            request = (Map<String, Object>) userClient.findAllEventsIdByUsername(username);

            response.put("events",eventService.findAllEventsByEventsId((List<Integer>) request.get("eventsId")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (NoSuchElementException e) {
            response.put("message", "no se encontraron eventos para ".concat(username));
            response.put("Error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


}
