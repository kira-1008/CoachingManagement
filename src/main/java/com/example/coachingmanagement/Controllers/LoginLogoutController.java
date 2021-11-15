package com.example.coachingmanagement.Controllers;

import com.example.coachingmanagement.Models.User;
import com.example.coachingmanagement.Repository.UserRepository;
import com.example.coachingmanagement.Services.SecurityService;
import com.example.coachingmanagement.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class LoginLogoutController {

    SecurityService securityService;
    UserService userService;
    UserRepository userRepository;

   @Autowired
    public LoginLogoutController(UserService userService,SecurityService securityService,UserRepository userRepository) {
        this.userService = userService;
        this.securityService=securityService;
        this.userRepository=userRepository;
    }


    @GetMapping("/register")
    public String register(Model model)
    {
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName!=null){
           return "redirect:/";
        }

        User user=new User();
        model.addAttribute("user",user);
        model.addAttribute("existing_user",false);
        model.addAttribute("loginStatus",false);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model) {


       if(userService.exists(user))
        {
            model.addAttribute("user",user);
            model.addAttribute("existing_user",true);
            return "register";
        }

        java.util.Date date=new java.util.Date();
        user.setCreationDate(new java.sql.Date(date.getTime()));
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(String error,Model model,Authentication authentication)
    {
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName!=null){
            return "redirect:/";
        }
        if(error!=null)
        {
            model.addAttribute("errormessage","Either the password username combination is invalid or user does not exist");
        }
        model.addAttribute("loginStatus",false);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(Authentication authentication){
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null){
            return "redirect:/login";
        }
        return "logout";
    }




}
