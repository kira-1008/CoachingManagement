package com.example.coachingmanagement.Services;

import com.example.coachingmanagement.Models.Employee;
import com.example.coachingmanagement.Models.Student;
import com.example.coachingmanagement.Repository.EmployeeRepository;
import com.example.coachingmanagement.Utility.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Integer add(Employee employee) {
       Boolean res= Validators.validateEmployee(employee);
       if(res==false)
       {
           return null;
       }
       java.util.Date date=new java.util.Date();
       java.sql.Date sqlDate=new java.sql.Date(date.getTime());
       employee.setJoinDate(sqlDate);
        Integer id=employeeRepository.add(employee);
       return id;
    }

    @Override
    public List<Employee> listAll() {
        return employeeRepository.getAll();
    }


    @Override
    public List<Employee> listStaff() {
        return employeeRepository.getAllStaff();
    }

    @Override
    public List<Employee> listTeacher() {
        return employeeRepository.getAllTeacher();
    }

    @Override
    public Employee getFromId(int id) {
        return employeeRepository.getEmployeeById(id);
    }

    @Override
    public Employee getFromUsername(String username) {
        return employeeRepository.getFromUsername(username);
    }

    @Override
    public void update(Employee prev, Employee employee)
    {
        if(employee.getGender() == null)return;
        employee.setGender(String.valueOf(employee.getGender().charAt(0)));
        prev.setGender(employee.getGender());
        prev.setPincode(employee.getPincode());
        prev.setAddress(employee.getAddress());
        prev.setDateOfBirth(employee.getDateOfBirth());
        prev.setSalary(employee.getSalary());
        prev.setBankAccountNumber(employee.getBankAccountNumber());
        prev.setBankName(employee.getBankName());
        prev.setIfscCode(employee.getIfscCode());
        prev.setPFNumber(employee.getPFNumber());
        employeeRepository.update(prev);

    }
}
