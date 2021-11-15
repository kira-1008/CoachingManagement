package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.EmergencyContact;

import java.util.List;

public interface ContactRepository {
    public List<EmergencyContact> findByUsername(String username);
    public EmergencyContact findByUserAndId(String username,int id);
    public void update(EmergencyContact contact);
    public void delete(String username,int contactId);
    public void add(EmergencyContact contact,String username);
}
