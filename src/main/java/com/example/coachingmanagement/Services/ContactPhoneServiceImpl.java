package com.example.coachingmanagement.Services;

import com.example.coachingmanagement.Models.ContactPhoneNumber;
import com.example.coachingmanagement.Repository.ContactPhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactPhoneServiceImpl implements  ContactPhoneService{
    ContactPhoneRepository contactPhoneRepository;

    @Autowired
    public ContactPhoneServiceImpl(ContactPhoneRepository contactPhoneRepository) {
        this.contactPhoneRepository = contactPhoneRepository;
    }

    @Override
    public Boolean add(ContactPhoneNumber contactPhoneNumber) {
       String number=contactPhoneNumber.getPhoneNumber();
       if(number!=null&&number.length()==10)
       {
           contactPhoneRepository.add(contactPhoneNumber);
           return true;
       }
       return false;
    }

    @Override
    public Boolean update(String prev,ContactPhoneNumber contactPhoneNumber) {
        String number=contactPhoneNumber.getPhoneNumber();
        if(number!=null&&number.length()==10)
        {
            contactPhoneRepository.update(prev,contactPhoneNumber);
            return true;
        }
        return false;
    }
}
