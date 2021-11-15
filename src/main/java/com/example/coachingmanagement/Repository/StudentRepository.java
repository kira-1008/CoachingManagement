package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.Batch;
import com.example.coachingmanagement.Models.Enrollment;
import com.example.coachingmanagement.Models.Student;
import com.example.coachingmanagement.Models.User;

import java.util.List;

public interface StudentRepository {
    public Student findByUsername(String username);
    public Student findByRoll(int rollNo);
    public List<Batch> getBatches(int rollNo);
    public List<Enrollment> getEnrollment(int rollNo);
    public List<Student> getAll();
    public void update(Student student);
    public User userFromRollNo(int rollNo);
    public void add(Student student);
}
