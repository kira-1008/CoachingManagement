package com.example.coachingmanagement.Models;

import java.util.Date;

public class Payroll {
    int paymentReferenceNumber;
    String paymentMode;
    int amount;
    Date dateOfPayment;
    int employeeId;

    public Payroll() {
    }

    public Payroll(int paymentReferenceNumber, String paymentMode, int amount, Date dateOfPayment, int employeeId) {
        this.paymentReferenceNumber = paymentReferenceNumber;
        this.paymentMode = paymentMode;
        this.amount = amount;
        this.dateOfPayment = dateOfPayment;
        this.employeeId = employeeId;
    }

    public int getPaymentReferenceNumber() {
        return paymentReferenceNumber;
    }

    public void setPaymentReferenceNumber(int paymentReferenceNumber) {
        this.paymentReferenceNumber = paymentReferenceNumber;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "Payroll{" +
                "paymentReferenceNumber=" + paymentReferenceNumber +
                ", paymentMode='" + paymentMode + '\'' +
                ", amount=" + amount +
                ", dateOfPayment=" + dateOfPayment +
                ", employeeId=" + employeeId +
                '}';
    }
}
