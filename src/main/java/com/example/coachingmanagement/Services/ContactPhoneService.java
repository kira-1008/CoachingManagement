package com.example.coachingmanagement.Services;

import com.example.coachingmanagement.Models.ContactPhoneNumber;

public interface ContactPhoneService {
    public Boolean add(ContactPhoneNumber contactPhoneNumber);
    public Boolean update(String prev,ContactPhoneNumber contactPhoneNumber);
}
