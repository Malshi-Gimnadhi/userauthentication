package com.group.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.group.security.Model.TicketDetails;
import com.group.security.repository.TicketDetailsRepository;

import java.util.List;

@Service
public class TicketDetailsService {

    @Autowired
    private TicketDetailsRepository ticketDetailsRepository;

    public List<TicketDetails> saveTicketDetails(List<TicketDetails> ticketDetailsList) {
        return ticketDetailsRepository.saveAll(ticketDetailsList);
    }
}
