package com.example.coachingmanagement.Controllers;

import com.example.coachingmanagement.Services.SecurityService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {
    SecurityService securityService;

    public HomePageController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @RequestMapping("/")
    public String homepage(Authentication authentication, Model model)
    {
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }
        return "homepage";
    }

    @RequestMapping("/mission")
    public String mission(Authentication authentication,Model model)
    {
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }

        return "mission";
    }

    @RequestMapping("/programs")
    public String programs(Authentication authentication,Model model)
    {
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null){
            model.addAttribute("loginStatus",false);
        }else{
            model.addAttribute("loginStatus",true);
        }

        return "programs";
    }
}
