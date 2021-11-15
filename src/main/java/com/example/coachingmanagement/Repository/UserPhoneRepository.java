package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.User;
import com.example.coachingmanagement.Models.UserPhoneNumber;

import java.util.List;

public interface UserPhoneRepository {
    public List<UserPhoneNumber> findByUsernameContacts(String username);
    public void add(String username,String number);
    public void remove(String username,String number);
    public void edit(String username,String prevNumber,String number);
    public List<String> findByUsernameNumbers(String username);
}
