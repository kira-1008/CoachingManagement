package com.example.coachingmanagement.Models;

public class Subject {
    int subjectId;
    String name;
    Integer headOfDepartment;

    public Subject() {
    }

    public Subject(int subjectId, String name, int headOfDepartment) {
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

    public Integer getHeadOfDepartment() {
        return headOfDepartment;
    }

    public void setHeadOfDepartment(Integer headOfDepartment) {
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
