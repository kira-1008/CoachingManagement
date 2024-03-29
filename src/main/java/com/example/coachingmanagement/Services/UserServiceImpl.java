package com.example.coachingmanagement.Services;

import com.example.coachingmanagement.Models.User;
import com.example.coachingmanagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public Boolean exists(User user){
        Boolean exist=userRepository.exists(user.getUsername());
        return exist;
    }

    @Override
    public void update(User prevUser, User user) {
        prevUser.setFirstName(user.getFirstName());
        prevUser.setMiddleName(user.getMiddleName());
        prevUser.setLastName(user.getLastName());
        System.out.println("new password is"+user.getPassword());
        if(user.getPassword()!=null && !user.getPassword().isEmpty()) {user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        prevUser.setPassword(user.getPassword());}
        userRepository.update(prevUser);
    }
}
