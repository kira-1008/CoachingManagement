package com.example.coachingmanagement.Repository;


import com.example.coachingmanagement.Models.StudentAttendance;

import java.sql.Date;

public interface AttendanceRepository {
    public StudentAttendance getAttendanceByDateAndRoll(int roll, Date date);
    public Boolean exists(int roll,int batch,Date date);
    public void update(StudentAttendance studentAttendance);
    public void add(StudentAttendance studentAttendance);
}
