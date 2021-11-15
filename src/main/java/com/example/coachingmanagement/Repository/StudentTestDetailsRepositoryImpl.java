package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.StudentTestDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentTestDetailsRepositoryImpl implements StudentTestDetailsRepository{
    JdbcTemplate jdbcTemplate;
    RowMapper<StudentTestDetails> rowMapper;

    @Autowired
    public StudentTestDetailsRepositoryImpl(JdbcTemplate jdbcTemplate, RowMapper<StudentTestDetails> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    public List<StudentTestDetails> getRecentFiveTestsByRollNo(int roll) {
        String sql="select st.* from student_test_details as st,test where st.test_no=test.test_no and st.subject_id=test.subject_id and st.roll_no=?  order by test.test_date DESC limit 5";
        return jdbcTemplate.query(sql,new Object[]{roll},rowMapper);

    }

    @Override
    public List<StudentTestDetails> getAllTests(int roll) {
        String sql="select st.* from student_test_details as st,test where st.test_no=test.test_no and st.subject_id=test.subject_id and st.roll_no=?  order by test.test_date DESC";
        return jdbcTemplate.query(sql,new Object[]{roll},rowMapper);
    }

    @Override
    public StudentTestDetails getFromStudentAndTest(int roll, int subject, int testNo) {

        String sql="select * from student_test_details where roll_no=? and subject_id=? and test_no=?";
        List<StudentTestDetails> studentTestDetails=jdbcTemplate.query(sql,new Object[]{roll,subject,testNo},rowMapper);
        if(studentTestDetails.isEmpty())return null;
        return studentTestDetails.get(0);
    }

    @Override
    public Boolean exists(int subject, int id, int roll) {
        String sql="select count(*) from student_test_details where subject_id=? and test_no=? and roll_no=?";
        Integer count=jdbcTemplate.queryForObject(sql,new Object[]{subject,id,roll},Integer.class);
        return (count>0);
    }

    @Override
    public void add(StudentTestDetails studentTestDetails) {
        String sql="insert into student_test_details(roll_no,test_no,subject_id,marks,remarks) values(?,?,?,?,?)";
        jdbcTemplate.update(sql,studentTestDetails.getRollNumber(),studentTestDetails.getTestNumber(),studentTestDetails.getSubjectId(),studentTestDetails.getMarks(),studentTestDetails.getRemarks());

    }

    @Override
    public void update(StudentTestDetails studentTestDetails) {
        String sql="update student_test_details set marks=?,remarks=? where roll_no=? and subject_id=? and test_no=?";
        jdbcTemplate.update(sql,studentTestDetails.getMarks(),studentTestDetails.getRemarks(),studentTestDetails.getRollNumber(),studentTestDetails.getSubjectId(),studentTestDetails.getTestNumber());
    }
}
