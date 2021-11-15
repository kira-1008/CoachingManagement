package com.example.coachingmanagement.Services;

import com.example.coachingmanagement.Models.User;
import com.example.coachingmanagement.Models.UserEmail;
import com.example.coachingmanagement.Models.UserPhoneNumber;

import java.util.List;

public interface UserEmailService {
    public boolean update(String username, String prev,String email);
    public boolean add(UserEmail userEmail);
}
