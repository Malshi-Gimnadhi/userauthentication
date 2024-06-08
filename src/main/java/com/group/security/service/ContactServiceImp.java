package com.group.security.service;

import com.group.security.entity.Contact;
import com.group.security.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImp implements ContactService {
    @Autowired
    private ContactRepository contactRepository;



    @Override
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public List<Contact> getAllContact() {
        return contactRepository.findAll();
    }



    @Override
    public List<Contact> getContactDetailsById(String contactName) {
        return contactRepository.findByContactName(contactName);
    }


}
