package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.ContactPhoneNumber;
import com.example.coachingmanagement.Models.EmergencyContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ContactPhoneRepositoryImpl implements ContactPhoneRepository {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public ContactPhoneRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> findNumbers(String username, int ContactId) {
        String sql="select phone_no from contact_phone where username=? and contact_id=?";
         return jdbcTemplate.query(sql, new Object[]{username, ContactId}, new RowMapper<String>() {
             @Override
             public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                 return rs.getString("phone_no");
             }
         });

    }

    @Override
    public List<ContactPhoneNumber> findContacts(String username, int ContactId) {
        String sql="select * from contact_phone where username=? and contact_id=?";
        return jdbcTemplate.query(sql, new Object[]{username, ContactId},new BeanPropertyRowMapper<>(ContactPhoneNumber.class));

    }

    @Override
    public void add(ContactPhoneNumber contactPhoneNumber) {
        String sql="insert into contact_phone(phone_no,contact_id,username) values(?,?,?)";
        jdbcTemplate.update(sql,contactPhoneNumber.getPhoneNumber(),contactPhoneNumber.getContactId(),contactPhoneNumber.getUsername());
    }

    @Override
    public void update(String prevNUmber,ContactPhoneNumber contactPhoneNumber) {
        String sql="update contact_phone set phone_no=? where contact_id=? and username=? and phone_no=?";
        jdbcTemplate.update(sql,contactPhoneNumber.getPhoneNumber(),contactPhoneNumber.getContactId(),contactPhoneNumber.getUsername(),prevNUmber);

    }

    @Override
    public void delete(ContactPhoneNumber contactPhoneNumber) {
        String sql="delete from contact_phone where phone_no=? and contact_id=? and username=?";
        jdbcTemplate.update(sql,contactPhoneNumber.getPhoneNumber(),contactPhoneNumber.getContactId(),contactPhoneNumber.getUsername());
    }
}
