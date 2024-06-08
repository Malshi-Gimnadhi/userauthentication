package com.group.security.service;

import com.group.security.Model.Eventcards;
import com.group.security.Model.TicketDetails;
import com.group.security.repository.EventRepository;
import com.group.security.repository.TicketDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EventServiceImp implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketDetailsRepository ticketDetailsRepository;

    @Override
    public Eventcards saveEventcards(Eventcards eventcards) {
        return eventRepository.save(eventcards);
    }

    @Override
    public List<Eventcards> getAllEventcards() {
        return eventRepository.findAll();
    }

    @Override
    public List<Eventcards> getEventcardsByCategory(String category) {
        return eventRepository.findByEventCategory(category);
    }

    @Override
    public List<TicketDetails> getTicketDetailsByEventId(Long eventId) {
        return ticketDetailsRepository.findByEventId(eventId);
    }

    @Override
    public List<TicketDetails> getCustomTicketDetailsByEventId(Long eventId) {
        return ticketDetailsRepository.findCustomTicketDetailsByEventId(eventId);
    }
}