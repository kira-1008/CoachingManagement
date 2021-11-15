package com.example.coachingmanagement.Models;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.sql.Date;

public class Test {
    int testNumber;
    String testName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date testDate;
    Time startTime;
    Time endTime;
    int maximumMarks;
    int subjectId;

    public Test() {
    }

    public Test(int testNumber, String testName, Date testDate, Time startTime, Time endTime, int maximumMarks, int subjectId) {
        this.testNumber = testNumber;
        this.testName = testName;
        this.testDate = testDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maximumMarks = maximumMarks;
        this.subjectId = subjectId;
    }

    public int getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(int testNumber) {
        this.testNumber = testNumber;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public int getMaximumMarks() {
        return maximumMarks;
    }

    public void setMaximumMarks(int maximumMarks) {
        this.maximumMarks = maximumMarks;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "Test{" +
                "testNumber=" + testNumber +
                ", testName='" + testName + '\'' +
                ", testDate=" + testDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", maximumMarks=" + maximumMarks +
                ", subjectId=" + subjectId +
                '}';
    }
}
