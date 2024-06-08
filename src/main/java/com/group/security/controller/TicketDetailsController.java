package com.group.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.group.security.Model.TicketDetails;
import com.group.security.service.TicketDetailsService;

import java.util.List;

@RestController
@RequestMapping("/ticketDetails")
public class TicketDetailsController {

    @Autowired
    private TicketDetailsService ticketDetailsService;

    @PostMapping("/add")
    public List<TicketDetails> addTicketDetails(@RequestBody List<TicketDetails> ticketDetailsList) {
        return ticketDetailsService.saveTicketDetails(ticketDetailsList);
    }
}
