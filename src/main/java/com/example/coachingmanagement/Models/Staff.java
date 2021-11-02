package com.example.coachingmanagement.Models;

public class Staff {
    int staffId;
    int employeeId;

    public Staff() {
    }

    public Staff(int staffId, int employeeId) {
        this.staffId = staffId;
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staffId=" + staffId +
                ", employeeId=" + employeeId +
                '}';
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}
