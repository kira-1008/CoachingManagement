package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.EmergencyContact;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ContactRepositoryImpl implements  ContactRepository{
    JdbcTemplate jdbcTemplate;

    public ContactRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<EmergencyContact> findByUsername(String username) {
        String sql="select * from emergency_contact where username='"+username+"'";
        List<EmergencyContact> contactList=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(EmergencyContact.class));
        return contactList;
    }

    @Override
    public EmergencyContact findByUserAndId(String username, int id) {
       String sql="select * from emergency_contact where username=? and contact_id=?";
       EmergencyContact contact= jdbcTemplate.queryForObject(sql, new Object[]{username, id}, new RowMapper<EmergencyContact>() {
           @Override
           public EmergencyContact mapRow(ResultSet rs, int rowNum) throws SQLException {
               return new EmergencyContact(rs.getInt("contact_id"),rs.getString("first_name"),
                       rs.getString("middle_name"),rs.getString("last_name")
                       ,rs.getString("address"),rs.getInt("pincode"),rs.getString("relation"),
                       rs.getString("username"));
           }
       });
       System.out.println(contact);
       return  contact;
    }

    @Override
    public void update(EmergencyContact contact) {
        String sql="update emergency_contact set first_name=?,middle_name=?,last_name=?,address=?,pincode=?,relation=? where username=? and contact_id=?";
        jdbcTemplate.update(sql,contact.getFirstName(),contact.getMiddleName(),contact.getLastName(),contact.getAddress(),contact.getPincode(),contact.getRelation(),contact.getUsername(),contact.getContactId());

    }

    @Override
    public void delete(String username,int contactId)
    {
        String sql="delete from emergency_contact where username=? and contact_id=?";
        jdbcTemplate.update(sql,username,contactId);
    }

    @Override
    public void add(EmergencyContact contact,String username)
    {
        String sql="insert into emergency_contact(first_name,middle_name,last_name,address,pincode,relation,username) values(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,contact.getFirstName(),contact.getMiddleName(),contact.getLastName(),contact.getAddress(),contact.getPincode(),contact.getRelation(),username);

    }
}
