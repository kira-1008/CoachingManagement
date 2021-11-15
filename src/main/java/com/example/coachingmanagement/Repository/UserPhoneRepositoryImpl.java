package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.User;
import com.example.coachingmanagement.Models.UserPhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserPhoneRepositoryImpl implements UserPhoneRepository {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public UserPhoneRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<UserPhoneNumber> findByUsernameContacts(String username) {
        String sqlQuery="select * from user_phone where username='"+username+"'";
        List<UserPhoneNumber> phoneNumbers=jdbcTemplate.query(sqlQuery,new BeanPropertyRowMapper<>(UserPhoneNumber.class));
        return phoneNumbers;
    }

    @Override
    public void add(String username, String number) {
        String sqlQuery="insert into user_phone(username,phone_no) values(?,?)";
        jdbcTemplate.update(sqlQuery,username,number);
    }

    @Override
    public void remove(String username, String number) {
        String sqlQuery="delete from user_phone where phone_no=? and username=?";
        jdbcTemplate.update(sqlQuery,number,username);
    }

    @Override
    public void edit(String username,String prevNumber, String number) {
        String sqlQuery="update user_phone set phone_no=? where phone_no=? and username=?";
        jdbcTemplate.update(sqlQuery,number,prevNumber,username);
    }

    @Override
    public List<String> findByUsernameNumbers(String username) {
        String sqlQuery="select phone_no from user_phone where username='"+username+"'";
        List<String> phoneNumbers=jdbcTemplate.query(sqlQuery, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("phone_no");
            }
        });
        return phoneNumbers;
    }
}
