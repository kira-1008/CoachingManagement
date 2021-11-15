package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.User;

import java.util.List;

public interface UserRepository {
    public User findByUsername(String username);
    public void save(User user);
    public String getPassword(String username);
    public Boolean exists(String username);
    public void activate(String username);
    public void changePassword(String username,String password);
    public User setLoginTimestamp(User user);
    public void update(User user);
    public void delete(User user);
    public List<User> getAll();
    public List<User> getNotVerifiedUsers();
}
