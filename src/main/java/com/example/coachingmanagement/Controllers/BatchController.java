package com.example.coachingmanagement.Controllers;

import com.example.coachingmanagement.Models.*;
import com.example.coachingmanagement.Repository.*;
import com.example.coachingmanagement.Services.SecurityService;
import com.example.coachingmanagement.Utility.StudentTestDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BatchController {
    BatchRepository batchRepository;
    TeacherRepository teacherRepository;
    SubjectRepository subjectRepository;
    StudentRepository studentRepository;
    SecurityService securityService;
    UserRepository userRepository;
    TestRepository testRepository;
    EnrollmentRepository enrollmentRepository;
    StudentTestDetailsRepository studentTestDetailsRepository;

    @Autowired
    public BatchController(BatchRepository batchRepository, TeacherRepository teacherRepository, SubjectRepository subjectRepository, StudentRepository studentRepository,SecurityService securityService,UserRepository userRepository,TestRepository testRepository,
                           EnrollmentRepository enrollmentRepository,StudentTestDetailsRepository studentTestDetailsRepository) {
        this.batchRepository = batchRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
        this.securityService=securityService;
        this.userRepository=userRepository;
        this.testRepository=testRepository;
        this.enrollmentRepository=enrollmentRepository;
        this.studentTestDetailsRepository=studentTestDetailsRepository;
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
        String loggedUser=securityService.findLoggedInUsername();
        if(loggedUser==null)return false;
        User user=userRepository.findByUsername(loggedUser);
        if(user!=null && !user.getRole().equalsIgnoreCase("Student"))return true;
        return false;
    }
    @GetMapping("/batches/{batchId}")
    public String getInfo(@PathVariable int batchId, Model model){


        passLoggedInUser(model,null);
        String loggedInUsername=securityService.findLoggedInUsername();
        if(loggedInUsername==null)
        {
            return "redirect:/redirect";
        }
        Batch batch=batchRepository.findById(batchId);
        model.addAttribute("batch",batch);

        User teacher=teacherRepository.getTeacherUserFromBatch(batchId);
        Subject subject=subjectRepository.getSubjectById(batch.getSubjectId());
        model.addAttribute("subject",subject);





        model.addAttribute("teacher",teacher);
        return "batch";


    }

    @GetMapping("/batches/{batchId}/students")
    public String getStudents(@PathVariable int batchId,Model model)
    {

        passLoggedInUser(model,null);
        if(check()==false)
        {
            return "redirect:/redirect";
        }
        List<Enrollment> studentId=batchRepository.listStudents(batchId);
        List<User> students=new ArrayList<>();

        for(Enrollment e:studentId){

            students.add(studentRepository.userFromRollNo(e.getRollNumber()));
        }
        Batch batch=batchRepository.findById(batchId);
        model.addAttribute("batch",batch);
        model.addAttribute("studentIds",studentId);
        model.addAttribute("students",students);
        return "studentbatchList";
    }

    @GetMapping("/batch/tests")
    public String batchTests(Model model)
    {
        if(check()==false)
        {
            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        Integer testId=null;
        Integer batchId=null;
        Integer subjectId=null;
        model.addAttribute("subjectId",subjectId);
        model.addAttribute("batchId",batchId);
        model.addAttribute("testId",testId);
        List<Batch> batchList=batchRepository.getAllBatches();
        model.addAttribute("batches",batchList);

        return "batchTests";


    }
    @PostMapping("/batch/tests")
    public String batchTests(Model model,@ModelAttribute("subjectId") Integer subjectId,@ModelAttribute("testId") Integer testId, @ModelAttribute("batchId") Batch batch)
    {
        passLoggedInUser(model,null);
        Test test=testRepository.findTestById(testId,subjectId);
        model.addAttribute("test",test);
        model.addAttribute("subject",subjectRepository.getSubjectById(test.getSubjectId()));

        batch=batchRepository.findById(batch.getBatchId());

        List<Student> students=enrollmentRepository.getStudentsByBatch(batch.getBatchId());
        List<String> studentNames=new ArrayList<>();

        List<StudentTestDetails> studentTestDetails=new ArrayList<>();

        for(Student student:students)
        {
            User user=userRepository.findByUsername(student.getUsername());
            studentNames.add(user.getFirstName()+ " "+user.getMiddleName() +" "+ user.getLastName());
            studentTestDetails.add(studentTestDetailsRepository.getFromStudentAndTest(student.getRollNumber(),test.getSubjectId(),test.getTestNumber()));
        }
        model.addAttribute("studentList",studentNames);
        List<Batch> batchList=batchRepository.getAllBatches();
        model.addAttribute("batches",batchList);
        System.out.println(studentTestDetails);
        model.addAttribute("studentTestDetails",studentTestDetails);
        return "batchTests";



    }

    @GetMapping("/batches/tests/addMarks")
    public String addMarks(Model model)
    {
        if(check()==false)
        {
            return "redirect:/redirect";
        }
        passLoggedInUser(model,null);
        Integer testId=null;
        Integer batchId=null;
        Integer subjectId=null;
        model.addAttribute("subjectId",subjectId);
        model.addAttribute("batchId",batchId);
        model.addAttribute("testId",testId);
        List<Batch> batchList=batchRepository.getAllBatches();
        model.addAttribute("batches",batchList);
        return "batchTestsAddMarks";
    }

    @PostMapping("/batch/tests/addMarks")
    public String batchTestsaddMarks(Model model,@ModelAttribute("subjectId") Integer subjectId,@ModelAttribute("testId") Integer testId, @ModelAttribute("batchId") Batch batch)
    {
        passLoggedInUser(model,null);
        Test test=testRepository.findTestById(testId,subjectId);
        model.addAttribute("test",test);
        model.addAttribute("subject",subjectRepository.getSubjectById(test.getSubjectId()));

        batch=batchRepository.findById(batch.getBatchId());

        List<Student> students=enrollmentRepository.getStudentsByBatch(batch.getBatchId());
        List<String> studentNames=new ArrayList<>();

        List<StudentTestDetails> studentTestDetails=new ArrayList<>();

        for(Student student:students)
        {
            User user=userRepository.findByUsername(student.getUsername());
            studentNames.add(user.getFirstName()+ " "+user.getMiddleName() +" "+ user.getLastName());
            StudentTestDetails studentTestDetail=studentTestDetailsRepository.getFromStudentAndTest(student.getRollNumber(),test.getSubjectId(),test.getTestNumber());
            if(studentTestDetail==null)
            {
                studentTestDetail=new StudentTestDetails();
                studentTestDetail.setSubjectId(batch.getSubjectId());
                studentTestDetail.setRollNumber(student.getRollNumber());
                studentTestDetail.setTestNumber(test.getTestNumber());
            }
            studentTestDetails.add(studentTestDetail);
        }
        model.addAttribute("studentList",studentNames);
        List<Batch> batchList=batchRepository.getAllBatches();
        model.addAttribute("batches",batchList);


        model.addAttribute("studentTestDetailsForm",new StudentTestDetailsDTO(studentTestDetails));
        return "batchTestsAddMarks";



    }

    @PostMapping("/batches/tests/addMarks/{subject}/{id}/save")
    public RedirectView addMarksSubmit(RedirectAttributes redirectAttributes,@PathVariable int subject,@ModelAttribute("studentTestDetailsForm") StudentTestDetailsDTO studentTestDetails,@PathVariable int id)
    {

        passLoggedInUser(null,redirectAttributes);
        for(StudentTestDetails studentTestDetails1:studentTestDetails.studentTestDetails)
        {
            studentTestDetails1.setTestNumber(id);
            studentTestDetails1.setSubjectId(subject);
            if(studentTestDetailsRepository.exists(subject,id,studentTestDetails1.getRollNumber()))
            {
                studentTestDetailsRepository.update(studentTestDetails1);
            }
            else
            {
                studentTestDetailsRepository.add(studentTestDetails1);
            }
        }

        return new RedirectView("/batch/tests",true);
    }



}
