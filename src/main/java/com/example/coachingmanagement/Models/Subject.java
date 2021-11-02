package com.example.coachingmanagement.Models;

public class Subject {
    int subjectId;
    String name;
    String headOfDepartment;

    public Subject() {
    }

    public Subject(int subjectId, String name, String headOfDepartment) {
        this.subjectId = subjectId;
        this.name = name;
        this.headOfDepartment = headOfDepartment;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadOfDepartment() {
        return headOfDepartment;
    }

    public void setHeadOfDepartment(String headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectId=" + subjectId +
                ", name='" + name + '\'' +
                ", headOfDepartment='" + headOfDepartment + '\'' +
                '}';
    }
}
