package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.EmployeeAttendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class EmployeeAttendanceRepositoryImpl implements EmployeeAttendanceRepository{
    JdbcTemplate jdbcTemplate;
    RowMapper<EmployeeAttendance> employeeAttendanceRowMapper;

    @Autowired
    public EmployeeAttendanceRepositoryImpl(JdbcTemplate jdbcTemplate, RowMapper<EmployeeAttendance> employeeAttendanceRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.employeeAttendanceRowMapper = employeeAttendanceRowMapper;
    }

    @Override
    public List<EmployeeAttendance> getFiveRecentAttendanceByEmployeeId(int id) {
        String sql="select * from employee_attendance where employee_id=? order by date desc limit 5";
        return jdbcTemplate.query(sql,new Object[]{id},employeeAttendanceRowMapper);
    }

    @Override
    public EmployeeAttendance getEmployeeAttendanceByDateAndId(int id, Date date) {
        String sql="select * from employee_attendance where employee_id=?" +
                " and date=?";
        List<EmployeeAttendance> employeeAttendances=jdbcTemplate.query(sql,new Object[]{id,date},
                employeeAttendanceRowMapper);
        if(employeeAttendances.isEmpty())return null;
        else return employeeAttendances.get(0);
    }

    @Override
    public void add(EmployeeAttendance employeeAttendance) {
        String sql="insert into employee_attendance(employee_id,date,is_present,remarks)" +
                " values(?,?,?,?)";
        jdbcTemplate.update(sql,employeeAttendance.getEmployeeId(),employeeAttendance.getDate(),employeeAttendance.getIsPresent(),employeeAttendance.getRemarks());
    }
}
