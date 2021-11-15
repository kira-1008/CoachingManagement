package com.example.coachingmanagement.Services;

import com.example.coachingmanagement.Models.Employee;

import java.util.List;

public interface EmployeeService {
    public Integer add(Employee employee);
    public List<Employee> listAll();
    public List<Employee> listStaff();
    public List<Employee> listTeacher();
    public Employee getFromId(int id);
    public Employee getFromUsername(String username);
    public void update(Employee prevEmployee,Employee employee);
}
