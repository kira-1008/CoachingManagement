package com.example.coachingmanagement.Models;

public class UserPhoneNumber {
    String phoneNumber;
    String username;

    public UserPhoneNumber() {
    }

    public UserPhoneNumber(String phoneNumber, String username) {
        this.phoneNumber = phoneNumber;
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserPhoneNumber{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
