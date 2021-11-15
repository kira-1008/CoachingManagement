package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.Payroll;

import java.util.List;

public interface PayrollRepository {
    public List<Payroll> getFiveRecentByEmployeeId(int id);
    public List<Payroll> getAll(int id);
    public void add(Payroll payroll);
    public Boolean exists(Payroll payroll);
    public List<Payroll> listAll();
}
