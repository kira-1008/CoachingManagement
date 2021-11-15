package com.example.coachingmanagement.Services;

import com.example.coachingmanagement.Models.Student;

import java.util.List;

public interface StudentService {
    public void update(Student prev, Student student);
    public Boolean add(Student student);
    public List<Student> listAll();
}
