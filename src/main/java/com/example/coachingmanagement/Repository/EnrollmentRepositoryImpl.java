package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnrollmentRepositoryImpl implements  EnrollmentRepository{
   JdbcTemplate jdbcTemplate;
   RowMapper<Student> studentRowMapper;

   @Autowired
    public EnrollmentRepositoryImpl(JdbcTemplate jdbcTemplate,RowMapper<Student> studentRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentRowMapper=studentRowMapper;
    }

    @Override
    public void add(int batchId, int rollNumber) {
        java.util.Date date=new java.util.Date();
        java.sql.Date sqlDate=new java.sql.Date(date.getTime());
        String sql="insert into enrollment(roll_no,batch_id,join_date) values(?,?,?)";
        jdbcTemplate.update(sql,rollNumber,batchId,sqlDate);
    }

    @Override
    public Boolean exists(int roll, int batchId) {
        String sql="select count(*) from enrollment where roll_no=? and batch_id=?";

        Integer count=jdbcTemplate.queryForObject(sql,new Object[]{roll,batchId},Integer.class);
        return (count>0);
    }

    @Override
    public List<Student> getStudentsByBatch(int id) {
        String sql="select * from student where roll_no in(select roll_no from enrollment where batch_id=?)";
        List<Student> students=jdbcTemplate.query(sql,new Object[]{id},studentRowMapper);
        return students;
    }
}
