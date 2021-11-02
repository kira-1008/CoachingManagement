package com.example.coachingmanagement.Models;

public class Teacher {
    int teacherId;
    int employeeId;
    int subjectId;

    public Teacher() {
    }

    public Teacher(int teacherId, int employeeId, int subjectId) {
        this.teacherId = teacherId;
        this.employeeId = employeeId;
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", employeeId=" + employeeId +
                ", subjectId=" + subjectId +
                '}';
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
}
