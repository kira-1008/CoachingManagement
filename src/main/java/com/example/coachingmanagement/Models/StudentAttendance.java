package com.example.coachingmanagement.Models;

import java.sql.Date;

public class StudentAttendance {
    int rollNumber;
    int batchId;
    Date date;
    public boolean isPresent;
    String remarks;

    public StudentAttendance() {
    }

    public StudentAttendance(int rollNumber, int batchId, Date date, boolean isPresent, String remarks) {
        this.rollNumber = rollNumber;
        this.batchId = batchId;
        this.date = date;
        this.isPresent = isPresent;
        this.remarks = remarks;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
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

    public void setIsPresent(boolean present) {
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
        return "StudentAttendance{" +
                "rollNumber=" + rollNumber +
                ", batchId=" + batchId +
                ", date=" + date +
                ", isPresent=" + isPresent +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
