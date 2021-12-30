package com.RPYS.shopmicroservice.controllers;

import com.RPYS.shopmicroservice.entities.Event;
import com.RPYS.shopmicroservice.repositories.UserClient;
import com.RPYS.shopmicroservice.services.EventService;
import com.RPYS.shopmicroservice.services.ProductService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@CrossOrigin()
@RestController
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;
    private final ProductService productService;
    private final UserClient userClient;

    public EventController(EventService eventService, ProductService productService, UserClient userClient) {
        this.eventService = eventService;
        this.productService = productService;
        this.userClient = userClient;
    }

    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> create(@RequestBody Event event,@RequestHeader(value = "Authorization",required = false) String token) {
        Map<String, Object> response = new HashMap<>();

        try {
            event = eventService.generateProductRequestsUUID(event);
            event.getProductRequests().forEach(productService::saveProductRequest);
            eventService.save(event);
            // se le envia una notificacion al usuario de que se acaba de crear un evento
            if(userClient.saveEventIdByUsername(event.getUsername(), event.getId(),token)){
                return new ResponseEntity<>(event, HttpStatus.CREATED);
            }
            else {
                response.put("message", "error al comunicarse con el microservicio de usuario");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (DataAccessException e) {
            response.put("message", "No se pudo crear el evento en la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('admin','empleado')")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Event> findAll() {
        return eventService.findAll();
    }

    @GetMapping("/{username}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findByUsername(@PathVariable String username,
                                            HttpServletResponse header,
                                            @RequestHeader(value = "Authorization",required = false) String token) {
        Map<String, Object> response = new HashMap<>(),request;
        try {

            List<Integer> events = userClient.findAllEventsIdByUsername(username,token);

            response.put("events",eventService.findAllEventsByEventsId(events));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (NoSuchElementException e) {
            response.put("message", "no se encontraron eventos para ".concat(username));
            response.put("Error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


}
