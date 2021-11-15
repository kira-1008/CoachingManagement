package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.PractiseSheet;

import java.util.List;

public interface PractiseRepository {
    public List<PractiseSheet> getListbySubject(int subjectId);
}
