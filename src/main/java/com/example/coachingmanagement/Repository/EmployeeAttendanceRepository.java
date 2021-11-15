package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.EmployeeAttendance;

import java.sql.Date;
import java.util.List;

public interface EmployeeAttendanceRepository {
    public List<EmployeeAttendance> getFiveRecentAttendanceByEmployeeId(int id);
    public EmployeeAttendance getEmployeeAttendanceByDateAndId(int id, Date date);
    public void add(EmployeeAttendance employeeAttendance);
}
