package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.User;
import com.example.coachingmanagement.Models.UserEmail;


import java.util.List;

public interface UserEmailRepository {
    public List<String> findByUsernameString(String username);
    public void add(String username, String email);
    public void remove(String username,String email);
    public void edit(String username,String prevEmail,String email);
}
