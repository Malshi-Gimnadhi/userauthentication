package com.group.security.controller;

import com.group.security.Exception.EventNotFoundException;
import com.group.security.Model.Event;
import com.group.security.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @PostMapping("/event")
    Event newEvent(@RequestBody Event newEvent){
        return eventRepository.save(newEvent);
    }

    @GetMapping("/events")
    List<Event> getAllEvents(){
        return eventRepository.findAll();
    }

    @GetMapping("/event/{id}")
    Event getLandById(@PathVariable Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    @PutMapping("/event/{id}")
    Event updateEvent(@RequestBody Event newEvent, @PathVariable Long id) {
        return eventRepository.findById(id)
                .map(event -> {
                    event.setEventname(newEvent.getEventname());
                    event.setEventid(newEvent.getEventid());
                    event.setDate(newEvent.getDate());
                    event.setTime(newEvent.getTime());
                    event.setVenue(newEvent.getVenue());
                    event.setDescription(newEvent.getDescription());
                    event.setCategory(newEvent.getCategory());
                    event.setOrganizer(newEvent.getOrganizer());
                    event.setTickectprices(newEvent.getTickectprices());
                    event.setTickectcategory(newEvent.getTickectcategory());
                    event.setStatus(newEvent.getStatus());

                    return eventRepository.save(event);
                }).orElseThrow(() -> new EventNotFoundException(id));
    }


    @DeleteMapping("/event/{id}")
    String deleteEvent(@PathVariable Long id){
        if(!eventRepository.existsById(id)){
            throw new EventNotFoundException(id);
        }
        eventRepository.deleteById(id);
        return  "Event with id "+id+" has been deleted success.";
    }

}
