package com.group.security.service;

import com.group.security.entity.Contact;


import java.util.List;

public interface ContactService {
    Contact saveContact(Contact contact);
    List<Contact> getAllContact();

    List<Contact> getContactDetailsById(String id);

}
