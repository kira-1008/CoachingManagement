package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestRepositoryImpl implements  TestRepository{
    JdbcTemplate jdbcTemplate;
    RowMapper<Test> rowMapper;

    @Autowired
    public TestRepositoryImpl(JdbcTemplate jdbcTemplate, RowMapper<Test> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    public Test findTestById(int id,int subject) {
        String sql="select * from test where test_no=? and subject_id=?";
        return jdbcTemplate.queryForObject(sql,new Object[]{id,subject},rowMapper);
    }

    @Override
    public List<Test> listAll() {
        String sql="select * from test";
        return jdbcTemplate.query(sql,rowMapper);
    }

    @Override
    public void add(Test test) {
        String sql="insert into test(test_no,subject_id,test_name,test_date,start_time,end_time,max_marks) values(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,test.getTestNumber(),test.getSubjectId(),test.getTestName(),test.getTestDate(),test.getStartTime(),test.getEndTime(),test.getMaximumMarks());

    }

    @Override
    public Boolean exists(Test test) {
        String sql="select count(*) from test where test_no=? and subject_id=?";
        Integer count=jdbcTemplate.queryForObject(sql,new Object[]{test.getTestNumber(),test.getSubjectId()},Integer.class);
        return (count>0);
    }

    @Override
    public void update(Test test) {
        String sql="update test set test_name=?,test_date=?,start_time=?,end_time=?,max_marks=? where test_no=? and subject_id=?";
        jdbcTemplate.update(sql,test.getTestName(),test.getTestDate(),test.getStartTime(),test.getEndTime(),test.getMaximumMarks(),test.getTestNumber(), test.getSubjectId());

    }

    @Override
    public void remove(int subjectId, int testNo) {
        String sql="delete from test where subject_id=? and test_no=?";
        jdbcTemplate.update(sql,subjectId,testNo);
    }
}
