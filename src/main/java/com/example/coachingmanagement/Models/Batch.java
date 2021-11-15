package com.example.coachingmanagement.Models;

public class Batch {
    Integer batchId;
    String name;
    Integer subjectId;
    Integer teacherId;

    public Batch(Integer batchId, String name, Integer subjectId, Integer teacherId) {
        this.batchId = batchId;
        this.name = name;
        this.subjectId = subjectId;
        this.teacherId = teacherId;
    }

    public Batch() {
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "batchId=" + batchId +
                ", name='" + name + '\'' +
                ", subjectId=" + subjectId +
                ", teacherId=" + teacherId +
                '}';
    }
}
