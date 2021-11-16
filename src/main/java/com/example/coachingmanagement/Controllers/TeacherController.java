package com.example.coachingmanagement.Controllers;

import com.example.coachingmanagement.Models.*;
import com.example.coachingmanagement.Repository.*;
import com.example.coachingmanagement.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class TeacherController {
    SecurityService securityService;
    UserRepository userRepository;
    EmployeeService employeeService;
    UserPhoneRepository userPhoneRepository;
    UserEmailRepository userEmailRepository;
    ContactRepository contactRepository;
    ContactPhoneRepository contactPhoneRepository;
    UserService userService;
    ContactPhoneService contactPhoneService;
    UserPhoneService userPhoneService;
    UserEmailService userEmailService;
    TeacherRepository teacherRepository;
    SubjectRepository subjectRepository;
    BatchRepository batchRepository;
    EmployeeAttendanceRepository employeeAttendanceRepository;
    PayrollRepository payrollRepository;


    @Autowired
    public TeacherController(SecurityService securityService, UserRepository userRepository, EmployeeService employeeService, UserPhoneRepository userPhoneRepository, UserEmailRepository userEmailRepository, ContactRepository contactRepository, ContactPhoneRepository contactPhoneRepository, UserService userService, ContactPhoneService contactPhoneService, UserPhoneService userPhoneService, UserEmailService userEmailService, TeacherRepository teacherRepository, SubjectRepository subjectRepository, BatchRepository batchRepository,
                             EmployeeAttendanceRepository employeeAttendanceRepository,PayrollRepository payrollRepository) {
        this.securityService = securityService;
        this.userRepository = userRepository;
        this.employeeService = employeeService;
        this.userPhoneRepository = userPhoneRepository;
        this.userEmailRepository = userEmailRepository;
        this.contactRepository = contactRepository;
        this.contactPhoneRepository = contactPhoneRepository;
        this.userService = userService;
        this.contactPhoneService = contactPhoneService;
        this.userPhoneService = userPhoneService;
        this.userEmailService = userEmailService;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.batchRepository = batchRepository;
        this.employeeAttendanceRepository=employeeAttendanceRepository;
        this.payrollRepository=payrollRepository;
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

    Boolean check()
    {
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null)return false;
        User user = userRepository.findByUsername(loggedInUSerName);

        if(user!=null && (user.getRole().equalsIgnoreCase("Staff")||user.getRole().equalsIgnoreCase("Teacher")||user.getRole().equalsIgnoreCase("Admin")))return true;
        return false;
    }



    @GetMapping("/teacher/{username}")
    public String teacher(Model model, @PathVariable String username)
    {
        passLoggedInUser(model,null);
        String loggedInUser=securityService.findLoggedInUsername();
        if(loggedInUser==null)
        {
            return "redirect:/redirect";
        }
        User user=userRepository.findByUsername(loggedInUser);
        if(user.getRole().equalsIgnoreCase("Student"))
        {
            return "redirect:/redirect";
        }

        User user1=userRepository.findByUsername(username);
        model.addAttribute("user",user1);
        Employee employee=employeeService.getFromUsername(username);
        Teacher teacherInfo=teacherRepository.getFromEmployeeId(employee.getEmployeeId());
        Subject subject1=subjectRepository.getSubjectById(teacherInfo.getSubjectId());
        String subject=null;
        if(subject1!=null)subject=subject1.getName();
        model.addAttribute("subject",subject);
        model.addAttribute("employee",employee);
        List<String> phoneNumberList=userPhoneRepository.findByUsernameNumbers(username);
        List<String> emailList=userEmailRepository.findByUsernameString(username);

        model.addAttribute("phonelist",phoneNumberList);
        model.addAttribute("emailList",emailList);
        List<EmergencyContact> emergencyContacts=contactRepository.findByUsername(username);
        model.addAttribute("contacts",emergencyContacts);
        List<List<String>> phoneNumbers=new ArrayList<>();
        for(EmergencyContact contact:emergencyContacts)
        {
            List<String> contactPhoneNumberList=contactPhoneRepository.findNumbers(username,contact.getContactId());
            phoneNumbers.add(contactPhoneNumberList);
        }
        model.addAttribute("contactPhone",phoneNumbers);
        List<Batch> batchList=batchRepository.getBatchesByTeacherId(teacherInfo.getTeacherId());
        model.addAttribute("batches",batchList);
        List<EmployeeAttendance> employeeAttendances=employeeAttendanceRepository.getFiveRecentAttendanceByEmployeeId(employee.getEmployeeId());
        model.addAttribute("attendanceList",employeeAttendances);
        List<Payroll> payrolls=payrollRepository.getFiveRecentByEmployeeId(employee.getEmployeeId());
        model.addAttribute("payrollList",payrolls);
        return "teacher";
    }

    @GetMapping("/teacher/{username}/edit")
    public String edit(Model model, @PathVariable String username)
    {
        passLoggedInUser(model,null);
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null)
        {
            return "redirect:/redirect";
        }

        User user=userRepository.findByUsername(loggedInUSerName);
        if(user.getRole().equalsIgnoreCase("teacher") && !loggedInUSerName.equalsIgnoreCase(username))
        {
            model.addAttribute("message","You do not have access to the requested page");
            return "redirect:/redirect";
        }
        User employee=userRepository.findByUsername(username);
        Employee employee1=employeeService.getFromUsername(username);
        if(user.getRole().equalsIgnoreCase("Student"))
        {return "redirect:/redirect";}
        Integer subjectId=null;

        model.addAttribute("subject",subjectId);
        model.addAttribute("employeeInfo",employee1);
        model.addAttribute("employee",employee);
        model.addAttribute("user",employee);


        return "employeeEdit";




    }

    @GetMapping("/teacher/{username}/remove")
    public RedirectView remove(HttpServletRequest req, RedirectAttributes redir, @PathVariable String username)
    {

        passLoggedInUser(null,redir);
        RedirectView redirectView=new RedirectView("/");
        if(check()==false)
        {
            redir.addFlashAttribute("message","You do not have access to the requested page");
            return redirectView;
        }
        String loggedUser=securityService.findLoggedInUsername();
        User user=userRepository.findByUsername(loggedUser);
        if(user.getRole().equalsIgnoreCase("teacher"))
        {
            redir.addFlashAttribute("message","You do not have access to the requested page");
            return redirectView;
        }
        User employee=userRepository.findByUsername(username);


        userRepository.delete(employee);
        redir.addFlashAttribute("message","Operation Successful");
        return redirectView;

    }

    @PostMapping("/teacher/{username}/edit")
    public RedirectView studentEdit(@ModelAttribute("employee") User user,@ModelAttribute("subject") Integer subjectId, @ModelAttribute("employeeInfo") Employee employeeInfo, @PathVariable String username, Model model, RedirectAttributes redir)
    {
        System.out.println(subjectId);
        passLoggedInUser(null,redir);
        User prevUser=userRepository.findByUsername(username);
        Employee prevEmployee=employeeService.getFromUsername(username);
    Teacher prevTeacher=teacherRepository.getFromEmployeeId(prevEmployee.getEmployeeId());
    prevTeacher.setSubjectId(subjectId);
        userService.update(prevUser,user);
        employeeService.update(prevEmployee,employeeInfo);
   teacherRepository.update(prevTeacher);
        RedirectView redirectView=new RedirectView("/teacher/"+username,true);
        redir.addFlashAttribute("message","Information was successfully updated");
        return redirectView;
    }

    @GetMapping("/teacher/{username}/contact/{id}/edit")
    public String teacherContactEdit(Model model,@PathVariable String username,@PathVariable int id)
    {

        if(check()==false)
        {
            return "redirect:/redirect";
        }
        String loggedUser=securityService.findLoggedInUsername();
        User user=userRepository.findByUsername(loggedUser);
        if(user.getRole().equalsIgnoreCase("teacher") && !loggedUser.equalsIgnoreCase(username))
        {
            model.addAttribute("message","You do not have access to the requested page");
            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        User employee=userRepository.findByUsername(username);

        EmergencyContact contact=contactRepository.findByUserAndId(username,id);
        model.addAttribute("contact",contact);
        model.addAttribute("user",employee);

        return "contactEdit";

    }

    @PostMapping("/teacher/{username}/contact/{id}/edit")
    public  RedirectView studentContactEdit(HttpServletRequest request, Model model,@ModelAttribute("contact") EmergencyContact contact,@PathVariable String username,@PathVariable int id, RedirectAttributes redir)  {


        passLoggedInUser(null,redir);
        contact.setContactId(id);
        contactRepository.update(contact);

        RedirectView redirectView=new RedirectView("/teacher/"+username,true);
        redir.addFlashAttribute("message","Information was successfully updated");
        return redirectView;
    }

    @GetMapping("/teacher/{username}/contact/{id}/remove")
    public String studentContactRemove(Model model,@PathVariable String username,@PathVariable int id)
    {

        Boolean flag=check();
        if(flag==false)
        {
            return "redirect:/redirect";
        }
        String loggedUser=securityService.findLoggedInUsername();
        User user=userRepository.findByUsername(loggedUser);
        if(user.getRole().equalsIgnoreCase("teacher") && !loggedUser.equalsIgnoreCase(username))
        {
            model.addAttribute("message","You do not have access to the requested page");
            return "redirect:/redirect";
        }
        contactRepository.delete(username,id);

        return "redirect:/teacher/"+username;


    }



    @GetMapping("/teacher/{username}/contact/add")
    public String studentContactAdd(Model model,@PathVariable String username)
    {
        passLoggedInUser(model,null);
        Boolean flag=check();
        if(flag==false)
        {
            return "redirect:/redirect";
        }
        String loggedUser=securityService.findLoggedInUsername();
        User user=userRepository.findByUsername(loggedUser);
        if(user.getRole().equalsIgnoreCase("teacher") && !loggedUser.equalsIgnoreCase(username))
        {
            model.addAttribute("message","You do not have access to the requested page");
            return "redirect:/redirect";
        }
        User employee=userRepository.findByUsername(username);
        EmergencyContact contact=new EmergencyContact();
        model.addAttribute("contact",contact);
        model.addAttribute("user",employee);
        return "addContact";
    }

    @PostMapping("/teacher/{username}/contact/add")
    public String studentContactAdd(@ModelAttribute EmergencyContact contact,@PathVariable String username)
    {
        contactRepository.add(contact,username);
        return "redirect:/teacher/"+username;
    }



    @GetMapping("/teacher/{username}/contact/{id}/phone/add")
    public String contactPhoneAdd(Model model,@PathVariable String username,@PathVariable int id)
    {
        if(check()==false)
        {
            return "redirect:/redirect";
        }
        String loggedUser=securityService.findLoggedInUsername();
        User user=userRepository.findByUsername(loggedUser);
        if(user.getRole().equalsIgnoreCase("teacher") && !loggedUser.equalsIgnoreCase(username))
        {
            model.addAttribute("message","You do not have access to the requested page");
            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        ContactPhoneNumber contactPhoneNumber=new ContactPhoneNumber();
        model.addAttribute("contactPhone",contactPhoneNumber);
        User user1=userRepository.findByUsername(username);
        model.addAttribute("user",user1);
        model.addAttribute("id",id);
        return "addContactPhone";
    }

    @PostMapping("/teacher/{username}/contact/{id}/phone/add")
    public RedirectView contactPhoneAdd(Model model,@ModelAttribute ContactPhoneNumber contactPhoneNumber,@PathVariable String username,@PathVariable int id,RedirectAttributes redir)
    {
        contactPhoneNumber.setContactId(id);
        contactPhoneNumber.setUsername(username);
        passLoggedInUser(null,redir);
        Boolean res=contactPhoneService.add(contactPhoneNumber);
        if(res)
        {
            RedirectView redirectView=new RedirectView("/teacher/"+username);

            return redirectView;
        }
        RedirectView redirectView=new RedirectView("teacher/"+username+"/contact/"+id+"/phone/add");
        redir.addFlashAttribute("message","invalid mobile number");
        return redirectView;

    }

    @GetMapping("/teacher/{username}/contact/{id}/edit/{phone}")
    public String contactPhoneEdit(Model model,@ModelAttribute("message") String message,@PathVariable String username,@PathVariable int id,@PathVariable String phone) {

        if(check()==false)
        {
            return "redirect:/redirect";
        }
        String loggedUser=securityService.findLoggedInUsername();
        User user=userRepository.findByUsername(loggedUser);
        if(user.getRole().equalsIgnoreCase("teacher") && !loggedUser.equalsIgnoreCase(username))
        {
            model.addAttribute("message","You do not have access to the requested page");
            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        model.addAttribute("message",message);
        User user1=userRepository.findByUsername(username);
        model.addAttribute("user",user1);
        model.addAttribute("id",id);
        ContactPhoneNumber contactPhoneNumber=new ContactPhoneNumber(phone,username,id);
        model.addAttribute("phone",contactPhoneNumber);
        return "contactPhoneEdit";
    }

    @PostMapping("/teacher/{username}/contact/{id}/edit/{phone}")
    public RedirectView contactPhoneEdit(@ModelAttribute ContactPhoneNumber contactPhoneNumber,@PathVariable String phone,@PathVariable int id,@PathVariable String username,RedirectAttributes redir)
    {

        passLoggedInUser(null,redir);
        contactPhoneNumber.setContactId(id);
        contactPhoneNumber.setUsername(username);
        Boolean res=contactPhoneService.update(phone,contactPhoneNumber);
        RedirectView redirectView;
        if(res) {
            redirectView = new RedirectView("/teacher/" + username, true);
        }
        else {
            redirectView = new RedirectView("/teacher/" + username + "/contact/" + id + "/phone/" + phone + "/edit", true);
            redir.addFlashAttribute("message","invalid mobile number");
        }
        return redirectView;

    }

    @GetMapping("/teacher/{username}/contact/{id}/remove/{phone}")
    public RedirectView contactPhoneRemove(RedirectAttributes redir,Model model,@PathVariable String username,@PathVariable int id,@PathVariable String phone) {

        passLoggedInUser(null,redir);
        if(check()==false)
        {
            return new RedirectView("/redirect",true);
        }
        String loggedUser=securityService.findLoggedInUsername();
        User user=userRepository.findByUsername(loggedUser);
        if(user.getRole().equalsIgnoreCase("teacher") && !loggedUser.equalsIgnoreCase(username))
        {

            return new RedirectView("/redirect",true);
        }
        redir.addFlashAttribute("message","Phone number removed successfully");
        ContactPhoneNumber contactPhoneNumber=new ContactPhoneNumber(phone,username,id);
        contactPhoneRepository.delete(contactPhoneNumber);
        RedirectView redirectView=new RedirectView("/teacher/"+username,true);
        return redirectView;

    }

    @GetMapping("/teacher/{username}/phone/{phone}/edit")
    public String studentPhoneEdit(Model model,@ModelAttribute("message") String message,@PathVariable String username,@PathVariable String phone)
    {
        if(check()==false)
        {

        }
        String loggedUser=securityService.findLoggedInUsername();
        User user=userRepository.findByUsername(loggedUser);
        if(user.getRole().equalsIgnoreCase("teacher") && !loggedUser.equalsIgnoreCase(username))
        {

            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        model.addAttribute("message",message);
        User user1=userRepository.findByUsername(username);
        model.addAttribute("user",user1);
        UserPhoneNumber userPhoneNumber=new UserPhoneNumber();
        userPhoneNumber.setPhoneNumber(phone);
        model.addAttribute("phone",userPhoneNumber);
        return "userPhoneEdit";
    }

    @PostMapping("/teacher/{username}/phone/{phone}/edit")
    public RedirectView studentPhoneEdit(RedirectAttributes redir,@ModelAttribute UserPhoneNumber userPhoneNumber,@PathVariable String username,@PathVariable String phone)
    {

        passLoggedInUser(null,redir);
        Boolean res=userPhoneService.update(username,phone,userPhoneNumber.getPhoneNumber());

        RedirectView redirectView;
        if(res) {

            redirectView = new RedirectView("/teacher/" + username, true);
        }
        else {
            redirectView = new RedirectView("/teacher/" + username + "/phone/" + phone + "/edit", true);
            redir.addFlashAttribute("message","invalid mobile number");
        }
        return redirectView;
    }

    @GetMapping("/teacher/{username}/phone/{phone}/remove")
    public RedirectView userPhoneRemove(RedirectAttributes redir,Model model,@PathVariable String username,@PathVariable String phone) {

        if(check()==false)
        {
            return new RedirectView("/redirect",true);
        }
        String loggedUser=securityService.findLoggedInUsername();
        User user=userRepository.findByUsername(loggedUser);
        if(user.getRole().equalsIgnoreCase("teacher") && !loggedUser.equalsIgnoreCase(username))
        {

            return new RedirectView("/redirect",true);
        }
        passLoggedInUser(null,redir);
        redir.addFlashAttribute("message","Phone number removed successfully");
        userPhoneRepository.remove(username,phone);
        RedirectView redirectView=new RedirectView("/teacher/"+username,true);
        return redirectView;

    }

    @GetMapping("/teacher/{username}/phone/add")
    public String  userPhoneAdd(Model model,@PathVariable String username)
    {
        if(check()==false)
        {
            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        String loggedUser=securityService.findLoggedInUsername();
        User user=userRepository.findByUsername(loggedUser);
        if(user.getRole().equalsIgnoreCase("teacher") && !loggedUser.equalsIgnoreCase(username))
        {

            return "redirect:/redirect";
        }

        UserPhoneNumber userPhoneNumber=new UserPhoneNumber();
        model.addAttribute("userPhone",userPhoneNumber);
        User user1=userRepository.findByUsername(username);
        model.addAttribute("user",user1);
        return "addUserPhone";
    }

    @PostMapping("/teacher/{username}/phone/add")
    public RedirectView userPhoneAdd(Model model,@ModelAttribute UserPhoneNumber userPhoneNumber,@PathVariable String username,RedirectAttributes redir)
    {
        if(check()==false)
        {
            return new RedirectView("/redirect",true);
        }
        passLoggedInUser(null,redir);
        userPhoneNumber.setUsername(username);
        Boolean res=userPhoneService.add(userPhoneNumber);
        if(res)
        {
            RedirectView redirectView=new RedirectView("/teacher/"+username);
            return redirectView;
        }

        RedirectView redirectView=new RedirectView("/teacher/"+username+"/phone/add",true);
        redir.addFlashAttribute("message","invalid mobile number");
        return redirectView;

    }

    @GetMapping("/teacher/{username}/email/add")
    public String  userEmailAdd(Model model,@PathVariable String username)
    {
        if(check()==false)
        {
            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        String loggedInUsername=securityService.findLoggedInUsername();
        User user=userRepository.findByUsername(loggedInUsername);
        if(user.getRole().equalsIgnoreCase("teacher")&& !username.equalsIgnoreCase(loggedInUsername))return "redirect:/redirect";
        UserEmail userEmail=new UserEmail();
        userEmail.setUsername(username);
        User user1=userRepository.findByUsername(username);
        model.addAttribute("user",user1);
        model.addAttribute("userEmail",userEmail);

        return "addUserEmail";
    }

    @PostMapping("/teacher/{username}/email/add")
    public RedirectView useEmailAdd(Model model,@ModelAttribute UserEmail userEmail,@PathVariable String username,RedirectAttributes redir)
    {
        passLoggedInUser(null,redir);
        userEmail.setUsername(username);
        boolean res=userEmailService.add(userEmail);
        if(res)
        {
            RedirectView redirectView=new RedirectView("/teacher/"+username);
            redir.addFlashAttribute("loginStatus",true);
            return redirectView;
        }
        RedirectView redirectView=new RedirectView("/teacher/"+username+"/email/add");
        redir.addFlashAttribute("message","invalid email");
        return redirectView;

    }

    @GetMapping("/teacher/{username}/email/{email}/edit")
    public String teacherEmailEdit(Model model,@ModelAttribute("message") String message,@PathVariable String username,@PathVariable String email)
    {
        if(check()==false)
        {
            return "redirect:/redirect";
        }
        String loggedUser=securityService.findLoggedInUsername();
        User user=userRepository.findByUsername(loggedUser);
        if(user.getRole().equalsIgnoreCase("teacher") && !loggedUser.equalsIgnoreCase(username))
        {

            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        model.addAttribute("message",message);
        User user1=userRepository.findByUsername(username);
        model.addAttribute("user",user1);
        UserEmail userEmail=new UserEmail();
        userEmail.setUsername(username);
        userEmail.setEmail(email);
        model.addAttribute("email",userEmail);
        return "userEmailEdit";
    }

    @PostMapping("/teacher/{username}/email/{email}/edit")
    public RedirectView teacherEmailEdit(RedirectAttributes redir,@ModelAttribute UserEmail userEmail,@PathVariable String username,@PathVariable String email)
    {

        passLoggedInUser(null,redir);
        Boolean res=userEmailService.update(username,email,userEmail.getEmail());

        RedirectView redirectView;
        if(res) {

            redirectView = new RedirectView("/teacher/" + username, true);
        }
        else {
            redirectView = new RedirectView("/teacher/" + username + "/email/" + email + "/edit", true);
            redir.addFlashAttribute("message","invalid email");
        }
        return redirectView;
    }


    @GetMapping("/teacher/{username}/email/{email}/remove")
    public RedirectView userEmailRemove(RedirectAttributes redir,Model model,@PathVariable String username,@PathVariable String email) {

        if(check()==false)
        {
            return new RedirectView("/redirect",true);
        }
        String loggedUser=securityService.findLoggedInUsername();
        User user=userRepository.findByUsername(loggedUser);
        if(user.getRole().equalsIgnoreCase("teacher") && !loggedUser.equalsIgnoreCase(username))
        {

            return new RedirectView("/redirect",true);
        }
        passLoggedInUser(null,redir);
        redir.addFlashAttribute("message","Email removed successfully");
        userEmailRepository.remove(username,email);
        RedirectView redirectView=new RedirectView("/teacher/"+username,true);
        return redirectView;

    }

    @GetMapping("/employee/{username}/payroll")
    public String getEmployeePayroll(@PathVariable String username,Model model)
    {
        if(check()==false)
        {
            return "redirect:/redirect";
        }
        String loggedUser=securityService.findLoggedInUsername();
        User user=userRepository.findByUsername(loggedUser);
        if(user.getRole().equalsIgnoreCase("teacher") && !loggedUser.equalsIgnoreCase(username))
        {

            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        User user1=userRepository.findByUsername(username);
        Employee employee=employeeService.getFromUsername(username);
        List<Payroll> payrolls=payrollRepository.getAll(employee.getEmployeeId());
        model.addAttribute("payrollList",payrolls);
        model.addAttribute("user",user1);
        model.addAttribute("employee",employee);
        return "employeePayroll";
    }

    @GetMapping("/payrolls/add")
    public String addEmployeePayroll(Model model)
    {
        passLoggedInUser(model,null);
        Boolean res=check();
        if(res==false)
        {

            return "redirect:/redirect";
        }
        String loggedUsername=securityService.findLoggedInUsername();
        User loggedUser=userRepository.findByUsername(loggedUsername);

        if(loggedUser.getRole().equalsIgnoreCase("teacher"))
        {
            return "redirect:/redirect";
        }
        Payroll payroll=new Payroll();
        model.addAttribute("payroll",payroll);
        model.addAttribute("date",new String());
        return "addPayroll";

    }

    @PostMapping("/payrolls/add")
    public RedirectView addEmployeePayrollPost(RedirectAttributes redirectAttributes,@ModelAttribute("payroll") Payroll payroll,@ModelAttribute("date") String date) throws ParseException {



        passLoggedInUser(null,redirectAttributes);
        Date date1=new SimpleDateFormat("yyyy-mm-dd").parse(date);
        java.sql.Date sqlDate=new java.sql.Date(date1.getTime());
        payroll.setDateOfPayment(sqlDate);
        if(!payrollRepository.exists(payroll))payrollRepository.add(payroll);

        return new RedirectView("/payrolls",true);

    }

}
