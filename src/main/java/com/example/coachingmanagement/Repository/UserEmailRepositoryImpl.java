package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.User;
import com.example.coachingmanagement.Models.UserEmail;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserEmailRepositoryImpl implements UserEmailRepository{

     JdbcTemplate jdbcTemplate;

    public UserEmailRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> findByUsernameString(String username) {
        String sqlQuery="select email from user_email where username='"+username+"'";
        List<String> emails=jdbcTemplate.query(sqlQuery, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("email");
            }
        });
        return emails;
    }

    @Override
    public void add(String username, String email) {
        String sqlQuery="insert into user_email(username,email) values(?,?)";
        jdbcTemplate.update(sqlQuery,username,email);
    }

    @Override
    public void remove(String username, String email) {
        String sqlQuery="delete from user_email where email=? and username=?";
        jdbcTemplate.update(sqlQuery,email,username);
    }

    @Override
    public void edit(String username, String prevEmail,String email) {
        String sqlQuery="update user_email set email=? where email=? and username=?";
        jdbcTemplate.update(sqlQuery,email,prevEmail,username);
    }
}
