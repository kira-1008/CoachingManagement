package com.example.coachingmanagement.Services;

import com.example.coachingmanagement.Repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentServiceImpl implements  EnrollmentService{
    EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public Boolean add(int rollNo, int batchId) {

        Boolean exists=enrollmentRepository.exists(rollNo,batchId);

        if(exists)
        {
            return false;
        }
        enrollmentRepository.add(batchId,rollNo);
        return true;
    }
}
