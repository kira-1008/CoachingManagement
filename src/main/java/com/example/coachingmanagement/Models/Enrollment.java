package com.example.coachingmanagement.Models;

import java.util.Date;

public class Enrollment {
    int rollNumber;
    int batchId;
    Date joiningDate;
    Date endDate;

    public Enrollment() {
    }

    public Enrollment(int rollNumber, int batchId, Date joiningDate, Date endDate) {
        this.rollNumber = rollNumber;
        this.batchId = batchId;
        this.joiningDate = joiningDate;
        this.endDate = endDate;
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

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "rollNumber=" + rollNumber +
                ", batchId=" + batchId +
                ", joiningDate=" + joiningDate +
                ", endDate=" + endDate +
                '}';
    }
}
