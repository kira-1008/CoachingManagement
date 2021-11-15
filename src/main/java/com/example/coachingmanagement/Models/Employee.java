package com.example.coachingmanagement.Models;

import java.sql.Date;

public class Employee {
    int employeeId;
    Date joinDate;
    Date endDate;
    int salary;
    String bankName;
    String ifscCode;
    String bankAccountNumber;
    String PFNumber;
    Date dateOfBirth;
    String address;
    int pincode;
    String gender;
    String username;

    public Employee() {
    }

    public Employee(int employeeId, Date joinDate, Date endDate, int salary, String bankName, String ifscCode, String bankAccountNumber, String PFNumber, Date dateOfBirth, String address, int pincode, String gender, String username) {
        this.employeeId = employeeId;
        this.joinDate = joinDate;
        this.endDate = endDate;
        this.salary = salary;
        this.bankName = bankName;
        this.ifscCode = ifscCode;
        this.bankAccountNumber = bankAccountNumber;
        this.PFNumber = PFNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.pincode = pincode;
        this.gender = gender;
        this.username = username;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getPFNumber() {
        return PFNumber;
    }

    public void setPFNumber(String PFNumber) {
        this.PFNumber = PFNumber;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", joinDate=" + joinDate +
                ", endDate=" + endDate +
                ", salary=" + salary +
                ", bankName='" + bankName + '\'' +
                ", ifscCode='" + ifscCode + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", PFNumber='" + PFNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", pincode=" + pincode +
                ", gender=" + gender +
                ", username='" + username + '\'' +
                '}';
    }
}
