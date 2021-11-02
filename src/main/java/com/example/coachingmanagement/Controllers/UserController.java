package com.example.coachingmanagement.Controllers;

import com.example.coachingmanagement.Models.User;
import com.example.coachingmanagement.Models.UserEmail;
import com.example.coachingmanagement.Models.UserPhoneNumber;
import com.example.coachingmanagement.Repository.UserEmailRepository;
import com.example.coachingmanagement.Repository.UserPhoneRepository;
import com.example.coachingmanagement.Repository.UserRepository;
import com.example.coachingmanagement.Services.SecurityService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
   UserRepository userRepository;
    SecurityService securityService;
UserPhoneRepository userPhoneRepository;
    UserEmailRepository userEmailRepository;

    @Autowired
    public UserController(SecurityService securityService,UserRepository userRepository,UserPhoneRepository userPhoneRepository,UserEmailRepository userEmailRepository) {
        this.securityService = securityService;
        this.userRepository=userRepository;
        this.userPhoneRepository=userPhoneRepository;
        this.userEmailRepository=userEmailRepository;
    }

    @GetMapping("/self")
    public String userPage(Model model)
    {
        System.out.println(securityService + "is not null");
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null){
            return "redirect:/";
        }
        User user = userRepository.findByUsername(loggedInUSerName);
        List<UserPhoneNumber> phoneNumberList=userPhoneRepository.findByUsername(loggedInUSerName);
        List<UserEmail> emailList=userEmailRepository.findByUsername(loggedInUSerName);
        model.addAttribute("phonelist",phoneNumberList);
        model.addAttribute("emailList",emailList);
        model.addAttribute("user",user);

        return "self";
    }

//    @PostMapping("/self/{action}")
//    public String userPage(Model model, @PathVariable String action)
//    {
//        if(action=="Edit")
//        {
//            return "redirect:/self/edit";
//        }
//        else if(action=="Remove")
//        {
//            return "redirect:/self/"
//        }
//        else
//        {
//            return null;
//        }
//    }
}
