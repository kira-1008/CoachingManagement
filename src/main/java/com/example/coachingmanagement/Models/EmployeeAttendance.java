package com.example.coachingmanagement.Models;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

public class EmployeeAttendance {
    int employeeId;
    Date date;
    Boolean isPresent;
    String remarks;

    public EmployeeAttendance() {
    }

    public EmployeeAttendance(int employeeId, Date date, Boolean isPresent, String remarks) {
        this.employeeId = employeeId;
        this.date = date;
        this.isPresent = isPresent;
        this.remarks = remarks;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(Boolean present) {
        isPresent = present;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "EmployeeAttendance{" +
                "employeeId=" + employeeId +
                ", date=" + date +
                ", isPresent=" + isPresent +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
