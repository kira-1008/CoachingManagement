package com.example.coachingmanagement.Utility;

import com.example.coachingmanagement.Models.StudentAttendance;
import com.example.coachingmanagement.Models.StudentTestDetails;

import java.util.List;

public class StudentTestDetailsDTO {
    public List<StudentTestDetails> studentTestDetails;

    public StudentTestDetailsDTO() {
    }

    public StudentTestDetailsDTO(List<StudentTestDetails> studentTestDetails) {
        this.studentTestDetails = studentTestDetails;
    }

    public void add(StudentTestDetails studentTestDetails)
    {
        this.studentTestDetails.add(studentTestDetails);
    }

    public List<StudentTestDetails> getStudentTestDetails() {
        return studentTestDetails;
    }

    public void setStudentTestDetails(List<StudentTestDetails> studentTestDetails) {
        this.studentTestDetails = studentTestDetails;
    }
}
