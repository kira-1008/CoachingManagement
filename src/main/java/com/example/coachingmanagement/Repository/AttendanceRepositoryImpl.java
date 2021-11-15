package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.StudentAttendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class AttendanceRepositoryImpl implements  AttendanceRepository{
    JdbcTemplate jdbcTemplate;
    RowMapper<StudentAttendance> studentAttendanceRowMapper;

    @Autowired
    public AttendanceRepositoryImpl(JdbcTemplate jdbcTemplate,RowMapper<StudentAttendance> studentAttendanceRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentAttendanceRowMapper=studentAttendanceRowMapper;
    }

    @Override
    public StudentAttendance getAttendanceByDateAndRoll(int roll, Date date) {
        String sql="select * from student_attendance where roll_no=? and date=?";
        List<StudentAttendance> studentAttendanceList=  jdbcTemplate.query(sql,new Object[]{roll,date},studentAttendanceRowMapper);
        if(studentAttendanceList.isEmpty())return null;
        else return studentAttendanceList.get(0);
    }

    @Override
    public Boolean exists(int roll, int batch, Date date) {
        String sql="select count(*) from student_attendance where roll_no=? and batch_id=? and date=?";
        Integer count=jdbcTemplate.queryForObject(sql,new Object[]{roll,batch,date},Integer.class);
        return (count>0);
    }

    @Override
    public void update(StudentAttendance studentAttendance) {
        String sql="update student_attendance set is_present=?,remarks=? where roll_no=? and batch_id=? and date=?";
        jdbcTemplate.update(sql,studentAttendance.getIsPresent(),studentAttendance.getRemarks(),studentAttendance.getRollNumber(),studentAttendance.getBatchId(),studentAttendance.getDate());
    }

    @Override
    public void add(StudentAttendance studentAttendance) {
        String sql="insert into student_attendance(is_present,remarks,roll_no,batch_id,date) values(?,?,?,?,?)";
        jdbcTemplate.update(sql,studentAttendance.getIsPresent(),studentAttendance.getRemarks(),studentAttendance.getRollNumber(),studentAttendance.getBatchId(),studentAttendance.getDate());
    }
}
