package com.example.coachingmanagement.Utility;

import com.example.coachingmanagement.Models.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RowMappers {

    @Bean
    public RowMapper<User> getUserRowMapper()
    {
        RowMapper<User> user=new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user=new User();
                user.setActive(rs.getBoolean("is_active"));
                user.setCreationDate(rs.getDate("date_created"));
                user.setUsername(rs.getString("username"));
                user.setFirstName(rs.getString("first_name"));
                user.setMiddleName(rs.getString("middle_name"));
                user.setLastName(rs.getString("last_name"));
                user.setRole(rs.getString("role"));
                user.setLastLoginDate(rs.getDate("last_login_date"));
                user.setLastLoginTime(rs.getTime("last_login_time"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        };
        return user;
    }

    @Bean
    public RowMapper<StudentAttendance> getStudentAttendanceMapper()
    {
        RowMapper<StudentAttendance> rowMapper=new RowMapper<StudentAttendance>() {
            @Override
            public StudentAttendance mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new StudentAttendance(rs.getInt("roll_no"),rs.getInt("batch_id"),rs.getDate("date"),rs.getBoolean("is_present"),rs.getString("remarks"));
            }
        };
        return rowMapper;
    }

    @Bean
    public RowMapper<Test> getTestRowMapper(){
        RowMapper<Test> rowMapper=new RowMapper<Test>() {
            @Override
            public Test mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Test(rs.getInt("test_no"),rs.getString("test_name"),rs.getDate("test_date"),rs.getTime("start_time"),
                        rs.getTime("end_time"),rs.getInt("max_marks"),rs.getInt("subject_id"));
            }
        };
        return rowMapper;
    }

    @Bean
    public RowMapper<StudentTestDetails> getStudentTestRowMapper(){
        RowMapper<StudentTestDetails> rowMapper=new RowMapper<StudentTestDetails>() {
            @Override
            public StudentTestDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new StudentTestDetails(rs.getInt("roll_no"),rs.getInt("subject_id"),
                        rs.getInt("test_no"),rs.getInt("marks"),rs.getString("remarks"));
            }
        };
        return rowMapper;
    }

    @Bean
    public RowMapper<Student> getStudentRowMapper() {
        RowMapper<Student> rowMapper = new RowMapper<Student>() {
            @Override
            public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
                Student student = new Student();
                student.setRollNumber(rs.getInt("roll_no"));
                student.setStandard(rs.getInt("class"));
                student.setDateOfBirth(rs.getDate("DOB"));
                student.setAddress(rs.getString("address"));
                student.setPincode(rs.getInt("pincode"));
                student.setGender(String.valueOf(rs.getString("gender").charAt(0)));
                student.setUsername(rs.getString("username"));
                return student;
            }
        };
        return rowMapper;
    }

    @Bean
    public RowMapper<Employee> employeeRowMapper()
    {
        RowMapper<Employee> rowMapper=new RowMapper<Employee>() {
            @Override
            public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Employee(rs.getInt("employee_id"),rs.getDate("join_date"),rs.getDate("end_date"),rs.getInt("salary"),rs.getString("bank_name"),
                        rs.getString("ifsc_code"),rs.getString("bank_account_no"),rs.getString("pf_no"),rs.getDate("DOB"),
                        rs.getString("address"),rs.getInt("pincode"),rs.getString("gender"),rs.getString("username"));
            }
        } ;
        return rowMapper;
    }

    @Bean
    public RowMapper<Batch> batchRowMapper()
    {
        RowMapper<Batch> rowMapper=new RowMapper<Batch>() {
            @Override
            public Batch mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Batch(rs.getInt("batch_id"),rs.getString("name"),rs.getInt("subject_id"), rs.getInt("teacher_id"));
            }
        };

        return rowMapper;
    }

    @Bean
    public RowMapper<EmployeeAttendance> employeeAttendanceRowMapper()
    {
        RowMapper<EmployeeAttendance> rowMapper=new RowMapper<EmployeeAttendance>() {
            @Override
            public EmployeeAttendance mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new EmployeeAttendance(rs.getInt("employee_id"),rs.getDate("date"),rs.getBoolean("is_present"),rs.getString("remarks"));
            }
        };

        return rowMapper;
    }

    @Bean
    public RowMapper<Payroll> payrollRowMapper()
    {
        RowMapper<Payroll> rowMapper=new RowMapper<Payroll>() {
            @Override
            public Payroll mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Payroll(rs.getInt("payment_reference_no"),rs.getString("payment_mode"),
                        rs.getInt("amount"),rs.getDate("date"),rs.getInt("employee_id"));
            }
        };

        return rowMapper;
    }

    @Bean
    public RowMapper<Subject> subjectRowMapper()
    {
        RowMapper<Subject> rowMapper=new RowMapper<Subject>() {
            @Override
            public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Subject(rs.getInt("subject_id"),rs.getString("name"),rs.getInt("hod"));
            }
        };
        return rowMapper;
    }

}
