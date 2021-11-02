package com.example.coachingmanagement.Models;

public class Batch {
    int batchId;
    String batchName;
    int subjectId;

    public Batch() {
    }

    public Batch(int batchId, String batchName, int subjectId) {
        this.batchId = batchId;
        this.batchName = batchName;
        this.subjectId = subjectId;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "batchId=" + batchId +
                ", batchName='" + batchName + '\'' +
                ", subjectId=" + subjectId +
                '}';
    }
}
