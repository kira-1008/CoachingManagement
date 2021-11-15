package com.example.coachingmanagement.Services;

import com.example.coachingmanagement.Models.UserPhoneNumber;
import com.example.coachingmanagement.Repository.UserPhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPhoneServiceImpl implements UserPhoneService{
    UserPhoneRepository userPhoneRepository;

    @Autowired
    public UserPhoneServiceImpl(UserPhoneRepository userPhoneRepository) {
        this.userPhoneRepository = userPhoneRepository;
    }
    public Boolean check(String phone)
    {
        if(phone!=null && phone.length()==10)
        {
            return true;
        }
        return false;
    }

    @Override
    public Boolean update(String username, String prev,String phone) {

        if(check(phone))
        {

            userPhoneRepository.edit(username,prev,phone);
            return true;
        }
        return false;
    }

    @Override
    public Boolean add(UserPhoneNumber userPhoneNumber) {
        if(check(userPhoneNumber.getPhoneNumber()))
        {
            userPhoneRepository.add(userPhoneNumber.getUsername(),userPhoneNumber.getPhoneNumber());
            return true;
        }
        else return false;
    }
}
