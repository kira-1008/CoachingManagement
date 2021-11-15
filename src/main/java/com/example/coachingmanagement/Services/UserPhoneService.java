package com.example.coachingmanagement.Services;

import com.example.coachingmanagement.Models.UserPhoneNumber;

import java.util.List;

public interface UserPhoneService {
    public Boolean update(String username,String prev,String phone);
    public Boolean add(UserPhoneNumber userPhoneNumber);
}
