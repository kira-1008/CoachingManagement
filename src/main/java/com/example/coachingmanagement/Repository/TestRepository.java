package com.example.coachingmanagement.Repository;


import com.example.coachingmanagement.Models.Test;

import java.util.List;


public interface TestRepository {
    public Test findTestById(int no,int subjectId);
    public List<Test> listAll();
    public void add(Test test);
    public Boolean exists(Test test);
    public void update(Test test);
    public void remove(int subjectId,int testNo);
}
