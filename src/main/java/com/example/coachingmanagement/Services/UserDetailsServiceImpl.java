package com.example.coachingmanagement.Services;

import com.example.coachingmanagement.Models.User;
import com.example.coachingmanagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Username is "+username);
        User user=userRepository.findByUsername(username);
        if(user==null)
        {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        List<GrantedAuthority> grantList = new ArrayList<>();
        String role=user.getRole();
        if(role!=null) {
            String[] roles = role.split(" ");
            for (int i = 0; i < roles.length; i++) {
                GrantedAuthority authority = new SimpleGrantedAuthority(roles[i]);
                grantList.add(authority);
            }
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(),grantList
        );
    }
}
