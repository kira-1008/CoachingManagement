package com.example.coachingmanagement.Models;

public class ContactPhoneNumber {
    String phoneNumber;
    String username;
    int contactId;

    public ContactPhoneNumber() {
    }

    public ContactPhoneNumber(String phoneNumber, String username, int contactId) {
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.contactId = contactId;
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

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    @Override
    public String toString() {
        return "ContactPhoneNumber{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", username='" + username + '\'' +
                ", contactId=" + contactId +
                '}';
    }
}
