package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.Student;

import java.util.List;

public interface EnrollmentRepository {
    public void add(int batchId,int rollNumber);
    public Boolean exists(int roll,int batchId);
    public List<Student> getStudentsByBatch(int id);

}
