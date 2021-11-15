package com.example.coachingmanagement.Utility;

import com.example.coachingmanagement.Models.Employee;
import com.example.coachingmanagement.Models.Student;

import java.util.regex.Pattern;

public class Validators {

    public static boolean validateEmail(String emailAddress,String regexPattern) {

        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public static Boolean validateStudent(Student student)
    {
        if(Integer.toString(student.getPincode()).length()!=6)
        {
            return false;
        }
        return true;
    }

    public static Boolean validateEmployee(Employee employee)
    {
        if(Integer.toString(employee.getPincode()).length()!=6)
        {
            return false;
        }
        return true;
    }
}
