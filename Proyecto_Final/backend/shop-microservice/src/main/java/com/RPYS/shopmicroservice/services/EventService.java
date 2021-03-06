package com.RPYS.shopmicroservice.services;

import com.RPYS.shopmicroservice.entities.Event;
import com.RPYS.shopmicroservice.entities.ProductRequest;
import com.RPYS.shopmicroservice.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event save(Event event){
        return eventRepository.save(event);
    }

    public List<Event> findAll(){
        return eventRepository.findAll();
    }

    public List<Event> findAllActive(){
        return eventRepository.findAllByActiveIsTrue();
    }

    public Event findByID(Integer id) {
        return eventRepository.findById(id).get();
    }

    public void delete(Event event) {
        eventRepository.delete(event);
    }

    public Event generateProductRequestsUUID(Event event) {
        for(ProductRequest request: event.getProductRequests()){
            request.setUuid(UUID.randomUUID().toString());
            if (event.getName().isEmpty()) {
                event.setName(request.getUuid());
            }
        }

        return event;
    }

    public List<Event> findAllEventsByEventsId(List<Integer> e){
        List<Event> events = new ArrayList<>();
        for(Integer aux:e){
            events.add(eventRepository.findById(aux).get());
        }
        return events;
    }

}
