package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.User;
import com.example.coachingmanagement.Models.UserPhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserPhoneRepositoryImpl implements UserPhoneRepository {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public UserPhoneRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<UserPhoneNumber> findByUsername(String username) {
        String sqlQuery="select phone_no from user_phone where username='"+username+"'";
        List<UserPhoneNumber> phoneNumbers=jdbcTemplate.query(sqlQuery,new BeanPropertyRowMapper<>(UserPhoneNumber.class));
        return phoneNumbers;
    }

    @Override
    public void add(User user, String number) {
        String sqlQuery="insert into user_phone(username,phone_no) values(?,?)";
        jdbcTemplate.update(sqlQuery,user.getUsername(),number);
    }

    @Override
    public void remove(User user, String number) {
        String sqlQuery="delete from user_phone where phone_no=? and username=?";
        jdbcTemplate.update(sqlQuery,number,user.getUsername());
    }

    @Override
    public void edit(User user,String prevNumber, String number) {
        String sqlQuery="update user_phone set where phone_no=? where phone_no=? and username=?";
        jdbcTemplate.update(sqlQuery,number,prevNumber,user.getUsername());
    }
}
