package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.Subject;

import java.util.List;

public interface SubjectRepository {
    public Subject getSubjectById(int id);
    public List<Subject> listAll();
    public void add(Subject subject);
    public void update(Subject subject);
    public void remove(int id);
}
