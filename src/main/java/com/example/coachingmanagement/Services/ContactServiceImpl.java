package com.example.coachingmanagement.Services;

import com.example.coachingmanagement.Models.EmergencyContact;
import com.example.coachingmanagement.Models.User;
import com.example.coachingmanagement.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService{
    ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

}
