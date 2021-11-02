package com.example.coachingmanagement.Repository;

import com.example.coachingmanagement.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{
    JdbcTemplate jdbcTemplate;

    RowMapper<User> rowMapper=new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user=new User();
            user.setActive(rs.getBoolean("is_active"));
            user.setCreationDate(rs.getDate("date_created"));
            user.setUsername(rs.getString("username"));
            user.setFirstName(rs.getString("first_name"));
            user.setMiddleName(rs.getString("middle_name"));
            user.setLastName(rs.getString("last_name"));
            user.setRole(rs.getString("role"));
            user.setLastLoginDate(rs.getDate("last_login_date"));
            user.setLastLoginTime(rs.getTime("last_login_time"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    };



    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findByUsername(String username) {
       System.err.print(username);
        String sqlQuery="select * from user where username='"+username+"'";
        User user =new User();
        try{
            user=jdbcTemplate.queryForObject(sqlQuery,rowMapper);
        }
        catch(EmptyResultDataAccessException e)
        {
              e.printStackTrace();
              return null;
        }
        return user;
    }

    @Override
    public void save(User user) {
        String sqlQuery="insert into user(username,first_name,middle_name,last_name,password,role,is_active,date_created,last_login_date,last_login_time) values(?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sqlQuery,user.getUsername(),user.getFirstName(),user.getMiddleName(),user.getLastName(),user.getPassword(),user.getRole(), user.getActive(),user.getCreationDate(),user.getLastLoginDate(),user.getLastLoginTime());
    }

    @Override
    public String getPassword(String username) {

        String sqlQuery="select password from user where username='"+username+"'";
        String password=jdbcTemplate.queryForObject(sqlQuery,String.class);
        return password;
    }

    @Override
    public Boolean exists(String username) {
        String sqlQuery="select count(*) from user where username='"+username+"'";
        System.out.println(sqlQuery);
        int count = jdbcTemplate.queryForObject(sqlQuery,Integer.class);
        return (count>0);
    }

    @Override
    public void activate(String username) {
        String sqlQuery="update user set is_active=True where username='"+username+"'";
        jdbcTemplate.execute(sqlQuery);
    }

    @Override
    public void changePassword(String username, String password) {
        String sqlQuery = "update user set password=? where username=?";
        jdbcTemplate.update(sqlQuery, password, username);
    }

    @Override
    public User setLoginTimestamp(User user) {
        java.util.Date date = new java.util.Date();
        java.sql.Date sqlDate=new java.sql.Date(date.getTime());
        java.sql.Time sqlTime=new java.sql.Time(date.getTime());
        String sqlQuery="update user set last_login_date=?,last_login_time=? where username='"+user.getUsername()+"'";
        jdbcTemplate.update(sqlQuery,sqlDate,sqlTime);
        user.setLastLoginTime(sqlTime);
        user.setLastLoginDate(sqlDate);
        return user;
    }

//    @Override
//    public void addRole(User user, String role) {
//        String roles=user.getRole();
//        if(!roles.isEmpty())roles+=' ';
//        roles+=role;
//        user.setRole(roles);
//        String sqlQuery="update user set role=? where username=?";
//        jdbcTemplate.update(sqlQuery,roles,user.getUsername());
//    }

    @Override
    public void update(User user) {
        String sqlQuery="update user set first_name=?,middle_name=?,last_name=?,password=?,role=?,is_active=?,date_created=?,last_login_date=?,last_login_time=? where username=?";
        jdbcTemplate.update(sqlQuery,
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                user.getPassword(),
                user.getRole(),
                user.getActive(),
                user.getCreationDate(),
                user.getLastLoginDate(),
                user.getLastLoginTime(),
                user.getUsername());
    }

    @Override
    public void delete(User user) {
        String sqlQuery="delete from user where username=?'"+user.getUsername()+"'";
        jdbcTemplate.execute(sqlQuery);
    }

    @Override
    public List<User> getAll(){
        String sqlQuery="select * from user";
        List<User> userList=jdbcTemplate.query(sqlQuery,new BeanPropertyRowMapper<>(User.class));
        return userList;
    }
}
