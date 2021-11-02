package com.example.coachingmanagement.Models;

import java.util.Date;

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

    public Boolean getPresent() {
        return isPresent;
    }

    public void setPresent(Boolean present) {
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
