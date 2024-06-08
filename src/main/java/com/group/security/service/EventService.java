package com.group.security.service;
import com.group.security.Model.Eventcards;
import com.group.security.Model.TicketDetails;
import java.util.List;
public interface EventService {
    Eventcards saveEventcards(Eventcards eventcards);
    List<Eventcards> getAllEventcards();
    List<Eventcards> getEventcardsByCategory(String category);
    List<TicketDetails> getTicketDetailsByEventId(Long eventId);
    List<TicketDetails> getCustomTicketDetailsByEventId(Long eventId);
}