package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.PractiseSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PractiseRepositoryImpl implements  PractiseRepository{
 JdbcTemplate jdbcTemplate;

 @Autowired
    public PractiseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PractiseSheet> getListbySubject(int subjectId) {
        String sql="select * from practise_sheet where subject_id=?";
        List<PractiseSheet> list=jdbcTemplate.query(sql, new Object[]{subjectId}, new RowMapper<PractiseSheet>() {
            @Override
            public PractiseSheet mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new PractiseSheet(rs.getInt("sheet_no"),rs.getString("topic"),rs.getString("description"),rs.getString("name"),rs.getInt("subject_id"));
            }
        });
        return list;
    }
}
