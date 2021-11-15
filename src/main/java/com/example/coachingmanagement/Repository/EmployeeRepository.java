package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.Employee;

import java.util.List;

public interface EmployeeRepository {
    public Integer add(Employee employee);
    public List<Employee> getAll();
    public List<Employee> getAllStaff();
    public List<Employee> getAllTeacher();
    public Employee getEmployeeById(int id);
    public Employee getFromUsername(String username);
    public void update(Employee employee);
}
