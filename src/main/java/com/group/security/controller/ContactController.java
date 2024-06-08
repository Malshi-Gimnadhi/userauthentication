package com.group.security.controller;

import com.group.security.entity.Contact;
import com.group.security.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @PostMapping("/add")
    public String add(@RequestBody Contact contact){
        contactService.saveContact(contact);
        return "New message added successfully";
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Contact> getAllContact(){
        return contactService.getAllContact();
    }

    @GetMapping("/{contactId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Contact> getContactDetailsById(@PathVariable String contactId) {
        return contactService.getContactDetailsById(contactId);
    }
}
