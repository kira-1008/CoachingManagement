package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.User;
import com.example.coachingmanagement.Models.UserPhoneNumber;

import java.util.List;

public interface UserPhoneRepository {
    public List<UserPhoneNumber> findByUsername(String username);
    public void add(User user,String number);
    public void remove(User user,String number);
    public void edit(User user,String prevNumber,String number);
}
