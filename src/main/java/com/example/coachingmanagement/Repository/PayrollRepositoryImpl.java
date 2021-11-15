package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.Payroll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PayrollRepositoryImpl implements PayrollRepository{
    JdbcTemplate jdbcTemplate;
    RowMapper<Payroll> rowMapper;

    @Autowired
    public PayrollRepositoryImpl(JdbcTemplate jdbcTemplate, RowMapper<Payroll> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    public List<Payroll> getFiveRecentByEmployeeId(int id) {
        String sql="select * from payroll where employee_id=? order by date desc limit 5";
        List<Payroll> payrolls=jdbcTemplate.query(sql,new Object[]{id},rowMapper);
        return payrolls;
    }

    @Override
    public List<Payroll> getAll(int id) {
        String sql="select * from payroll where employee_id=? order by date desc";
        List<Payroll> payrolls=jdbcTemplate.query(sql,new Object[]{id},rowMapper);
        return payrolls;
    }

    @Override
    public void add(Payroll payroll) {
        String sql="insert into payroll(payment_reference_no,payment_mode,amount,date,employee_id) values(?,?,?,?,?)";
        jdbcTemplate.update(sql,payroll.getPaymentReferenceNumber(),payroll.getPaymentMode(),payroll.getAmount(),payroll.getDateOfPayment(),payroll.getEmployeeId());

    }

    @Override
    public Boolean exists(Payroll payroll) {
        String sql="select count(*) from payroll where payment_reference_no=? and payment_mode=? and amount=? and date=? and employee_id=?";
        Integer count=jdbcTemplate.queryForObject(sql,new Object[]{payroll.getPaymentReferenceNumber(),payroll.getPaymentMode(),payroll.getAmount(),payroll.getDateOfPayment(),payroll.getEmployeeId()},Integer.class);
        return (count>0);
    }

    @Override
    public List<Payroll> listAll() {
        String sql="select * from payroll order by date desc";
        List<Payroll> payrolls=jdbcTemplate.query(sql,rowMapper);
        return payrolls;
    }
}
