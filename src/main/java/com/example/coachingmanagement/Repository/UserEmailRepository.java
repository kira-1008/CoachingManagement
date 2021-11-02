package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.User;
import com.example.coachingmanagement.Models.UserEmail;


import java.util.List;

public interface UserEmailRepository {
    public List<UserEmail> findByUsername(String username);
    public void add(User user, String email);
    public void remove(User user,String email);
    public void edit(User user,String prevEmail,String email);
}
