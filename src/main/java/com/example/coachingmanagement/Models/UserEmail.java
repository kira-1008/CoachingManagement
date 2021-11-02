package com.example.coachingmanagement.Models;

public class UserEmail {
    String email;
    String username;

    public UserEmail() {
    }

    public UserEmail(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserEmail{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
