package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SubjectRepositoryImpl implements  SubjectRepository{
    JdbcTemplate jdbcTemplate;
    RowMapper<Subject> rowMapper;

    @Autowired
    public SubjectRepositoryImpl(JdbcTemplate jdbcTemplate,RowMapper<Subject> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper=rowMapper;
    }

    @Override
    public Subject getSubjectById(int id) {
        String sql="select * from subject where subject_id="+id;
        List<Subject> subjects=jdbcTemplate.query(sql,rowMapper);
        if(subjects.isEmpty())return null;
        return subjects.get(0);
    }

    @Override
    public List<Subject> listAll() {
        String sql="select * from subject";
        List<Subject> subjects=jdbcTemplate.query(sql,rowMapper);
        return subjects;
    }

    @Override
    public void add(Subject subject) {
        String sql="insert into subject(name) values(?)";
        jdbcTemplate.update(sql,subject.getName());
    }

    @Override
    public void update(Subject subject) {
        String sql="update  subject set name=? , hod=? where subject_id=?";
        jdbcTemplate.update(sql,subject.getName(),subject.getHeadOfDepartment(),subject.getSubjectId());
    }

    @Override
    public void remove(int id) {
        String sql="delete from subject where subject_id=?";
        jdbcTemplate.update(sql,id);
    }
}
