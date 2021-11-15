package com.example.coachingmanagement.Utility;

import com.example.coachingmanagement.Models.StudentAttendance;
import com.example.coachingmanagement.Models.StudentTestDetails;

import java.util.List;

public class StudentAttendanceDTO {
    public List<StudentAttendance> studentAttendanceList;

    public StudentAttendanceDTO() {
    }

    public StudentAttendanceDTO(List<StudentAttendance> studentAttendanceList) {
        this.studentAttendanceList = studentAttendanceList;
    }

    public List<StudentAttendance> getStudentAttendanceList() {
        return studentAttendanceList;
    }

    public void setStudentAttendanceList(List<StudentAttendance> studentAttendanceList) {
        this.studentAttendanceList = studentAttendanceList;
    }
}
