package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.User;
import com.example.coachingmanagement.Models.UserEmail;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserEmailRepositoryImpl implements UserEmailRepository{

     JdbcTemplate jdbcTemplate;

    public UserEmailRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<UserEmail> findByUsername(String username) {
        String sqlQuery="select email from user_email where username='"+username+"'";
        List<UserEmail> emails=jdbcTemplate.query(sqlQuery,new BeanPropertyRowMapper<>(UserEmail.class));
        return emails;
    }

    @Override
    public void add(User user, String email) {
        String sqlQuery="insert into user_email(username,email) values(?,?)";
        jdbcTemplate.update(sqlQuery,user.getUsername(),email);
    }

    @Override
    public void remove(User user, String email) {
        String sqlQuery="delete from user_email where email=? and username=?";
        jdbcTemplate.update(sqlQuery,email,user.getUsername());
    }

    @Override
    public void edit(User user, String prevEmail,String email) {
        String sqlQuery="update user_email set where email=? where email=? and username=?";
        jdbcTemplate.update(sqlQuery,email,prevEmail,user.getUsername());
    }
}
