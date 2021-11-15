package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.Batch;
import com.example.coachingmanagement.Models.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BatchRepositoryImpl implements  BatchRepository{
    JdbcTemplate jdbcTemplate;
    RowMapper<Batch> batchRowMapper;

    @Autowired
    public BatchRepositoryImpl(JdbcTemplate jdbcTemplate,RowMapper<Batch> batchRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.batchRowMapper=batchRowMapper;
    }




    @Override
    public List<Batch> getBatchesByRollNo(int rollNo) {
        String sql="select *  from batch where batch_id in(select batch_id from enrollment where roll_no=?)";
        return jdbcTemplate.query(sql, new Object[]{rollNo}, new RowMapper<Batch>() {
            @Override
            public Batch mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Batch(rs.getInt("batch_id"),rs.getString("name"),rs.getInt("subject_id"),rs.getInt("teacher_id"));
            }
        });
    }

    @Override
    public List<Batch> getAllBatches() {
       String sql="select * from batch";
       return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Batch.class));
    }

    @Override
    public Batch findById(int batchId) {
        String sql="select * from batch where batch_id="+batchId;
        List<Batch> batchList= jdbcTemplate.query(sql, new RowMapper<Batch>() {
            @Override
            public Batch mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Batch(rs.getInt("batch_id"),rs.getString("name"),rs.getInt("subject_id"),rs.getInt("teacher_id"));
            }
        });
        if(batchList.isEmpty())return null;
        return batchList.get(0);
    }

    @Override
    public List<Enrollment> listStudents(int batchId) {
        String sql="select * from enrollment where batch_id="+batchId;
        return jdbcTemplate.query(sql, new RowMapper<Enrollment>() {
            @Override
            public Enrollment mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Enrollment(rs.getInt("roll_no"),rs.getInt("batch_id"),rs.getDate("join_date"),rs.getDate("end_date"));
            }
        });
    }

    @Override
    public List<Batch> getBatchesByTeacherId(int teacherId) {
        String sql="select * from batch where teacher_id=?";
        return jdbcTemplate.query(sql,new Object[]{teacherId},batchRowMapper);
    }

    @Override
    public void add(Batch batch) {
        String sql="insert into batch(name,subject_id,teacher_id) values(?,?,?)";
        jdbcTemplate.update(sql,batch.getName(),batch.getSubjectId(),batch.getTeacherId());
    }

    @Override
    public void update(Batch batch) {
        String sql="update batch set name=?,teacher_id=?,subject_id=? where batch_id=?";
        jdbcTemplate.update(sql,batch.getName(),batch.getTeacherId(),batch.getSubjectId(),batch.getBatchId());
    }

    @Override
    public void remove(Integer BatchId) {
        String sql="delete from batch where batch_id=?";
        jdbcTemplate.update(sql,BatchId);
    }
}
