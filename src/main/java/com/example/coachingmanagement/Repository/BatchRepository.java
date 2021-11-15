package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.Batch;
import com.example.coachingmanagement.Models.Enrollment;

import java.util.List;

public interface BatchRepository {
    public List<Batch> getBatchesByRollNo(int rollNo);
    public List<Batch> getAllBatches();
    public Batch findById(int batchId);
    public List<Enrollment> listStudents(int batchId);
    public List<Batch> getBatchesByTeacherId(int teacherId);
    public void add(Batch batch);
    public void update(Batch batch);
    public void remove(Integer BatchId);
}
