package com.SchoolRegistrationSystem.controller;


import com.SchoolRegistrationSystem.dao.CourseDao;
import com.SchoolRegistrationSystem.dao.StudentDao;
import com.SchoolRegistrationSystem.entity.Course;
import com.SchoolRegistrationSystem.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class ReportForAdminController {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private CourseDao courseDao;

    @GetMapping("/reports/studentsWithoutAnyCourses")
    public ResponseEntity<List<Student>> getStudentsWithoutAnyCourses() {
        try {
            List<Student> students = studentDao.findAll().stream()
                    .filter(p -> p.getCoursesTaken().size() == 0)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reports/coursesWithoutAnyStudents")
    public ResponseEntity<List<Course>> getCoursesWithoutAnyCourses() {
        try {
            List<Course> courses = courseDao.findAll().stream()
                    .filter(p -> p.getStudents().size() == 0)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/reports/student/{id}/courses")
    public ResponseEntity<List<Course>> getCoursesforSpesificStudents(@PathVariable("id") long id) {
        Optional<Student> studentData = studentDao.findById(id);

        if (studentData.isPresent()) {
            Student student = studentData.get();
            return new ResponseEntity<>(new ArrayList<Course>(student.getCoursesTaken()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/reports/course/{id}/students")
    public ResponseEntity<List<Student>> getStudentsforSpesificCourses(@PathVariable("id") long id) {
        Optional<Course> courseData = courseDao.findById(id);

        if (courseData.isPresent()) {
            Course course = courseData.get();
            return new ResponseEntity<>(new ArrayList<Student>(course.getStudents()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
