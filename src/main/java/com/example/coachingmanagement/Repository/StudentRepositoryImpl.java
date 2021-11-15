package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.Batch;
import com.example.coachingmanagement.Models.Enrollment;
import com.example.coachingmanagement.Models.Student;
import com.example.coachingmanagement.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StudentRepositoryImpl implements  StudentRepository{
   JdbcTemplate jdbcTemplate;
   RowMapper<Student> studentRowMapper;


   RowMapper<User> rowMapperUser=new RowMapper<User>() {
       @Override
       public User mapRow(ResultSet rs, int rowNum) throws SQLException {

           return new User(rs.getString("username"),
                   rs.getString("first_name"),
                   rs.getString("middle_name"),
                   rs.getString("last_name"),
                   rs.getString("password"),
                   rs.getString("role"),rs.getBoolean("is_active"),rs.getDate("date_created"),rs.getDate("last_login_date"),rs.getTime("last_login_time"));
       }
   };



   @Autowired
    public StudentRepositoryImpl(JdbcTemplate jdbcTemplate,RowMapper<Student> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentRowMapper=rowMapper;
    }

    @Override
    public Student findByUsername(String username) {
        String sql="select * from student where username='"+username+"'";
        Student student=jdbcTemplate.queryForObject(sql,studentRowMapper);
        return student;
    }

    @Override
    public Student findByRoll(int rollNo) {
        String sql="select * from student where roll_no="+rollNo;
        Student student=jdbcTemplate.queryForObject(sql,studentRowMapper);
        return student;
    }

    @Override
    public List<Enrollment> getEnrollment(int rollNo) {
        String sql="select * from enrollment where roll_no="+rollNo;
        List<Enrollment> enrollmentList=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Enrollment.class));
        return enrollmentList;
   }

    @Override
    public List<Batch> getBatches(int rollNo) {
        String sql="select * from batch where batch_id in (select batch_id from enrollment where roll_no=?)";
        List<Batch> batchList=jdbcTemplate.query(sql, new Object[]{rollNo}, new RowMapper<Batch>() {
            @Override
            public Batch mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Batch(rs.getInt("batch_id"),rs.getString("name"),rs.getInt("subject_id"),rs.getInt("teacher_id"));
            }
        });
        return batchList;
    }

    @Override
    public List<Student> getAll() {
        String sql="select * from student";
        List<Student> students=jdbcTemplate.query(sql,studentRowMapper);
        return students;
    }

    @Override
    public void update(Student student) {

        String sql="update student set class=?,DOB=?,address=?,pincode=?,gender=? where username=?";
        jdbcTemplate.update(sql,student.getStandard(),student.getDateOfBirth(),student.getAddress(),student.getPincode(),student.getGender(),student.getUsername());
    }

    @Override
    public User userFromRollNo(int rollNo) {

       String sql="select * from user,student where user.username=student.username and student.roll_no="+rollNo;
        List<User> user= jdbcTemplate.query(sql,rowMapperUser);
        if(user.isEmpty())return null;
        else return user.get(0);
    }

    @Override
    public void add(Student student) {
        String sql="insert into student(class,DOB,address,pincode,gender,username) values(?,?,?,?,?,?)";
        jdbcTemplate.update(sql,student.getStandard(),student.getDateOfBirth(),student.getAddress(),student.getPincode(),student.getGender(),student.getUsername());
    }

}
