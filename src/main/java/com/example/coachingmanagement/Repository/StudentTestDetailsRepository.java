package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.StudentTestDetails;

import java.util.List;

public interface StudentTestDetailsRepository {
    public List<StudentTestDetails> getRecentFiveTestsByRollNo(int roll);
    public List<StudentTestDetails> getAllTests(int roll);
    public StudentTestDetails getFromStudentAndTest(int roll,int subject,int testNo);
    public Boolean exists(int subject,int id,int roll);
    public void add(StudentTestDetails studentTestDetails);
    public void update(StudentTestDetails studentTestDetails);
}
