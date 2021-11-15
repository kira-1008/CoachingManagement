package com.example.coachingmanagement.Services;

import com.example.coachingmanagement.Models.UserEmail;
import com.example.coachingmanagement.Repository.UserEmailRepository;
import com.example.coachingmanagement.Utility.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserEmailServiceImpl implements  UserEmailService{
    UserEmailRepository userEmailRepository;

    @Autowired
    public UserEmailServiceImpl(UserEmailRepository userEmailRepository) {
        this.userEmailRepository = userEmailRepository;
    }

    @Override
    public boolean update(String username,String prev,String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Boolean res= Validators.validateEmail(email,regexPattern);
        if(res==false)return res;
        userEmailRepository.edit(username,prev,email);
        return true;

    }

    @Override
    public boolean add(UserEmail userEmail) {

        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Boolean res= Validators.validateEmail(userEmail.getEmail(),regexPattern);
        if(res == false)return res;
        userEmailRepository.add(userEmail.getUsername(),userEmail.getEmail());
        return res;
    }
}
