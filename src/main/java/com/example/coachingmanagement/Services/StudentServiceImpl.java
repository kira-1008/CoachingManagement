package com.example.coachingmanagement.Services;

import com.example.coachingmanagement.Models.Student;
import com.example.coachingmanagement.Repository.StudentRepository;
import com.example.coachingmanagement.Repository.UserRepository;
import com.example.coachingmanagement.Utility.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements  StudentService{
    StudentRepository studentRepository;
    UserRepository userRepository;


    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository=userRepository;
    }

    @Override
    public void update(Student prev, Student student)
    {
        if(student.getGender()==null)return;
        student.setGender(String.valueOf(student.getGender().charAt(0)));
        prev.setGender(student.getGender());
        prev.setPincode(student.getPincode());
        prev.setAddress(student.getAddress());
        prev.setDateOfBirth(student.getDateOfBirth());
        prev.setStandard(student.getStandard());
        studentRepository.update(prev);

    }

    @Override
    public Boolean add(Student student) {
        Boolean res= Validators.validateStudent(student);
        if(res==false)return false;
        studentRepository.add(student);
        return true;
    }

    @Override
    public List<Student> listAll() {
        return studentRepository.getAll();
    }
}
