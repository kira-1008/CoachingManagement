package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.Teacher;
import com.example.coachingmanagement.Models.User;

import java.util.List;

public interface TeacherRepository {
    public Teacher getTeacherFromId(Integer Id);
    public User getTeacherUserFromBatch(int BatchId);
    public void add(int employeeId,int subjectId);
    public Teacher getFromEmployeeId(Integer id);
    public void update(Teacher teacher);
}
