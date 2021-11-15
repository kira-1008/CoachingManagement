package com.example.coachingmanagement.Controllers;

import com.example.coachingmanagement.Models.*;
import com.example.coachingmanagement.Repository.*;
import com.example.coachingmanagement.Services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {
   UserRepository userRepository;
    SecurityService securityService;
UserPhoneRepository userPhoneRepository;
    UserEmailRepository userEmailRepository;
    StudentRepository studentRepository;
    UserService userService;
    UserEmailService userEmailService;
    UserPhoneService userPhoneService;
    ContactRepository contactRepository;
    ContactPhoneRepository contactPhoneRepository;
    ContactPhoneService contactPhoneService;
    StudentService studentService;
    BatchRepository batchRepository;
    SubjectRepository subjectRepository;
    TeacherRepository teacherRepository;
    EnrollmentRepository enrollmentRepository;
    EnrollmentService enrollmentService;
    StudentTestDetailsRepository studentTestDetailsRepository;
    TestRepository testRepository;

    public StudentController(UserRepository userRepository,
                             SecurityService securityService,
                             UserPhoneRepository userPhoneRepository,
                             UserEmailRepository userEmailRepository,
                             StudentRepository studentRepository,
                             UserService userService,
                             UserEmailService userEmailService,
                             UserPhoneService userPhoneService,
                             ContactRepository contactRepository,
                             ContactPhoneRepository contactPhoneRepository,
                             ContactPhoneService contactPhoneService,
                             StudentService studentService,
                             BatchRepository batchRepository,
                             SubjectRepository subjectRepository,
                             TeacherRepository teacherRepository,
                             EnrollmentRepository enrollmentRepository,
                             EnrollmentService enrollmentService,
                             StudentTestDetailsRepository studentTestDetailsRepository,
                             TestRepository testRepository) {
        this.userRepository = userRepository;
        this.securityService = securityService;
        this.userPhoneRepository = userPhoneRepository;
        this.userEmailRepository = userEmailRepository;
        this.studentRepository = studentRepository;
        this.userService = userService;
        this.userEmailService = userEmailService;
        this.userPhoneService = userPhoneService;
        this.contactRepository = contactRepository;
        this.contactPhoneRepository = contactPhoneRepository;
        this.contactPhoneService = contactPhoneService;
        this.studentService = studentService;
        this.batchRepository = batchRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.enrollmentRepository=enrollmentRepository;
        this.enrollmentService=enrollmentService;
        this.studentTestDetailsRepository=studentTestDetailsRepository;
        this.testRepository=testRepository;
    }

    @GetMapping("/self")
    public RedirectView self(RedirectAttributes redirectAttributes)
    {
        passLoggedInUser(null,redirectAttributes);


        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null)
        {


            return new RedirectView("/redirect",true);
        }

        User user = userRepository.findByUsername(loggedInUSerName);

        String role=user.getRole();
        role=role.toLowerCase();
        System.out.println(role);

        return new RedirectView("/"+role+"/"+loggedInUSerName,true);

    }

    public void passLoggedInUser(Model model,RedirectAttributes redirectAttributes)
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
        if(user!=null && user.getRole().equalsIgnoreCase("Staff")||user.getRole().equalsIgnoreCase("Admin"))return true;
        return false;
    }

    Boolean checkCorrectStudentAndStaff(String username)
    {
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null)return false;
        User user = userRepository.findByUsername(loggedInUSerName);
        if(user==null)return false;
        if(user.getRole().equals("student") && loggedInUSerName.equals(username))return true;
        if(user.getRole().equalsIgnoreCase("staff") || user.getRole().equalsIgnoreCase("admin"))return true;
        return false;
    }



    @RequestMapping("/redirect")
    public RedirectView redirectView(HttpServletRequest req, RedirectAttributes redir)
    {
        passLoggedInUser(null,redir);
        String loggedInUSerName = securityService.findLoggedInUsername();
        RedirectView redirectView= new RedirectView("/",true);
        if(loggedInUSerName==null)
        {

            redir.addFlashAttribute("message","Please login to view the page");
            return redirectView;
        }
        redir.addFlashAttribute("message","You do not have access to the requested page");
        return redirectView;
    }

    @GetMapping("/student/{username}")
    public String studentPage(Model model,@PathVariable String username)
    {
        passLoggedInUser(model,null);
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null)
        {

            return "redirect:/redirect";
        }
        User user = userRepository.findByUsername(loggedInUSerName);
        User student=userRepository.findByUsername(username);
        if(loggedInUSerName != username && user.getRole() == "student"){

            return "redirect:/redirect";
        }
        Student student1=studentRepository.findByUsername(username);
        model.addAttribute("user",student);
        List<EmergencyContact> emergencyContacts= contactRepository.findByUsername(username);
        model.addAttribute("student",student1);
        model.addAttribute("contacts",emergencyContacts);
        List<String> phoneNumberList=userPhoneRepository.findByUsernameNumbers(username);
        List<String> emailList=userEmailRepository.findByUsernameString(username);

        model.addAttribute("phonelist",phoneNumberList);
        model.addAttribute("emailList",emailList);
        List<Batch> batches=batchRepository.getBatchesByRollNo(student1.getRollNumber());

        model.addAttribute("batches",batches);
        List<Subject> subjects=new ArrayList<>();
        List<User> teachers=new ArrayList<>();
        for(Batch batch:batches)
        {
            subjects.add(subjectRepository.getSubjectById(batch.getSubjectId()));
            teachers.add(teacherRepository.getTeacherUserFromBatch(batch.getBatchId()));
        }
        model.addAttribute("subjects",subjects);
        model.addAttribute("teachers",teachers);
        List<StudentTestDetails> studentTestDetailsList=studentTestDetailsRepository.getRecentFiveTestsByRollNo(student1.getRollNumber());
        List<Test> tests=new ArrayList<>();
        List<String> testSubject=new ArrayList<>();
        for(StudentTestDetails i:studentTestDetailsList)
        {
            tests.add(testRepository.findTestById(i.getTestNumber(),i.getSubjectId()));
            testSubject.add(subjectRepository.getSubjectById(i.getSubjectId()).getName());
        }
        model.addAttribute("testSubjects",testSubject);
        model.addAttribute("testList",tests);
        model.addAttribute("testDetails",studentTestDetailsList);
        List<List<String>> phoneNumbers=new ArrayList<>();
        for(EmergencyContact contact:emergencyContacts)
        {
            List<String> contactPhoneRepositoryNumbers=contactPhoneRepository.findNumbers(username,contact.getContactId());
            phoneNumbers.add(contactPhoneRepositoryNumbers);
        }
        model.addAttribute("contactPhone",phoneNumbers);

        return "student";
    }
    @GetMapping("/student/{username}/edit")
    public String edit(Model model, @PathVariable String username)
    {
        passLoggedInUser(model,null);
        String loggedInUSerName = securityService.findLoggedInUsername();
        if(loggedInUSerName==null)
        {
            return "redirect:/redirect";
        }
        User user=userRepository.findByUsername(loggedInUSerName);
        User student=userRepository.findByUsername(username);
        Student student1=studentRepository.findByUsername(username);

        if(loggedInUSerName.equalsIgnoreCase(username) || user.getRole().equalsIgnoreCase("Staff")||user.getRole().equalsIgnoreCase("Admin"))
        {

            model.addAttribute("studentInfo",student1);
            model.addAttribute("student",student);


            return "studentEdit";
        }

        return "redirect:/redirect";

    }

    @GetMapping("/student/{username}/remove")
    public RedirectView remove(HttpServletRequest req, RedirectAttributes redir,@PathVariable String username)
    {

        passLoggedInUser(null,redir);
       RedirectView redirectView=new RedirectView("/");
        if(check()==false)
        {
            redir.addFlashAttribute("message","You do not have access to the requested page");
            return redirectView;
        }
        User student=userRepository.findByUsername(username);


          userRepository.delete(student);
          redir.addFlashAttribute("message","Operation Successful");
          return redirectView;

    }

    @PostMapping("/student/{username}/edit")
    public RedirectView studentEdit(@ModelAttribute("student") User user,@ModelAttribute("studentInfo") Student studentInfo,@PathVariable String username,Model model, RedirectAttributes redir)
    {
        if(checkCorrectStudentAndStaff(username)==false)
        {
            return new RedirectView("/redirect",true);
        }
        passLoggedInUser(null,redir);
        User prevUser=userRepository.findByUsername(username);
        Student prevStudent=studentRepository.findByUsername(username);

        userService.update(prevUser,user);
        studentService.update(prevStudent,studentInfo);
        RedirectView redirectView=new RedirectView("/student/"+username,true);
        redir.addFlashAttribute("message","Information was successfully updated");
        return redirectView;
    }

    @GetMapping("/student/{username}/contact/{id}/edit")
    public String studentContactEdit(Model model,@PathVariable String username,@PathVariable int id)
    {


       if(check()==false)
       {
           return "redirect:/redirect";
       }
       passLoggedInUser(model,null);
        User student=userRepository.findByUsername(username);

            EmergencyContact contact=contactRepository.findByUserAndId(username,id);
            model.addAttribute("contact",contact);
            model.addAttribute("user",student);

            return "contactEdit";

    }

    @PostMapping("/student/{username}/contact/{id}/edit")
    public  RedirectView studentContactEdit(HttpServletRequest request, Model model,@ModelAttribute("contact") EmergencyContact contact,@PathVariable String username,@PathVariable int id, RedirectAttributes redir)  {


        passLoggedInUser(null,redir);
        contact.setContactId(id);
        contactRepository.update(contact);

        RedirectView redirectView=new RedirectView("/student/"+username,true);
        redir.addFlashAttribute("message","Information was successfully updated");
        return redirectView;
    }

    @GetMapping("/student/{username}/contact/{id}/remove")
    public String studentContactRemove(Model model,@PathVariable String username,@PathVariable int id)
    {

        Boolean flag=check();
        if(flag==false)
        {
            return "redirect:/redirect";
        }
            contactRepository.delete(username,id);

            return "redirect:/student/"+username;


    }



    @GetMapping("/student/{username}/contact/add")
    public String studentContactAdd(Model model,@PathVariable String username)
    {
        passLoggedInUser(model,null);
        Boolean flag=check();
        if(flag==false)
        {
            return "redirect:/redirect";
        }
        User student=userRepository.findByUsername(username);
        EmergencyContact contact=new EmergencyContact();
        model.addAttribute("contact",contact);
        model.addAttribute("user",student);
        return "addContact";
    }

    @PostMapping("/student/{username}/contact/add")
    public String studentContactAdd(@ModelAttribute EmergencyContact contact,@PathVariable String username)
    {
        contactRepository.add(contact,username);
        return "redirect:/student/"+username;
    }



    @GetMapping("/student/{username}/contact/{id}/phone/add")
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

    @PostMapping("/student/{username}/contact/{id}/phone/add")
    public RedirectView contactPhoneAdd(Model model,@ModelAttribute ContactPhoneNumber contactPhoneNumber,@PathVariable String username,@PathVariable int id,RedirectAttributes redir)
    {
        contactPhoneNumber.setContactId(id);
        contactPhoneNumber.setUsername(username);
        passLoggedInUser(null,redir);
       Boolean res=contactPhoneService.add(contactPhoneNumber);
       if(res)
       {
           RedirectView redirectView=new RedirectView("/student/"+username);

           return redirectView;
       }
       RedirectView redirectView=new RedirectView("student/"+username+"/contact/"+id+"/phone/add");
       redir.addFlashAttribute("message","invalid mobile number");
       return redirectView;

    }

    @GetMapping("/student/{username}/contact/{id}/edit/{phone}")
    public String contactPhoneEdit(Model model,@ModelAttribute("message") String message,@PathVariable String username,@PathVariable int id,@PathVariable String phone) {

        if(check()==false)
        {
            return "redirect:/redirect";
        }
    passLoggedInUser(model,null);
        model.addAttribute("message",message);
        User user=userRepository.findByUsername(username);
        model.addAttribute("user",user);
    model.addAttribute("id",id);
    ContactPhoneNumber contactPhoneNumber=new ContactPhoneNumber(phone,username,id);
    model.addAttribute("phone",contactPhoneNumber);
    return "contactPhoneEdit";
    }

    @PostMapping("/student/{username}/contact/{id}/edit/{phone}")
    public RedirectView contactPhoneEdit(@ModelAttribute ContactPhoneNumber contactPhoneNumber,@PathVariable String phone,@PathVariable int id,@PathVariable String username,RedirectAttributes redir)
    {

        passLoggedInUser(null,redir);
        contactPhoneNumber.setContactId(id);
        contactPhoneNumber.setUsername(username);
      Boolean res=contactPhoneService.update(phone,contactPhoneNumber);
        RedirectView redirectView;
        if(res) {
            redirectView = new RedirectView("/student/" + username, true);
        }
      else {
            redirectView = new RedirectView("/student/" + username + "/contact/" + id + "/phone/" + phone + "/edit", true);
            redir.addFlashAttribute("message","invalid mobile number");
        }
        return redirectView;

    }

    @GetMapping("/student/{username}/contact/{id}/remove/{phone}")
    public RedirectView contactPhoneRemove(RedirectAttributes redir,Model model,@PathVariable String username,@PathVariable int id,@PathVariable String phone) {

        if(check()==false)
        {
            return new RedirectView("/redirect",true);
        }
        passLoggedInUser(null,redir);
        redir.addFlashAttribute("message","Phone number removed successfully");
        ContactPhoneNumber contactPhoneNumber=new ContactPhoneNumber(phone,username,id);
       contactPhoneRepository.delete(contactPhoneNumber);
       RedirectView redirectView=new RedirectView("/student/"+username,true);
       return redirectView;

    }

    @GetMapping("/student/{username}/phone/{phone}/edit")
    public String studentPhoneEdit(Model model,@ModelAttribute("message") String message,@PathVariable String username,@PathVariable String phone)
    {
        if(checkCorrectStudentAndStaff(username)==false)
        {
            return "redirect:/redirect";
        }

            passLoggedInUser(model,null);
            model.addAttribute("message",message);
            UserPhoneNumber userPhoneNumber=new UserPhoneNumber();
            userPhoneNumber.setPhoneNumber(phone);
            User user1=userRepository.findByUsername(username);
            model.addAttribute("user",user1);
            model.addAttribute("phone",userPhoneNumber);
            return "userPhoneEdit";


    }

    @PostMapping("/student/{username}/phone/{phone}/edit")
    public RedirectView studentPhoneEdit(RedirectAttributes redir,@ModelAttribute UserPhoneNumber userPhoneNumber,@PathVariable String username,@PathVariable String phone)
    {


        passLoggedInUser(null,redir);
        Boolean res=userPhoneService.update(username,phone,userPhoneNumber.getPhoneNumber());

        RedirectView redirectView;
        if(res) {

            redirectView = new RedirectView("/student/" + username, true);
        }
        else {
            redirectView = new RedirectView("/student/" + username + "/phone/" + phone + "/edit", true);
            redir.addFlashAttribute("message","invalid mobile number");
        }
        return redirectView;
    }

    @GetMapping("/student/{username}/phone/{phone}/remove")
    public RedirectView userPhoneRemove(RedirectAttributes redir,Model model,@PathVariable String username,@PathVariable String phone) {

        if(checkCorrectStudentAndStaff(username)==false)
        {
            return new RedirectView("/redirect",true);
        }
            passLoggedInUser(null, redir);
            redir.addFlashAttribute("message", "Phone number removed successfully");
            userPhoneRepository.remove(username, phone);
            RedirectView redirectView = new RedirectView("/student/" + username, true);
            return redirectView;

    }

    @GetMapping("/student/{username}/phone/add")
    public String  userPhoneAdd(Model model,@PathVariable String username)
    {
        if(checkCorrectStudentAndStaff(username)==false)
        {
            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);

        UserPhoneNumber userPhoneNumber=new UserPhoneNumber();
        model.addAttribute("userPhone",userPhoneNumber);
        User user1=userRepository.findByUsername(username);
        model.addAttribute("user",user1);
        return "addUserPhone";
    }

    @PostMapping("/student/{username}/phone/add")
    public RedirectView userPhoneAdd(Model model,@ModelAttribute UserPhoneNumber userPhoneNumber,@PathVariable String username,RedirectAttributes redir)
    {

        passLoggedInUser(null,redir);
        userPhoneNumber.setUsername(username);
        Boolean res=userPhoneService.add(userPhoneNumber);
        System.out.println(res);
        if(res)
        {
            RedirectView redirectView=new RedirectView("/student/"+username);
            redir.addFlashAttribute("loginStatus",true);
            return redirectView;
        }
        else
        {RedirectView redirectView=new RedirectView("/student/"+username+"/phone/add",true);
        redir.addFlashAttribute("message","invalid mobile number");
        return redirectView;}

    }

    @GetMapping("/student/{username}/email/add")
    public String  userEmailAdd(Model model,@PathVariable String username)
    {
        if(checkCorrectStudentAndStaff(username)==false)
        {
            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);

         UserEmail userEmail=new UserEmail();
        userEmail.setUsername(username);
        User user1=userRepository.findByUsername(username);
        model.addAttribute("user",user1);
        model.addAttribute("userEmail",userEmail);

        return "addUserEmail";
    }

    @PostMapping("/student/{username}/email/add")
    public RedirectView useEmailAdd(Model model,@ModelAttribute UserEmail userEmail,@PathVariable String username,RedirectAttributes redir)
    {
        passLoggedInUser(null,redir);
       userEmail.setUsername(username);
        boolean res=userEmailService.add(userEmail);
        if(res)
        {
            RedirectView redirectView=new RedirectView("/student/"+username);
            redir.addFlashAttribute("loginStatus",true);
            return redirectView;
        }
        RedirectView redirectView=new RedirectView("/student/"+username+"/email/add");
        redir.addFlashAttribute("message","invalid email");
        return redirectView;

    }

    @GetMapping("/student/{username}/email/{email}/edit")
    public String studentEmailEdit(Model model,@ModelAttribute("message") String message,@PathVariable String username,@PathVariable String email)
    {
        if(checkCorrectStudentAndStaff(username)==false)
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

    @PostMapping("/student/{username}/email/{email}/edit")
    public RedirectView studentEmailEdit(RedirectAttributes redir,@ModelAttribute UserEmail userEmail,@PathVariable String username,@PathVariable String email)
    {

        passLoggedInUser(null,redir);
        Boolean res=userEmailService.update(username,email,userEmail.getEmail());

        RedirectView redirectView;
        if(res) {

            redirectView = new RedirectView("/student/" + username, true);
        }
        else {
            redirectView = new RedirectView("/student/" + username + "/email/" + email + "/edit", true);
            redir.addFlashAttribute("message","invalid email");
        }
        return redirectView;
    }


    @GetMapping("/student/{username}/email/{email}/remove")
    public RedirectView userEmailRemove(RedirectAttributes redir,Model model,@PathVariable String username,@PathVariable String email) {

        if(checkCorrectStudentAndStaff(username)==false)
        {
            return new RedirectView("/redirect",true);
        }

        passLoggedInUser(null,redir);
        redir.addFlashAttribute("message","Email removed successfully");
        userEmailRepository.remove(username,email);
        RedirectView redirectView=new RedirectView("/student/"+username,true);
        return redirectView;

    }

    @GetMapping("student/{username}/batch/add")
    public String batchAdd(Model model,@PathVariable String username)
    {
        if(check()==false)
        {
            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        List<Batch> allBatches=batchRepository.getAllBatches();
        Batch batch=new Batch();
        model.addAttribute("batch",batch);
        model.addAttribute("batches",allBatches);
        User user=userRepository.findByUsername(username);
        model.addAttribute("user",user);
        return "setBatch";
    }

    @PostMapping("/student/{username}/batch/add")
    public RedirectView batchAdd(RedirectAttributes redirectAttributes,@ModelAttribute("batch") Batch batch,@PathVariable String username)
    {
        passLoggedInUser(null,redirectAttributes);


        int rollNo=studentRepository.findByUsername(username).getRollNumber();

        Boolean res=enrollmentService.add(rollNo,batch.getBatchId());
        if(res==true)
        {
            redirectAttributes.addFlashAttribute("message","added succesfully");
        }
        else
        {
            redirectAttributes.addFlashAttribute("message","batch already present");
        }
        RedirectView redirectView=new RedirectView("/student/"+username,true);
        redirectAttributes.addFlashAttribute("loginStatus",true);
        return redirectView;

    }

    @GetMapping("/student/{username}/tests")
    public String getTests(@PathVariable String username,Model model){
        passLoggedInUser(model,null);
        String loggedInUsername=securityService.findLoggedInUsername();
        if(loggedInUsername==null)
        {
            return "redirect:/redirect";
        }
        User user=userRepository.findByUsername(loggedInUsername);
        if(user.getRole().equalsIgnoreCase("Student") && !loggedInUsername.equals(username))
        {
            return "redirect:/redirect";
        }
        int roll=studentRepository.findByUsername(username).getRollNumber();
        List<StudentTestDetails> studentTestDetailsList=studentTestDetailsRepository.getAllTests(roll);
        List<Test> tests=new ArrayList<>();
        List<String> testSubject=new ArrayList<>();
        for(StudentTestDetails i:studentTestDetailsList)
        {
            tests.add(testRepository.findTestById(i.getTestNumber(),i.getSubjectId()));
            testSubject.add(subjectRepository.getSubjectById(i.getSubjectId()).getName());
        }
        model.addAttribute("testDetails",studentTestDetailsList);
        model.addAttribute("testList",tests);
        model.addAttribute("testSubjects",testSubject);

        return "testStudent";
    }




}

