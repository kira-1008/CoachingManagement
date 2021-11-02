package com.example.coachingmanagement.Models;

public class StudentTestDetails {
    int rollNumber;
    int subjectId;
    int testNumber;
    int marks;
    String remarks;

    public StudentTestDetails() {
    }

    public StudentTestDetails(int rollNumber, int subjectId, int testNumber, int marks, String remarks) {
        this.rollNumber = rollNumber;
        this.subjectId = subjectId;
        this.testNumber = testNumber;
        this.marks = marks;
        this.remarks = remarks;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(int testNumber) {
        this.testNumber = testNumber;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "StudentTestDetails{" +
                "rollNumber=" + rollNumber +
                ", subjectId=" + subjectId +
                ", testNumber=" + testNumber +
                ", marks=" + marks +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
