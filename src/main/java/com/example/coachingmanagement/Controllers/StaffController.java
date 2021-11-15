package com.example.coachingmanagement.Controllers;

import com.example.coachingmanagement.Models.*;
import com.example.coachingmanagement.Repository.*;
import com.example.coachingmanagement.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class StaffController {
SecurityService securityService;
UserRepository userRepository;
StudentService studentService;
EmployeeService employeeService;
StaffRepository staffRepository;
TeacherRepository teacherRepository;
SubjectRepository subjectRepository;
ContactRepository contactRepository;
BatchRepository batchRepository;
UserPhoneRepository userPhoneRepository;
UserEmailRepository userEmailRepository;
ContactPhoneRepository contactPhoneRepository;
UserService userService;
ContactPhoneService contactPhoneService;
UserPhoneService userPhoneService;
UserEmailService userEmailService;
EmployeeAttendanceRepository employeeAttendanceRepository;
PayrollRepository payrollRepository;

    @Autowired
    public StaffController(SecurityService securityService, UserRepository userRepository, StudentService studentService, EmployeeService employeeService, StaffRepository staffRepository, TeacherRepository teacherRepository, SubjectRepository subjectRepository, ContactRepository contactRepository, BatchRepository batchRepository, UserPhoneRepository userPhoneRepository, UserEmailRepository userEmailRepository, ContactPhoneRepository contactPhoneRepository, UserService userService, ContactPhoneService contactPhoneService, UserPhoneService userPhoneService, UserEmailService userEmailService, EmployeeAttendanceRepository employeeAttendanceRepository,
                           PayrollRepository payrollRepository) {
        this.securityService = securityService;
        this.userRepository = userRepository;
        this.studentService = studentService;
        this.employeeService = employeeService;
        this.staffRepository = staffRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.contactRepository = contactRepository;
        this.batchRepository = batchRepository;
        this.userPhoneRepository = userPhoneRepository;
        this.userEmailRepository = userEmailRepository;
        this.contactPhoneRepository = contactPhoneRepository;
        this.userService = userService;
        this.contactPhoneService = contactPhoneService;
        this.userPhoneService = userPhoneService;
        this.userEmailService = userEmailService;
        this.employeeAttendanceRepository = employeeAttendanceRepository;
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

        if(user!=null && user.isStaffOrAdmin())return true;
        return false;
    }
    @RequestMapping("users/notverified")
    public String notVerifiedUsers(Model model)
    {
        passLoggedInUser(model,null);
        List<User> user=userRepository.getNotVerifiedUsers();
        model.addAttribute("users",user);
        return "notVerified";
    }

    @GetMapping("/student/add")
    public String addStudent(Model model,RedirectAttributes redirectAttributes,@ModelAttribute("message") String message)
    {
        if(check()==false)
        {
            return "redirect:/redirect";
        }
        passLoggedInUser(model,redirectAttributes);
        Student student=new Student();
        String date=new String();
        model.addAttribute("date",date);
        model.addAttribute("student",student);
        model.addAttribute("message",message);
        return "addStudent";
    }

    @PostMapping("/student/add")
    public RedirectView addStudent(RedirectAttributes redirectAttributes,Model model, @ModelAttribute("student") Student student, @ModelAttribute("date") String date) throws ParseException {

        passLoggedInUser(model,redirectAttributes);
        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(date);
        User user=userRepository.findByUsername(student.getUsername());
        if(!user.getRole().equalsIgnoreCase("student"))
        {
            redirectAttributes.addFlashAttribute("message","user not registered as student");
            RedirectView redirectView=new RedirectView("/student/add");
            return redirectView;
        }
        java.sql.Date sqlDate=new java.sql.Date(date1.getTime());
        student.setDateOfBirth(sqlDate);
        student.setGender(Character.toString(student.getGender().charAt(0)));
        System.out.println(student);
        Boolean res=studentService.add(student);
        if(res==false)
        {
            redirectAttributes.addFlashAttribute("message","some error occured");
            return new RedirectView("/student/add",true);
        }
        RedirectView redirectView=new RedirectView("/student/"+student.getUsername());

        return redirectView;

    }

    @GetMapping("/student/list")
    public String listStudents(Model model)
    {
        if(check()==false)return "redirect:/redirect";
        passLoggedInUser(model,null);
        List<Student> studentList=studentService.listAll();
        List<User> users=new ArrayList<>();
        for(Student student:studentList)
        {
            users.add(userRepository.findByUsername(student.getUsername()));
        }

        model.addAttribute("studentList",studentList);
        model.addAttribute("userList",users);
        return "studentList";
    }

    @GetMapping("/{username}/verify")
    public RedirectView verify(@PathVariable String username)
    {
        if(check()==false)return new RedirectView("/redirect",true);
        User user=userRepository.findByUsername(username);
        user.setActive(true);
        userRepository.update(user);
        String page=user.getRole();
        page=page.toLowerCase();
        RedirectView redirectView=new RedirectView("/"+page+"/list",true);
        return redirectView;
    }
    @GetMapping("/staff/add")
    public String addStaff(Model model,RedirectAttributes redirectAttributes,@ModelAttribute("message") String message)
    {
        if(check()==false)return "redirect:/redirect";
        passLoggedInUser(model,null);
       Employee employee=new Employee();
        String date=new String();
        model.addAttribute("date",date);
        model.addAttribute("employee",employee);
        model.addAttribute("message",message);
        return "addStaff";
    }

    @PostMapping("/staff/add")
    public RedirectView addStaff(RedirectAttributes redirectAttributes,Model model, @ModelAttribute("employee") Employee employee, @ModelAttribute("date") String date) throws ParseException {


    passLoggedInUser(null,redirectAttributes);
        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
        java.sql.Date sqlDate=new java.sql.Date(date1.getTime());
        User user=userRepository.findByUsername(employee.getUsername());
        if(!user.getRole().equalsIgnoreCase("staff"))
        {
            redirectAttributes.addFlashAttribute("message","user not registered as staff");
            RedirectView redirectView=new RedirectView("/staff/add");
            return redirectView;
        }
        employee.setDateOfBirth(sqlDate);
        Integer id=employeeService.add(employee);
        if(id==null)
        {
            RedirectView redirectView=new RedirectView("/staff/add");
            redirectAttributes.addFlashAttribute("message","invalid pincode");
            return redirectView;
        }
       staffRepository.add(id);
        RedirectView redirectView=new RedirectView("/staff/"+employee.getUsername(),true);
        return redirectView;

    }
    @GetMapping("/staff/list")
    public String listStaff(Model model)
    {
        if(check()==false)return "redirect:/redirect";
        passLoggedInUser(model,null);
        List<Employee> employees=employeeService.listStaff();
        List<User> users=new ArrayList<>();
        for(Employee employee:employees)
        {
            users.add(userRepository.findByUsername(employee.getUsername()));
        }

        model.addAttribute("employeeList",employees);
        model.addAttribute("userList",users);
        return "staffList";
    }

    @GetMapping("/teacher/add")
    public String addTeacher(Model model,RedirectAttributes redirectAttributes,@ModelAttribute("message") String message)
    {
        if(check()==false)return "redirect:/redirect";
        passLoggedInUser(model,null);
        Employee employee=new Employee();
        String date=new String();
        Teacher teacher=new Teacher();
        Integer subjectId = null;
        model.addAttribute("subject",subjectId);
        model.addAttribute("teacher",teacher);
        model.addAttribute("date",date);
        model.addAttribute("employee",employee);
        model.addAttribute("message",message);
        return "addTeacher";
    }

    @PostMapping("/teacher/add")
    public RedirectView addTeacher(RedirectAttributes redirectAttributes,Model model, @ModelAttribute("employee") Employee employee, @ModelAttribute("date") String date,@ModelAttribute("subject") int subject) throws ParseException {



        passLoggedInUser(null,redirectAttributes);
        System.out.println(employee);
        System.out.println(date);
        System.out.println(subject);
        User user=userRepository.findByUsername(employee.getUsername());
        System.out.println(user.getRole());
        if(!user.getRole().equalsIgnoreCase("Teacher"))
        {
            redirectAttributes.addFlashAttribute("message","user not registered as teacher");
            RedirectView redirectView=new RedirectView("/teacher/add");
            return redirectView;
        }
        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
        java.sql.Date sqlDate=new java.sql.Date(date1.getTime());
        employee.setDateOfBirth(sqlDate);
        Integer id=employeeService.add(employee);
        if(id==null)
        {
            RedirectView redirectView=new RedirectView("/teacher/add");
            redirectAttributes.addFlashAttribute("message","invalid pincode");
            return redirectView;
        }
        teacherRepository.add(id,subject);
        RedirectView redirectView=new RedirectView("/teacher/"+employee.getUsername(),true);
        return redirectView;

    }
    @GetMapping("/teacher/list")
    public String listTeacher(Model model)
    {
        if(check()==false)return "redirect:/redirect";
        passLoggedInUser(model,null);
        List<Employee> employees=employeeService.listTeacher();
        List<User> users=new ArrayList<>();
        List<String> subjects=new ArrayList<>();
        List<Teacher> teachers=new ArrayList<>();
        for(Employee employee:employees)
        {

            users.add(userRepository.findByUsername(employee.getUsername()));
            teachers.add(teacherRepository.getFromEmployeeId(employee.getEmployeeId()));
            Subject subject=subjectRepository.getSubjectById(teacherRepository.getFromEmployeeId(employee.getEmployeeId()).getSubjectId());
            if(subject==null)subjects.add(null);
            else subjects.add(subject.getName());
        }
        System.out.println(teachers);

        model.addAttribute("teacherList",teachers);
        model.addAttribute("subjects",subjects);
        model.addAttribute("employeeList",employees);
        model.addAttribute("userList",users);
        return "teacherList";
    }

    @GetMapping("/staff/{username}")
    public String staff(Model model,@PathVariable String username)
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
        User staff=userRepository.findByUsername(username);
        model.addAttribute("staff",staff);
        model.addAttribute("user",staff);

        Employee employee=employeeService.getFromUsername(username);
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
        List<EmployeeAttendance> employeeAttendances=employeeAttendanceRepository.getFiveRecentAttendanceByEmployeeId(employee.getEmployeeId());
        model.addAttribute("attendanceList",employeeAttendances);
        List<Payroll> payrolls=payrollRepository.getFiveRecentByEmployeeId(employee.getEmployeeId());
        model.addAttribute("payrollList",payrolls);

        return "staff";
    }

    @GetMapping("/staff/{username}/edit")
    public String edit(Model model, @PathVariable String username)
    {
        passLoggedInUser(model,null);
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null)
        {
            return "redirect:/redirect";
        }
        User user=userRepository.findByUsername(loggedInUSerName);
        User employee=userRepository.findByUsername(username);
        Employee employee1=employeeService.getFromUsername(username);
        if(user.getRole().equalsIgnoreCase("Student")||user.getRole().equalsIgnoreCase("teacher"))
        {return "redirect:/redirect";}



            model.addAttribute("employeeInfo",employee1);
            model.addAttribute("employee",employee);
        model.addAttribute("user",employee);


            return "employeeEdit";




    }

    @GetMapping("/staff/{username}/remove")
    public RedirectView remove(HttpServletRequest req, RedirectAttributes redir, @PathVariable String username)
    {

        passLoggedInUser(null,redir);
        RedirectView redirectView=new RedirectView("/");
        String loggedUsername=securityService.findLoggedInUsername();
        if(check()==false || loggedUsername.equals(username))
        {
            redir.addFlashAttribute("message","You do not have access to the requested page");
            return redirectView;
        }
        User employee=userRepository.findByUsername(username);


        userRepository.delete(employee);
        redir.addFlashAttribute("message","Operation Successful");
        return redirectView;

    }

    @PostMapping("/staff/{username}/edit")
    public RedirectView studentEdit(@ModelAttribute("employee") User user,@ModelAttribute("employeeInfo") Employee employeeInfo,@PathVariable String username,Model model, RedirectAttributes redir)
    {
        passLoggedInUser(null,redir);
        User prevUser=userRepository.findByUsername(username);
        Employee prevEmployee=employeeService.getFromUsername(username);

        userService.update(prevUser,user);
        employeeService.update(prevEmployee,employeeInfo);
        RedirectView redirectView=new RedirectView("/staff/"+username,true);
        redir.addFlashAttribute("message","Information was successfully updated");
        return redirectView;
    }

    @GetMapping("/staff/{username}/contact/{id}/edit")
    public String staffContactEdit(Model model,@PathVariable String username,@PathVariable int id)
    {

        if(check()==false)
        {
            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        User employee=userRepository.findByUsername(username);

        EmergencyContact contact=contactRepository.findByUserAndId(username,id);
        model.addAttribute("contact",contact);
        model.addAttribute("user",employee);

        return "contactEdit";

    }

    @PostMapping("/staff/{username}/contact/{id}/edit")
    public  RedirectView studentContactEdit(HttpServletRequest request, Model model,@ModelAttribute("contact") EmergencyContact contact,@PathVariable String username,@PathVariable int id, RedirectAttributes redir)  {


        passLoggedInUser(null,redir);
        contact.setContactId(id);
        contactRepository.update(contact);

        RedirectView redirectView=new RedirectView("/staff/"+username,true);
        redir.addFlashAttribute("message","Information was successfully updated");
        return redirectView;
    }

    @GetMapping("/staff/{username}/contact/{id}/remove")
    public String studentContactRemove(Model model,@PathVariable String username,@PathVariable int id)
    {

        Boolean flag=check();
        if(flag==false)
        {
            return "redirect:/redirect";
        }
        contactRepository.delete(username,id);

        return "redirect:/staff/"+username;


    }



    @GetMapping("/staff/{username}/contact/add")
    public String studentContactAdd(Model model,@PathVariable String username)
    {
        passLoggedInUser(model,null);
        Boolean flag=check();
        if(flag==false)
        {
            return "redirect:/redirect";
        }
        User employee=userRepository.findByUsername(username);
        EmergencyContact contact=new EmergencyContact();
        model.addAttribute("contact",contact);
        model.addAttribute("user",employee);
        return "addContact";
    }

    @PostMapping("/staff/{username}/contact/add")
    public String studentContactAdd(@ModelAttribute EmergencyContact contact,@PathVariable String username)
    {
        contactRepository.add(contact,username);
        return "redirect:/staff/"+username;
    }



    @GetMapping("/staff/{username}/contact/{id}/phone/add")
    public String contactPhoneAdd(Model model,@PathVariable String username,@PathVariable int id)
    {
        if(check()==false)
        {
            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        ContactPhoneNumber contactPhoneNumber=new ContactPhoneNumber();
        model.addAttribute("contactPhone",contactPhoneNumber);
        User user=userRepository.findByUsername(username);
        model.addAttribute("user",user);
        model.addAttribute("id",id);
        return "addContactPhone";
    }

    @PostMapping("/staff/{username}/contact/{id}/phone/add")
    public RedirectView contactPhoneAdd(Model model,@ModelAttribute ContactPhoneNumber contactPhoneNumber,@PathVariable String username,@PathVariable int id,RedirectAttributes redir)
    {
        contactPhoneNumber.setContactId(id);
        contactPhoneNumber.setUsername(username);
        passLoggedInUser(null,redir);
        Boolean res=contactPhoneService.add(contactPhoneNumber);
        if(res)
        {
            RedirectView redirectView=new RedirectView("/staff/"+username,true);

            return redirectView;
        }
        RedirectView redirectView=new RedirectView("/staff/"+username+"/contact/"+id+"/phone/add");
        redir.addFlashAttribute("message","invalid mobile number");
        return redirectView;

    }

    @GetMapping("/staff/{username}/contact/{id}/edit/{phone}")
    public String contactPhoneEdit(Model model,@ModelAttribute("message") String message,@PathVariable String username,@PathVariable int id,@PathVariable String phone) {

        if(check()==false)
        {
            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        model.addAttribute("message",message);
        model.addAttribute("id",id);
        ContactPhoneNumber contactPhoneNumber=new ContactPhoneNumber(phone,username,id);
        User user=userRepository.findByUsername(username);
        model.addAttribute("user",user);
        model.addAttribute("phone",contactPhoneNumber);
        return "contactPhoneEdit";
    }

    @PostMapping("/staff/{username}/contact/{id}/edit/{phone}")
    public RedirectView contactPhoneEdit(@ModelAttribute ContactPhoneNumber contactPhoneNumber,@PathVariable String phone,@PathVariable int id,@PathVariable String username,RedirectAttributes redir)
    {

        passLoggedInUser(null,redir);
        contactPhoneNumber.setContactId(id);
        contactPhoneNumber.setUsername(username);
        Boolean res=contactPhoneService.update(phone,contactPhoneNumber);
        RedirectView redirectView;
        if(res) {
            redirectView = new RedirectView("/staff/" + username, true);
        }
        else {
            redirectView = new RedirectView("/staff/" + username + "/contact/" + id + "/phone/" + phone + "/edit", true);
            redir.addFlashAttribute("message","invalid mobile number");
        }
        return redirectView;

    }

    @GetMapping("/staff/{username}/contact/{id}/remove/{phone}")
    public RedirectView contactPhoneRemove(RedirectAttributes redir,Model model,@PathVariable String username,@PathVariable int id,@PathVariable String phone) {

        if(check()==false)
        {
            return new RedirectView("/redirect",true);
        }
        passLoggedInUser(null,redir);
        redir.addFlashAttribute("message","Phone number removed successfully");
        ContactPhoneNumber contactPhoneNumber=new ContactPhoneNumber(phone,username,id);
        contactPhoneRepository.delete(contactPhoneNumber);
        User user=userRepository.findByUsername(username);
        redir.addFlashAttribute("user",user);
        RedirectView redirectView=new RedirectView("/staff/"+username,true);
        return redirectView;

    }

    @GetMapping("/staff/{username}/phone/{phone}/edit")
    public String studentPhoneEdit(Model model,@ModelAttribute("message") String message,@PathVariable String username,@PathVariable String phone)
    {
        if(check()==false)
        {
            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        model.addAttribute("message",message);
        User user=userRepository.findByUsername(username);
        model.addAttribute("user",user);
        UserPhoneNumber userPhoneNumber=new UserPhoneNumber();
        userPhoneNumber.setPhoneNumber(phone);
        model.addAttribute("phone",userPhoneNumber);
        return "userPhoneEdit";
    }

    @PostMapping("/staff/{username}/phone/{phone}/edit")
    public RedirectView studentPhoneEdit(RedirectAttributes redir,@ModelAttribute UserPhoneNumber userPhoneNumber,@PathVariable String username,@PathVariable String phone)
    {

        passLoggedInUser(null,redir);
        Boolean res=userPhoneService.update(username,phone,userPhoneNumber.getPhoneNumber());

        RedirectView redirectView;
        if(res) {

            redirectView = new RedirectView("/staff/" + username, true);
        }
        else {
            redirectView = new RedirectView("/staff/" + username + "/phone/" + phone + "/edit", true);
            redir.addFlashAttribute("message","invalid mobile number");
        }
        return redirectView;
    }

    @GetMapping("/staff/{username}/phone/{phone}/remove")
    public RedirectView userPhoneRemove(RedirectAttributes redir,Model model,@PathVariable String username,@PathVariable String phone) {
        if(check()==false)
        {
            return new RedirectView("/redirect",true);
        }
        passLoggedInUser(null,redir);
        redir.addFlashAttribute("message","Phone number removed successfully");
        userPhoneRepository.remove(username,phone);
        RedirectView redirectView=new RedirectView("/staff/"+username,true);
        return redirectView;

    }

    @GetMapping("/staff/{username}/phone/add")
    public String  userPhoneAdd(Model model,@PathVariable String username)
    {
        if(check()==false)
        {
            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        String loggedInUsername=securityService.findLoggedInUsername();
        if(loggedInUsername==null)return "redirect:/redirect";
        User user=userRepository.findByUsername(loggedInUsername);
        if(user.getRole().equalsIgnoreCase("Student"))return "redirect:/redirect";
        UserPhoneNumber userPhoneNumber=new UserPhoneNumber();
        model.addAttribute("userPhone",userPhoneNumber);
        User user1=userRepository.findByUsername(username);
        model.addAttribute("user",user1);
        return "addUserPhone";
    }

    @PostMapping("/staff/{username}/phone/add")
    public RedirectView userPhoneAdd(Model model,@ModelAttribute UserPhoneNumber userPhoneNumber,@PathVariable String username,RedirectAttributes redir)
    {

        passLoggedInUser(null,redir);
        userPhoneNumber.setUsername(username);
        Boolean res=userPhoneService.add(userPhoneNumber);
        if(res)
        {
            RedirectView redirectView=new RedirectView("/staff/"+username,true);
            return redirectView;
        }

        RedirectView redirectView=new RedirectView("/staff/"+username+"/phone/add",true);
        redir.addFlashAttribute("message","invalid mobile number");
        return redirectView;

    }

    @GetMapping("/staff/{username}/email/add")
    public String  userEmailAdd(Model model,@PathVariable String username)
    {
        if(check()==false)
        {
            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        String loggedInUsername=securityService.findLoggedInUsername();
        if(loggedInUsername==null)return "redirect:/redirect";
        User user=userRepository.findByUsername(loggedInUsername);
        if(user.getRole().equalsIgnoreCase("student"))return "redirect:/redirect";
        UserEmail userEmail=new UserEmail();
        userEmail.setUsername(username);
        model.addAttribute("userEmail",userEmail);
        User user1=userRepository.findByUsername(username);
        model.addAttribute("user",user1);

        return "addUserEmail";
    }

    @PostMapping("/staff/{username}/email/add")
    public RedirectView useEmailAdd(Model model,@ModelAttribute UserEmail userEmail,@PathVariable String username,RedirectAttributes redir)
    {
        passLoggedInUser(null,redir);
        userEmail.setUsername(username);
        boolean res=userEmailService.add(userEmail);
        if(res)
        {
            RedirectView redirectView=new RedirectView("/staff/"+username);

            return redirectView;
        }
        RedirectView redirectView=new RedirectView("/staff/"+username+"/email/add");
        redir.addFlashAttribute("message","invalid email");
        return redirectView;

    }

    @GetMapping("/staff/{username}/email/{email}/edit")
    public String staffEmailEdit(Model model,@ModelAttribute("message") String message,@PathVariable String username,@PathVariable String email)
    {
        if(check()==false)
        {
            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        model.addAttribute("message",message);
        model.addAttribute("username",username);
        UserEmail userEmail=new UserEmail();
        userEmail.setUsername(username);
        userEmail.setEmail(email);
        User user=userRepository.findByUsername(username);
        model.addAttribute("user",user);
        model.addAttribute("email",userEmail);
        return "userEmailEdit";
    }

    @PostMapping("/staff/{username}/email/{email}/edit")
    public RedirectView staffEmailEdit(RedirectAttributes redir,@ModelAttribute UserEmail userEmail,@PathVariable String username,@PathVariable String email)
    {

        passLoggedInUser(null,redir);
        Boolean res=userEmailService.update(username,email,userEmail.getEmail());

        RedirectView redirectView;
        if(res) {

            redirectView = new RedirectView("/staff/" + username, true);
        }
        else {
            redirectView = new RedirectView("/staff/" + username + "/email/" + email + "/edit", true);
            redir.addFlashAttribute("message","invalid email");
        }
        return redirectView;
    }


    @GetMapping("/staff/{username}/email/{email}/remove")
    public RedirectView userEmailRemove(RedirectAttributes redir,Model model,@PathVariable String username,@PathVariable String email) {

        if(check()==false)
        {
            return new RedirectView("/redirect",true);
        }
        passLoggedInUser(null,redir);
        redir.addFlashAttribute("message","Email removed successfully");
        userEmailRepository.remove(username,email);
        User user=userRepository.findByUsername(username);
        redir.addFlashAttribute("user",user);
        RedirectView redirectView=new RedirectView("/staff/"+username,true);
        return redirectView;

    }





}
