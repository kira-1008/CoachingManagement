package com.example.coachingmanagement.Models;

public class PractiseSheet {
    int sheetNumber;
    String topic;
    String description;
    String name;
    int subjectId;

    public PractiseSheet() {
    }

    public PractiseSheet(int sheetNumber, String topic, String description, String name, int subjectId) {
        this.sheetNumber = sheetNumber;
        this.topic = topic;
        this.description = description;
        this.name = name;
        this.subjectId = subjectId;
    }

    public int getSheetNumber() {
        return sheetNumber;
    }

    public void setSheetNumber(int sheetNumber) {
        this.sheetNumber = sheetNumber;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "PractiseSheet{" +
                "sheetNumber=" + sheetNumber +
                ", topic='" + topic + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", subjectId=" + subjectId +
                '}';
    }
}
