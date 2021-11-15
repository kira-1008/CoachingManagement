package com.example.coachingmanagement.Controllers;

import com.example.coachingmanagement.Models.PractiseSheet;
import com.example.coachingmanagement.Models.User;
import com.example.coachingmanagement.Repository.PractiseRepository;
import com.example.coachingmanagement.Repository.UserRepository;
import com.example.coachingmanagement.Services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class SubjectController {
    PractiseRepository practiseRepository;
    SecurityService securityService;
    UserRepository userRepository;

    @Autowired
    public SubjectController(PractiseRepository practiseRepository,SecurityService securityService,UserRepository userRepository) {
        this.practiseRepository = practiseRepository;
        this.securityService=securityService;
        this.userRepository=userRepository;
    }


    public void passLoggedInUser(Model model, RedirectAttributes redirectAttributes)
    {
        String loggedInUsername=securityService.findLoggedInUsername();
        User user=null;
        if(loggedInUsername!=null)
        {
            if(model!=null)model.addAttribute("loginStatus",true);
            if(redirectAttributes!=null)redirectAttributes.addFlashAttribute("loginStatus",true);
            user=userRepository.findByUsername(loggedInUsername);
        }
        else {
            if(model!=null)model.addAttribute("loginStatus", false);
            if(redirectAttributes!=null)redirectAttributes.addFlashAttribute("loginStatus",false);
        }
        if(model!=null)model.addAttribute("loggedUser",user);
        if(redirectAttributes!=null)redirectAttributes.addFlashAttribute("loggedUser",user);
    }

    @GetMapping("/subject/{subjectId}/practise")
    public String practiseList(Model model, @PathVariable int subjectId)
    {
        String loggedUser=securityService.findLoggedInUsername();
        if(loggedUser==null)
        {
            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        List<PractiseSheet> sheetList=practiseRepository.getListbySubject(subjectId);
        model.addAttribute("sheetList",sheetList);
        model.addAttribute("loginStatus",true);
        return "practiseSheetList";
    }
}
