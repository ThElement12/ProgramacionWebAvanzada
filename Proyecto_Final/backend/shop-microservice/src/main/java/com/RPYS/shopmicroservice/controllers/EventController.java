package com.RPYS.shopmicroservice.controllers;

import com.RPYS.shopmicroservice.entities.Event;
import com.RPYS.shopmicroservice.entities.Product;
import com.RPYS.shopmicroservice.entities.ProductRequest;
import com.RPYS.shopmicroservice.models.User;
import com.RPYS.shopmicroservice.repositories.NotificationClient;
import com.RPYS.shopmicroservice.repositories.UserClient;
import com.RPYS.shopmicroservice.services.EventService;
import com.RPYS.shopmicroservice.services.ProductService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    private final NotificationClient notificationClient;

    public EventController(EventService eventService, ProductService productService, UserClient userClient, NotificationClient notificationClient) {
        this.eventService = eventService;
        this.productService = productService;
        this.userClient = userClient;
        this.notificationClient = notificationClient;
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
            User user = userClient.findByUsername(event.getUsername(),token);
            event.setUser(user);
            if(userClient.saveEventIdByUsername(event.getUsername(), event.getId(),token)){
                if(notificationClient.broadcast(event,token)){
                    return new ResponseEntity<>(event, HttpStatus.CREATED);
                }
                else {
                    response.put("message", "error al comunicarse con el microservice de notificaciones");
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }

            }
            else {
                response.put("message", "error al comunicarse con el microservice de usuario");
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
                                            @RequestHeader(value = "Authorization", required = false) String token) {
        Map<String, Object> response = new HashMap<>();
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

    @Scheduled(fixedRate=60*60*1000)
    public void updateStock(){
        List<Event> events = eventService.findAllActive();
        for(Event event:events){
            if(event.getStartTime().isBefore(LocalDateTime.now()) || event.getStartTime().isEqual(LocalDateTime.now()) ){
                for(ProductRequest productRequest:event.getProductRequests()){
                    Product product = productService.findById(productRequest.getProductId());
                    product.setStock(product.getStock() - productRequest.getRequested());
                    productService.save(product);
                }
                eventService.save(event);
            }
            else if(event.getEndTime().isBefore(LocalDateTime.now()) || event.getEndTime().isEqual(LocalDateTime.now())){
                for(ProductRequest productRequest:event.getProductRequests()){
                    Product product = productService.findById(productRequest.getProductId());
                    product.setStock(product.getStock() + productRequest.getRequested());
                    productService.save(product);
                }
                event.setActive(false);
                eventService.save(event);
            }

        }
    }




}
