package com.example.coachingmanagement;

import com.example.coachingmanagement.Models.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication()
public class CoachingManagementApplication {

    public static void main(String[] args) {

        SpringApplication.run(CoachingManagementApplication.class, args);
    }

}
