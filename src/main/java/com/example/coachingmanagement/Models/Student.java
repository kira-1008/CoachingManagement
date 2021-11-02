package com.example.coachingmanagement.Models;

import java.util.Date;

public class Student {
    int rollNumber;
    int standard;
    Date dateOfBirth;
    String address;
    int pincode;
    Character gender;
    String username;

    public Student() {
    }

    public Student(int rollNumber, int standard, Date dateOfBirth, String address, int pincode, Character gender, String username) {
        this.rollNumber = rollNumber;
        this.standard = standard;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.pincode = pincode;
        this.gender = gender;
        this.username = username;
    }

    @Override
    public String toString() {
        return "Student{" +
                "rollNumber=" + rollNumber +
                ", standard=" + standard +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", pincode=" + pincode +
                ", gender=" + gender +
                ", username='" + username + '\'' +
                '}';
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public int getStandard() {
        return standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
