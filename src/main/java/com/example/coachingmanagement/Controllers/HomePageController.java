package com.example.coachingmanagement.Controllers;

import com.example.coachingmanagement.Models.User;
import com.example.coachingmanagement.Repository.UserRepository;
import com.example.coachingmanagement.Services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomePageController {
    SecurityService securityService;
    UserRepository userRepository;

    @Autowired
    public HomePageController(SecurityService securityService,UserRepository userRepository) {
        this.securityService = securityService;
        this.userRepository=userRepository;
    }

    public void passLoggedInUser(Model model)
    {
        String loggedInUsername=securityService.findLoggedInUsername();
        User user=null;
        if(loggedInUsername!=null)
        {
            if(model!=null)model.addAttribute("loginStatus",true);
            user=userRepository.findByUsername(loggedInUsername);
        }
        else {
            if(model!=null)model.addAttribute("loginStatus", false);

        }
        if(model!=null)model.addAttribute("loggedUser",user);

    }

    @RequestMapping("/")
    public String homepage(Authentication authentication, Model model, @ModelAttribute("message") String message)
    {
        passLoggedInUser(model);

      model.addAttribute("message",message);
        return "homepage";
    }



    @RequestMapping("/mission")
    public String mission(Authentication authentication,Model model)
    {
        passLoggedInUser(model);

        return "mission";
    }

    @RequestMapping("/programs")
    public String programs(Authentication authentication,Model model)
    {
       passLoggedInUser(model);

        return "programs";
    }


}
