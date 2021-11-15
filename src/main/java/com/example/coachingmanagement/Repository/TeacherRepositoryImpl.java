package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.Teacher;
import com.example.coachingmanagement.Models.User;
import org.springframework.data.annotation.Id;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public  class TeacherRepositoryImpl implements  TeacherRepository{
   JdbcTemplate jdbcTemplate;

    public TeacherRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Teacher> rowMapper=new RowMapper<Teacher>() {
        @Override
        public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {

            Teacher teacher=new Teacher(rs.getInt("teacher_id"),rs.getInt("employee_id"),rs.getInt("subject_id"));
            return teacher;
        }
    };
    @Override
    public Teacher getTeacherFromId(Integer Id) {

       String sql="select * from teacher where teacher_id="+Id;

       List<Teacher> teachers=jdbcTemplate.query(sql,rowMapper);
       if(teachers.isEmpty())return null;
       return teachers.get(0);
    }

    @Override
    public User getTeacherUserFromBatch(int id) {

        String sql="select * from user,employee,teacher,batch where user.username=employee.username and employee.employee_id=teacher.employee_id and teacher.teacher_id=batch.teacher_id and batch.batch_id=?";
        User user= jdbcTemplate.queryForObject(sql,new Object[]{id},new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(rs.getString("username"),
                        rs.getString("first_name"),
                        rs.getString("middle_name"),
                        rs.getString("last_name"),
                        rs.getString("password"),
                        rs.getString("role"),rs.getBoolean("is_active"),rs.getDate("date_created"),rs.getDate("last_login_date"),rs.getTime("last_login_time"));

            }
        });

        return user;
    }

    @Override
    public void add(int employeeId,int subjectId) {
        String sql="insert into teacher(employee_id,subject_id) values(?,?)";
        jdbcTemplate.update(sql,employeeId,subjectId);
    }

    @Override
    public Teacher getFromEmployeeId(Integer id) {

        String sql="select * from teacher where employee_id="+id;
        List<Teacher> teacherList=jdbcTemplate.query(sql,rowMapper);
        if(teacherList.isEmpty())return  null;
        else return teacherList.get(0);
    }

    @Override
    public void update(Teacher teacher) {
        String sql="update teacher set subject_id=? where teacher_id=?";
        jdbcTemplate.update(sql,teacher.getSubjectId(),teacher.getTeacherId());
    }


}
