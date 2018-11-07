package com.codethecode.courseregistersystem.controller;

import com.codethecode.courseregistersystem.RequestStatus;
import com.codethecode.courseregistersystem.dto.RequestDto;
import com.codethecode.courseregistersystem.dto.ScheduleDto;
import com.codethecode.courseregistersystem.entity.Course;
import com.codethecode.courseregistersystem.entity.Request;
import com.codethecode.courseregistersystem.entity.Student;
import com.codethecode.courseregistersystem.entity.Teacher;
import com.codethecode.courseregistersystem.repository.CourseRepository;
import com.codethecode.courseregistersystem.repository.RequestRepository;
import com.codethecode.courseregistersystem.repository.StudentRepository;
import com.codethecode.courseregistersystem.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping(value="/teacher")
public class TeacherController {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    RequestRepository requestRepository;

    @GetMapping(value = "/getSelfSchedule/{id}")
    public ResponseEntity getSelfSchedule(@PathVariable Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        ScheduleDto teacherSchedule = new ScheduleDto();
        teacherSchedule.setName(teacher.get().getName());
        teacherSchedule.setSurname(teacher.get().getSurname());
        teacherSchedule.setBusyDays(teacher.get().getBusyDays());
        teacherSchedule.setIsStudent(false);

        return new ResponseEntity<>(teacherSchedule, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/getBalance/{id}")
    public ResponseEntity getBalance(@PathVariable Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        return new ResponseEntity<>("Balance for teacher " + teacher.get().getName()
                                        + "is " + teacher.get().getBalance() , HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/checkRequests/{id}")
    public ResponseEntity checkRequests(@PathVariable Long id) {
        // might be written as list of requests, instead of one request
        Optional<Request> request = requestRepository.findByTeacherId(id);
        RequestDto requestDto = new RequestDto();
        Optional<Course> requestedCourse = courseRepository.findById(requestDto.getCourse().getId());
        Optional<Student> requestingStudent = studentRepository.findById(requestDto.getStudent().getId());
        Optional<Teacher> requestedTeacher = teacherRepository.findById(requestDto.getTeacher().getId());

        request.get().setRequestStatus(RequestStatus.REVIEWED_BY_TEACHER);
        requestRepository.save(request.get())
        ;
        return new ResponseEntity<>("RequestId "+ request.get().getId() +": Request made by student"
                + requestingStudent.get().getName() + " " + requestingStudent.get().getSurname()
                + " for course " + requestedCourse.get().getName()
                + " for teacher " + requestedTeacher.get().getName(), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/responseRequest/id={requestId}&response={response}")
    public ResponseEntity responseRequest(@PathVariable Long requestId, Long response) {
        Optional<Request> request = requestRepository.findById(requestId);
        Optional<Teacher> requestedTeacher = teacherRepository.findById(request.get().getTeacher().getId());
        Optional<Course> requestedCourse = courseRepository.findById(request.get().getCourse().getId());

        if(response.equals(1)){ // Say, it's ACCEPT of the request

            request.get().setRequestStatus(RequestStatus.ACCEPTED);
            ArrayList<String> busyDays = requestedTeacher.get().getBusyDays();
            busyDays.add(requestedCourse.get().getDay());

            int teachersBalance = Math.toIntExact(requestedTeacher.get().getBalance());
            teachersBalance += requestedCourse.get().getCost();
            requestedTeacher.get().setBalance(teachersBalance);

            String responseMessage = "Request is accepted";
        }
        else if(response.equals(0)){ // DENIAL of the request
            request.get().setRequestStatus(RequestStatus.DENIED);
            String responseMessage = "Request is denied";
        }

        requestRepository.deleteById(requestId);
        return new ResponseEntity<>("Request is denied.", HttpStatus.ACCEPTED);
    }
}
