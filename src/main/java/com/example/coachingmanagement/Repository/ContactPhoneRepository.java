package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.ContactPhoneNumber;
import com.example.coachingmanagement.Models.EmergencyContact;

import java.util.List;

public interface ContactPhoneRepository {
    public List<String> findNumbers(String username, int ContactId);
    public List<ContactPhoneNumber> findContacts(String username, int ContactId);
    public void add(ContactPhoneNumber contactPhoneNumber);
    public void update(String preNumber,ContactPhoneNumber contactPhoneNumber);
    public void delete(ContactPhoneNumber contactPhoneNumber);
}
