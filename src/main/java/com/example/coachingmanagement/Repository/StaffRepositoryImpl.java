package com.example.coachingmanagement.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StaffRepositoryImpl implements  StaffRepository{
    JdbcTemplate jdbcTemplate;

    @Autowired
    public StaffRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(int employeeId) {
        String sql="insert into staff(employee_id) values(?)";
        jdbcTemplate.update(sql,employeeId);
    }
}
