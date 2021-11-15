package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.Employee;
import com.example.coachingmanagement.Models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class EmployeeRepositoryImpl implements  EmployeeRepository{
    JdbcTemplate jdbcTemplate;
    RowMapper<Employee> employeeRowMapper;

    @Autowired
    public EmployeeRepositoryImpl(JdbcTemplate jdbcTemplate,RowMapper<Employee> employeeRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.employeeRowMapper=employeeRowMapper;
    }

    @Override
    public Integer add(Employee employee) {
       String sql="insert into employee(join_date,salary,bank_name,ifsc_code,bank_account_no,pf_no,DOB,address,pincode,gender,username)"
               +" values(?,?,?,?,?,?,?,?,?,?,?)";
       jdbcTemplate.update(sql, employee.getJoinDate(),employee.getSalary(),employee.getBankName(),employee.getIfscCode(),employee.getBankAccountNumber(),
        employee.getPFNumber(),employee.getDateOfBirth(),employee.getAddress(),employee.getPincode(),employee.getGender(),employee.getUsername());
       String sql2="select employee_id from employee where username='"+employee.getUsername()+"'";
       List<Integer> ids= jdbcTemplate.queryForList(sql2,Integer.class);
       return ids.get(0);
    }

    @Override
    public List<Employee> getAll() {
        String sql="select * from employee";
        return jdbcTemplate.query(sql,employeeRowMapper);
    }

    @Override
    public List<Employee> getAllStaff() {
        String sql="select employee.* from employee,staff where employee.employee_id=staff.employee_id";
        return jdbcTemplate.query(sql,employeeRowMapper);
    }

    @Override
    public List<Employee> getAllTeacher() {
        String sql="select employee.* from employee,teacher where employee.employee_id=teacher.employee_id";
        return jdbcTemplate.query(sql,employeeRowMapper);
    }

    @Override
    public Employee getEmployeeById(int id) {
        String sql="select * from employee where employee_id=?";
        List<Employee> employees=jdbcTemplate.query(sql,new Object[]{id},employeeRowMapper);
        if(employees.isEmpty())return null;
        else return employees.get(0);
    }

    @Override
    public Employee getFromUsername(String username) {

        String sql="select * from employee where username=?";
        List<Employee> employees=jdbcTemplate.query(sql,new Object[]{username},employeeRowMapper);
        if(employees.isEmpty())return null;
        else return employees.get(0);
    }
    @Override
    public void update(Employee employee) {

        String sql="update employee set salary=?,bank_name=?,ifsc_code=?,bank_account_no=?,pf_no=?,DOB=?,address=?,pincode=?,gender=? where username=?";
        jdbcTemplate.update(sql,employee.getSalary(),employee.getBankName(),employee.getIfscCode(),employee.getBankAccountNumber(),employee.getPFNumber(),employee.getDateOfBirth(),employee.getAddress(),employee.getPincode(),employee.getGender(),employee.getUsername());
    }
}
