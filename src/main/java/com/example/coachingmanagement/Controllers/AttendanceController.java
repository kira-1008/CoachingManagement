package com.example.coachingmanagement.Controllers;

import com.example.coachingmanagement.Models.*;
import com.example.coachingmanagement.Repository.*;
import com.example.coachingmanagement.Services.SecurityService;
import com.example.coachingmanagement.Utility.StudentAttendanceDTO;
import com.example.coachingmanagement.Utility.StudentTestDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class AttendanceController {
    StudentRepository studentRepository;
    BatchRepository batchRepository;
    AttendanceRepository attendanceRepository;
    SecurityService securityService;
    UserRepository userRepository;
    EmployeeAttendanceRepository employeeAttendanceRepository;
    EmployeeRepository employeeRepository;
    EnrollmentRepository enrollmentRepository;


    @Autowired
    public AttendanceController(StudentRepository studentRepository, BatchRepository batchRepository, AttendanceRepository attendanceRepository, SecurityService securityService, UserRepository userRepository, EmployeeAttendanceRepository employeeAttendanceRepository, EmployeeRepository employeeRepository,
                                EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.batchRepository = batchRepository;
        this.attendanceRepository = attendanceRepository;
        this.securityService = securityService;
        this.userRepository = userRepository;
        this.employeeAttendanceRepository = employeeAttendanceRepository;
        this.employeeRepository = employeeRepository;
        this.enrollmentRepository=enrollmentRepository;
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
    Boolean check(String username)
    {
        String loggedUser=securityService.findLoggedInUsername();
        User user=userRepository.findByUsername(loggedUser);
        if(user.getRole().equalsIgnoreCase("student") && !loggedUser.equals(username))return false;
        return true;
    }

    @GetMapping("/student/{username}/attendance")
    public String studentAttendance(@PathVariable String username, Model model){
        if(check(username)==false)
        {
            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        int roll=studentRepository.findByUsername(username).getRollNumber();
        List<Batch> batchList=studentRepository.getBatches(roll);
        model.addAttribute("batches",batchList);
        User user=userRepository.findByUsername(username);
        model.addAttribute("user",user);
        String date=new String();
        model.addAttribute("date",date);
        return "studentAttendance";
    }

    @PostMapping("/student/{username}/attendance")
    public String studentAttendance(Model model,@PathVariable String username, @ModelAttribute("date") String date,@ModelAttribute("batch") Batch batch) throws ParseException {

        passLoggedInUser(model,null);
        batch=batchRepository.findById(batch.getBatchId());
        int roll=studentRepository.findByUsername(username).getRollNumber();
        List<Batch> batchList=studentRepository.getBatches(roll);
        Date date1=new SimpleDateFormat("dd/MM/yy").parse(date);
        java.sql.Date sqlDate=new java.sql.Date(date1.getTime());

        StudentAttendance studentAttendance=attendanceRepository.getAttendanceByDateAndRoll(roll,sqlDate);

        model.addAttribute("attendance",studentAttendance);
        if(studentAttendance!=null) {
            if (studentAttendance.getIsPresent() == true) {
                model.addAttribute("presence", "Present");
            } else {
                model.addAttribute("presence", "Absent");
            }

        }
        if(studentAttendance==null)
            model.addAttribute("noAttendance",true);
        model.addAttribute("loginStatus",true);
        model.addAttribute("batchName",batch.getName());
        model.addAttribute("batches",batchList);
        User user=userRepository.findByUsername(username);
        model.addAttribute("user",user);

        model.addAttribute("date",date);
        return "studentAttendance";
    }

    @GetMapping("/employee/{username}/attendance")
    public String getEmployeeAttendance( Model model,@PathVariable String username)
    {
        if(check(username)==false)
        {
            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        String date=new String();
        User user=userRepository.findByUsername(username);
        model.addAttribute("user",user);
        model.addAttribute("date",date);
        return "employeeAttendance";
    }

    @PostMapping("/employee/{username}/attendance")
    public String employeeAttendance(Model model,@PathVariable String username, @ModelAttribute("date") String date) throws ParseException {

        passLoggedInUser(model,null);
        Date date1=new SimpleDateFormat("dd/MM/yy").parse(date);
        java.sql.Date sqlDate=new java.sql.Date(date1.getTime());
        Employee employee=employeeRepository.getFromUsername(username);

        EmployeeAttendance employeeAttendance=employeeAttendanceRepository.getEmployeeAttendanceByDateAndId(employee.getEmployeeId(),sqlDate);
        User user=userRepository.findByUsername(username);
        model.addAttribute("user",user);
        model.addAttribute("attendance",employeeAttendance);

        if(employeeAttendance==null)
            model.addAttribute("noAttendance",true);
        model.addAttribute("date",date);
        return "employeeAttendance";
    }


    @GetMapping("/employee/{username}/attendance/mark")
    public String markAttendance(Model model,@PathVariable String username)
    {
        if(check(username)==false)
        {
            return "redirect:/redirect";
        }
        String loggedUser=securityService.findLoggedInUsername();
        User user=userRepository.findByUsername(loggedUser);
        if(!user.getRole().equalsIgnoreCase("staff")&&!loggedUser.equals(username))return "redirect:/redirect";
        passLoggedInUser(model,null);
        Employee employee=employeeRepository.getFromUsername(username);
        model.addAttribute("employee",employee);
        User user1=userRepository.findByUsername(username);
        model.addAttribute("user",user1);
        EmployeeAttendance employeeAttendance=new EmployeeAttendance();
        model.addAttribute("attendance",employeeAttendance);
        return "markEmployeeAttendance";
    }

    @PostMapping("/employee/{username}/attendance/mark")
    public RedirectView markAttendancePost(RedirectAttributes redirectAttributes,@ModelAttribute("attendance") EmployeeAttendance employeeAttendance,@PathVariable String username)
    {
        Employee employee=employeeRepository.getFromUsername(username);
        employeeAttendance.setEmployeeId(employee.getEmployeeId());
        employeeAttendanceRepository.add(employeeAttendance);
        User user=userRepository.findByUsername(username);
        RedirectView redirectView=new RedirectView("/"+user.getRole()+"/"+username);
        passLoggedInUser(null,redirectAttributes);
        redirectAttributes.addFlashAttribute("user",user);
        return redirectView;

    }

    @GetMapping("/batch/attendance")
    public String batchAttendance(Model model)
    {


        String loggedUser=securityService.findLoggedInUsername();
        if(loggedUser==null)return "redirect:/redirect";
        User user=userRepository.findByUsername(loggedUser);
        if(user==null || user.getRole().equalsIgnoreCase("student"))return "redirect:/redirect";
        passLoggedInUser(model,null);
        Integer batchId=null;
        String date=new String();

        model.addAttribute("batchId",batchId);
        model.addAttribute("date",date);
        List<Batch> batchList=batchRepository.getAllBatches();
        model.addAttribute("batches",batchList);

        return "batchAttendance";


    }
    @PostMapping("/batch/attendance")
    public String batchAttendance(Model model,@ModelAttribute("batchId") Batch batch,@ModelAttribute("date") String date ) throws ParseException {
        passLoggedInUser(model,null);
        batch=batchRepository.findById(batch.getBatchId());

        java.util.Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
        java.sql.Date sqlDate=new java.sql.Date(date1.getTime());
        List<Student> students=enrollmentRepository.getStudentsByBatch(batch.getBatchId());
        List<String> studentNames=new ArrayList<>();
        List<StudentAttendance> studentAttendanceList=new ArrayList<>();
        for(Student student:students)
        {
            User user=userRepository.findByUsername(student.getUsername());
            studentNames.add(user.getFirstName()+" "+user.getMiddleName()+" "+user.getLastName());
            studentAttendanceList.add(attendanceRepository.getAttendanceByDateAndRoll(student.getRollNumber(),sqlDate));
        }

        model.addAttribute("studentNames",studentNames);
        model.addAttribute("studentAttendanceList",studentAttendanceList);
        List<Batch> batchList=batchRepository.getAllBatches();
        model.addAttribute("batches",batchList);
        return "batchAttendance";



    }

    @PostMapping("/batch/attendance/addAttendance")
    public String addAttendance(Model model,@ModelAttribute("date") String date,@ModelAttribute("batchId") Batch batch) throws ParseException {
        passLoggedInUser(model,null);
        java.util.Date date1=new SimpleDateFormat("yyyy-mm-dd").parse(date);
        java.sql.Date sqlDate=new java.sql.Date(date1.getTime());
        batch=batchRepository.findById(batch.getBatchId());
        model.addAttribute("batch",batch);
        model.addAttribute("date",date);
        List<Student> students=enrollmentRepository.getStudentsByBatch(batch.getBatchId());
        List<String> studentNames=new ArrayList<>();

        List<StudentAttendance> studentAttendanceList=new ArrayList<>();

        for(Student student:students)
        {
            User user=userRepository.findByUsername(student.getUsername());
            studentNames.add(user.getFirstName()+ " "+user.getMiddleName() +" "+ user.getLastName());
            StudentAttendance studentAttendance=attendanceRepository.getAttendanceByDateAndRoll(student.getRollNumber(),sqlDate);
            if(studentAttendance==null)
            {
                studentAttendance=new StudentAttendance();
                studentAttendance.setDate(sqlDate);
                studentAttendance.setRollNumber(student.getRollNumber());
                studentAttendance.setBatchId(batch.getBatchId());
            }
            studentAttendanceList.add(studentAttendance);
        }
        model.addAttribute("studentList",studentNames);
        List<Batch> batchList=batchRepository.getAllBatches();
        model.addAttribute("batches",batchList);


        model.addAttribute("studentAttendanceForm",new StudentAttendanceDTO(studentAttendanceList));
        return "batchAddAttendance";
    }

    @GetMapping("/batch/attendance/addAttendance")
    public String batchTestsaddMarks(Model model)
    {
        String loggedUser=securityService.findLoggedInUsername();
        if(loggedUser==null)return "redirect:/redirect";
        User user=userRepository.findByUsername(loggedUser);
        if(user==null || user.getRole().equalsIgnoreCase("student"))return "redirect:/redirect";
        passLoggedInUser(model,null);
        Integer batchId=null;
        String date=new String();
        model.addAttribute("batchId",batchId);
        model.addAttribute("date",date);
        List<Batch> batchList=batchRepository.getAllBatches();
        model.addAttribute("batches",batchList);
        return "batchAddAttendance";

    }

    @PostMapping("/batch/attendance/addAttendance/{batch}/{date}/save")
    public RedirectView addMarksSubmit(RedirectAttributes redirectAttributes,@PathVariable int batch,@PathVariable String date,@ModelAttribute("studentAttendanceForm") StudentAttendanceDTO studentAttendanceDTO) throws ParseException {

        passLoggedInUser(null,redirectAttributes);
        java.util.Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
        java.sql.Date sqlDate=new java.sql.Date(date1.getTime());
        System.out.println(studentAttendanceDTO.studentAttendanceList);
        System.out.println(sqlDate);
        for(StudentAttendance studentAttendance:studentAttendanceDTO.studentAttendanceList)
        {
            studentAttendance.setDate(sqlDate);
            studentAttendance.setBatchId(batch);
            if(attendanceRepository.exists(studentAttendance.getRollNumber(),studentAttendance.getBatchId(),studentAttendance.getDate()))
            {
                attendanceRepository.update(studentAttendance);
            }
            else
            {
                attendanceRepository.add(studentAttendance);
            }
        }

        return new RedirectView("/batch/attendance",true);
    }


}
