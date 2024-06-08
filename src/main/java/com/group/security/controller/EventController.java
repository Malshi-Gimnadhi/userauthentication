package com.group.security.controller;

import com.group.security.Exception.ResourceNotFoundException;
import com.group.security.Model.Eventcards;
import com.group.security.Model.TicketDetails;
import com.group.security.repository.EventCardsRepository;
import com.group.security.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventcards")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventCardsRepository eventCardsRepository;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String add(@RequestBody Eventcards eventcards){
        eventService.saveEventcards(eventcards);
        return "New event added successfully";
    }

    @GetMapping("/getAll")
    public List<Eventcards> getAllEventcards(){
        return eventService.getAllEventcards();
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        Eventcards event = eventCardsRepository.findByEventId(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with eventId: " + eventId));
        eventCardsRepository.delete(event);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{eventId}")
    public Eventcards updateEvent(@PathVariable Long eventId, @RequestBody Eventcards eventDetails) {
        Eventcards event = eventCardsRepository.findByEventId(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with eventId: " + eventId));
        event.setOrganizerName(eventDetails.getOrganizerName());
        event.setOrganizerPhone(eventDetails.getOrganizerPhone());
        event.setOrganizerNic(eventDetails.getOrganizerNic());
        event.setOrganizerEmail(eventDetails.getOrganizerEmail());
        event.setEventName(eventDetails.getEventName());
        event.setEventDate(eventDetails.getEventDate());
        event.setEventTime(eventDetails.getEventTime());
        event.setEventLocation(eventDetails.getEventLocation());
        event.setEventDescription(eventDetails.getEventDescription());
        event.setTicketDetails(eventDetails.getTicketDetails());
        event.setEventCategory(eventDetails.getEventCategory());
        event.setFlyerLink(eventDetails.getFlyerLink());
        return eventCardsRepository.save(event);
    }


    @GetMapping("/category/{category}")
    public List<Eventcards> getEventcardsByCategory(@PathVariable String category) {
        return eventService.getEventcardsByCategory(category);
    }

    @GetMapping("/ticketDetails/{eventId}")
    public List<TicketDetails> getTicketDetailsByEventId(@PathVariable Long eventId) {
        return eventService.getTicketDetailsByEventId(eventId);
    }

    @GetMapping("/customTicketDetails/{eventId}")
    public List<TicketDetails> getCustomTicketDetailsByEventId(@PathVariable Long eventId) {
        return eventService.getCustomTicketDetailsByEventId(eventId);
    }
}

//import com.group.security.Exception.EventNotFoundException;
//import com.group.security.Model.Event;
//import com.group.security.repository.EventRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@CrossOrigin("http://localhost:3000")
//public class EventController {
//    @Autowired
//    private EventRepository eventRepository;
//
//    @PostMapping("/event")
//    Event newEvent(@RequestBody Event newEvent){
//        return eventRepository.save(newEvent);
//    }
//
//    @GetMapping("/events")
//    List<Event> getAllEvents(){
//        return eventRepository.findAll();
//    }
//
//    @GetMapping("/event/{id}")
//    Event getLandById(@PathVariable Long id) {
//        return eventRepository.findById(id)
//                .orElseThrow(() -> new EventNotFoundException(id));
//    }
//
//    @PutMapping("/event/{id}")
//    Event updateEvent(@RequestBody Event newEvent, @PathVariable Long id) {
//        return eventRepository.findById(id)
//                .map(event -> {
//                    event.setEventname(newEvent.getEventname());
//                    event.setEventid(newEvent.getEventid());
//                    event.setDate(newEvent.getDate());
//                    event.setTime(newEvent.getTime());
//                    event.setVenue(newEvent.getVenue());
//                    event.setDescription(newEvent.getDescription());
//                    event.setCategory(newEvent.getCategory());
//                    event.setOrganizer(newEvent.getOrganizer());
//                    event.setTickectprices(newEvent.getTickectprices());
//                    event.setTickectcategory(newEvent.getTickectcategory());
//                    event.setStatus(newEvent.getStatus());
//
//                    return eventRepository.save(event);
//                }).orElseThrow(() -> new EventNotFoundException(id));
//    }
//
//
//    @DeleteMapping("/event/{id}")
//    String deleteEvent(@PathVariable Long id){
//        if(!eventRepository.existsById(id)){
//            throw new EventNotFoundException(id);
//        }
//        eventRepository.deleteById(id);
//        return  "Event with id "+id+" has been deleted success.";
//    }
//
//}
