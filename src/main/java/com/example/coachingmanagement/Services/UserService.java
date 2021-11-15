package com.example.coachingmanagement.Services;

import com.example.coachingmanagement.Models.User;

public interface UserService {
    public void saveUser(User user);
    public Boolean exists(User user);
    public void update(User prevUser,User user);
}
