package com.example.coachingmanagement.Controllers;

import com.example.coachingmanagement.Models.*;
import com.example.coachingmanagement.Repository.*;
import com.example.coachingmanagement.Services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class miscellaneousController {

    SubjectRepository subjectRepository;
    SecurityService securityService;
    UserRepository userRepository;
    EmployeeRepository employeeRepository;
    TeacherRepository teacherRepository;
    BatchRepository batchRepository;
    TestRepository testRepository;
    PayrollRepository payrollRepository;

    @Autowired
    public miscellaneousController(SubjectRepository subjectRepository,SecurityService securityService,UserRepository userRepository,
                                   EmployeeRepository employeeRepository,TeacherRepository teacherRepository,BatchRepository batchRepository,
                                   TestRepository testRepository,PayrollRepository payrollRepository) {
        this.subjectRepository = subjectRepository;
        this.securityService=securityService;
        this.userRepository=userRepository;
        this.employeeRepository=employeeRepository;
        this.teacherRepository=teacherRepository;
        this.batchRepository=batchRepository;
        this.testRepository=testRepository;
        this.payrollRepository=payrollRepository;
    }

    Boolean checknotStudent()
    {
        String loggedInusername=securityService.findLoggedInUsername();
        if(loggedInusername==null)return false;
        User user=userRepository.findByUsername(loggedInusername);
        if(user==null || user.getRole().equalsIgnoreCase("Student"))return false;
        return true;
    }

    Boolean checkStaff()
    {
        String loggedInusername=securityService.findLoggedInUsername();
        if(loggedInusername==null)return false;
        User user=userRepository.findByUsername(loggedInusername);
        if(user==null || user.getRole().equals("Student") || user.getRole().equalsIgnoreCase("teacher"))return false;
        return true;
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

    @GetMapping("/subjects")
    public String subjects(Model model)
    {
        if(checknotStudent()==false)return "redirect:/redirect";
        passLoggedInUser(model,null);
        List<Subject> subjectList=subjectRepository.listAll();
        List<String> headofDepartments=new ArrayList<>();
        for(Subject subject:subjectList)
        {
            Teacher teacher=teacherRepository.getTeacherFromId(subject.getHeadOfDepartment());
            if(teacher==null)
            {
                headofDepartments.add(null);
                continue;
            }
            Employee employee=employeeRepository.getEmployeeById(teacher.getEmployeeId());
            User user=userRepository.findByUsername(employee.getUsername());
            headofDepartments.add(user.getFirstName()+" "+user.getMiddleName()+" "+user.getLastName());

        }
        model.addAttribute("headOfDepartments",headofDepartments);
        model.addAttribute("subjects",subjectList);
        return "subjectList";
    }

    @GetMapping("/subjects/add")
    public String addsubjects(Model model)
    {
        if(checkStaff()==false)return "redirect:/redirect";
        passLoggedInUser(model,null);
        Subject subject=new Subject();
        model.addAttribute("subject",subject);
        return "addSubject";
    }

    @PostMapping("/subjects/add")
    public RedirectView addsubjectsPost(RedirectAttributes redirectAttributes, @ModelAttribute("subject") Subject subject)
    {
        passLoggedInUser(null,redirectAttributes);
        subjectRepository.add(subject);
        RedirectView redirectView=new RedirectView("/subjects",true);
        return redirectView;

    }

    @GetMapping("/subjects/{id}/edit")
    public String subjectEdit(@PathVariable int id,Model model)
    {
        if(checkStaff()==false)return "redirect:/redirect";
        passLoggedInUser(model,null);
        Subject subject=subjectRepository.getSubjectById(id);
        model.addAttribute("subject",subject);
        System.out.println(subject.getSubjectId());
        return "subjectEdit";
    }

    @PostMapping("/subjects/{id}/edit")
    public RedirectView subjectEditPost(@ModelAttribute("subject") Subject subject,RedirectAttributes redirectAttributes,@PathVariable int id)
    {

        passLoggedInUser(null,redirectAttributes);
        if(subject.getHeadOfDepartment()==0)
        {
            subject.setHeadOfDepartment(null);
            subjectRepository.update(subject);

        }
        subject.setSubjectId(id);
        Teacher teacher=teacherRepository.getTeacherFromId(subject.getHeadOfDepartment());
        System.out.println(teacher.getSubjectId() + " " + subject.getSubjectId());
        if(teacher.getSubjectId() != subject.getSubjectId())
        {
            redirectAttributes.addFlashAttribute("message","teacher registered for another subject");
            return new RedirectView("/subjects/"+id+"/edit");
        }
        subjectRepository.update(subject);
        return new RedirectView("/subjects",true);
    }

    @GetMapping("/subjects/{id}/remove")
    public RedirectView removeSubject(@PathVariable int id,RedirectAttributes redirectAttributes)
    {
        if(checkStaff()==false)return new RedirectView("/redirect",true);
        passLoggedInUser(null,redirectAttributes);
        subjectRepository.remove(id);
        return new RedirectView("/subjects",true);

    }

    @GetMapping("/batches")
    public String batches(Model model)
    {
        if(checknotStudent()==false)return "redirect:/redirect";
        passLoggedInUser(model,null);
        List<Batch> batchList=batchRepository.getAllBatches();
        List<String> subjects=new ArrayList<>();
        List<String> teachers=new ArrayList<>();
        for(Batch batch:batchList)
        {
          Subject subject=subjectRepository.getSubjectById(batch.getSubjectId());
          if(subject==null)subjects.add(null);
          else subjects.add(subject.getName());
          Teacher teacher=teacherRepository.getTeacherFromId(batch.getTeacherId());
          if(teacher==null)
          {
              teachers.add(null);
              continue;
          }
          User teacheruser=userRepository.findByUsername(employeeRepository.getEmployeeById(teacher.getEmployeeId()).getUsername());
          teachers.add(teacheruser.getFirstName()+" "+teacheruser.getMiddleName()+" "+teacheruser.getLastName());


        }

        model.addAttribute("batches",batchList);
        model.addAttribute("subjects",subjects);
        model.addAttribute("teachers",teachers);
        return "batchList";
    }

    @GetMapping("/batches/add")
    public String addbatches(Model model)
    {
        if(checkStaff()==false)return "redirect:/redirect";
        passLoggedInUser(model,null);
        Batch batch=new Batch();
        model.addAttribute("batch",batch);
        return "addBatch";
    }

    @PostMapping("/batches/add")
    public RedirectView addsubjectsPost(RedirectAttributes redirectAttributes, @ModelAttribute("batch") Batch batch)
    {
        passLoggedInUser(null,redirectAttributes);
        System.out.println(batch);
        if(batch.getSubjectId()==0)
        {
            batch.setSubjectId(null);
        }
        if(batch.getTeacherId()==0)
        {
            batch.setTeacherId(null);
        }
        batchRepository.add(batch);
        RedirectView redirectView=new RedirectView("/batches",true);
        return redirectView;

    }

    @GetMapping("/batches/{id}/edit")
    public String batchEdit(@PathVariable int id,Model model)
    {
        if(checkStaff()==false)return "redirect:/redirect";
        passLoggedInUser(model,null);
        Batch batch=batchRepository.findById(id);
        model.addAttribute("batch",batch);
        return "batchEdit";
    }

    @PostMapping("/batches/{id}/edit")
    public RedirectView batchEditPost(@ModelAttribute("batch") Batch batch,RedirectAttributes redirectAttributes,@PathVariable int id)
    {

        passLoggedInUser(null,redirectAttributes);
        if(batch.getTeacherId()==0)
        {
            batch.setTeacherId(null);
        }
        if(batch.getSubjectId()==0)
        {
            batch.setSubjectId(null);
        }
        batch.setBatchId(id);
        Teacher teacher=teacherRepository.getTeacherFromId(batch.getTeacherId());

        if(teacher!=null && batch!=null && teacher.getSubjectId() != batch.getSubjectId())
        {
            redirectAttributes.addFlashAttribute("message","teacher registered for another subject");
            return new RedirectView("/batches/"+id+"/edit");
        }
        batchRepository.update(batch);
        return new RedirectView("/batches",true);
    }

    @GetMapping("/batches/{id}/remove")
    public RedirectView removeBatch(@PathVariable int id,RedirectAttributes redirectAttributes)
    {
        if(checkStaff()==false)return new RedirectView("/redirect",true);
        passLoggedInUser(null,redirectAttributes);
        batchRepository.remove(id);
        return new RedirectView("/batches",true);

    }

    @GetMapping("/tests")
    public String tests(Model model)
    {
        if(checknotStudent()==false)return "redirect:/redirect";
        passLoggedInUser(model,null);
        List<Test> testList=testRepository.listAll();
        List<Subject> subjects=new ArrayList<>();

        for(Test test:testList)
        {
            Subject subject=subjectRepository.getSubjectById(test.getSubjectId());
            subjects.add(subject);

        }
        model.addAttribute("tests",testList);
        model.addAttribute("subjects",subjects);
        return "testList";
    }

    @GetMapping("tests/add")
    public String addTests(Model model)
    {
        if(checkStaff()==false)return "redirect:/redirect";
        passLoggedInUser(model,null);
        Test test=new Test();
        String date=new String();
        String startTime=new String();
        String endTime=new String();
        model.addAttribute("date",date);
        model.addAttribute("test",test);
        model.addAttribute("sTime",startTime);
        model.addAttribute("eTime",endTime);


        return "addTest";
    }

    @PostMapping("/tests/add")
    public RedirectView addtestsPost(RedirectAttributes redirectAttributes, @ModelAttribute("test") Test test, @ModelAttribute("date") String date,@ModelAttribute("sTime") String startTime,@ModelAttribute("eTime") String endTime) throws ParseException {

        passLoggedInUser(null,redirectAttributes);

        java.util.Date date1=new SimpleDateFormat("yyyy-mm-dd").parse(date);
        java.sql.Date sqlDate=new java.sql.Date(date1.getTime());

        DateFormat formatter = new SimpleDateFormat("HH:mm");
        java.sql.Time sqlStartTime = new java.sql.Time(formatter.parse(startTime).getTime());
        java.sql.Time sqlEndTime = new java.sql.Time(formatter.parse(endTime).getTime());
        test.setTestDate(sqlDate);
        test.setStartTime(sqlStartTime);
        test.setEndTime(sqlEndTime);
        if(testRepository.exists(test))
        {
            redirectAttributes.addFlashAttribute("message","test already exists");
            return new RedirectView("/tests/add",true);
        };
        testRepository.add(test);
        RedirectView redirectView=new RedirectView("/tests",true);
        return redirectView;

    }

    @GetMapping("/subjects/{subjectId}/tests/{id}/edit")
    public String batchEdit(@PathVariable int id,@PathVariable int subjectId,Model model)
    {
        if(checkStaff()==false)return "redirect:/redirect";
        passLoggedInUser(model,null);
        Test test=testRepository.findTestById(id,subjectId);
        Subject subject=subjectRepository.getSubjectById(subjectId);
        model.addAttribute("subject",subject);
        model.addAttribute("test",test);
        String date=new String();
        String startTime=new String();
        String endTime=new String();
        model.addAttribute("date",date);
        model.addAttribute("sTime",startTime);
        model.addAttribute("eTime",endTime);
        return "testEdit";
    }

    @PostMapping("/subjects/{subjectId}/tests/{id}/edit")
    public RedirectView testEditPost(@ModelAttribute("test") Test test,RedirectAttributes redirectAttributes,@PathVariable int id,@PathVariable int subjectId, @ModelAttribute("date") String date,@ModelAttribute("sTime") String startTime,@ModelAttribute("eTime") String endTime) throws ParseException {

        passLoggedInUser(null,redirectAttributes);
        test.setSubjectId(subjectId);
        test.setTestNumber(id);
        java.util.Date date1=new SimpleDateFormat("yyyy-mm-dd").parse(date);
        java.sql.Date sqlDate=new java.sql.Date(date1.getTime());

        DateFormat formatter = new SimpleDateFormat("HH:mm");
        java.sql.Time sqlStartTime = new java.sql.Time(formatter.parse(startTime).getTime());
        java.sql.Time sqlEndTime = new java.sql.Time(formatter.parse(endTime).getTime());
        test.setTestDate(sqlDate);
        test.setStartTime(sqlStartTime);
        test.setEndTime(sqlEndTime);
        testRepository.update(test);
        return new RedirectView("/tests",true);
    }

    @GetMapping("/subjects/{subjectId}/tests/{id}/remove")
    public RedirectView removeBatch(@PathVariable int id,@PathVariable int subjectId, RedirectAttributes redirectAttributes)
    {
        if(checkStaff()==false)return new RedirectView("/redirect",true);
        System.out.println("In here");
        passLoggedInUser(null,redirectAttributes);
        testRepository.remove(subjectId,id);
        return new RedirectView("/tests",true);

    }

    @GetMapping("/payrolls")
    public String payrolls(Model model)
    {
        if(checkStaff()==false)return "redirect:/redirect";
        passLoggedInUser(model,null);
        List<Payroll> payrollList=payrollRepository.listAll();
        List<User> users=new ArrayList<>();

        for(Payroll payroll:payrollList)
        {
            User user=userRepository.findByUsername(employeeRepository.getEmployeeById(payroll.getEmployeeId()).getUsername());
            users.add(user);

        }
        model.addAttribute("payrolls",payrollList);
        model.addAttribute("userList",users);
        return "payrollList";
    }



}
