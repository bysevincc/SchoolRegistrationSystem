package com.SchoolRegistrationSystem.controller;


import com.SchoolRegistrationSystem.dao.CourseDao;
import com.SchoolRegistrationSystem.dao.StudentDao;
import com.SchoolRegistrationSystem.entity.Course;
import com.SchoolRegistrationSystem.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class StudentController {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private CourseDao courseDao;

    public static final int COURSES_LIMIT = 5;
    public static final int STUDENTS_LIMIT = 50;

    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        try {
            Student dataOfStudent = studentDao.save(
                    new Student(student.getStudentName(), student.getStudentSurname()));
            return new ResponseEntity<>(dataOfStudent, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") long id, @RequestBody Student student) {
        Optional<Student> studentData = studentDao.findById(id);

        if (studentData.isPresent()) {
            Student student1 = studentData.get();
            student1.setStudentName(student.getStudentName());
            student1.setStudentSurname(student.getStudentSurname());
            return new ResponseEntity<>(studentDao.save(student1), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        try {
            List<Student> students = studentDao.findAll();

            if (students.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/students/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") long id) {
        try {
            studentDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getById(@PathVariable("id") long id) {
        Optional<Student> student = studentDao.findById(id);

        if (student.isPresent()) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/students/{id}/register/{course_id}", method = RequestMethod.POST)
    public ResponseEntity<Student> registerStudentOnCourse(@PathVariable("id") long id, @PathVariable("course_id") long course_id) {
        Optional<Student> studentData = studentDao.findById(id);
        Optional<Course> courseData = courseDao.findById(course_id);

        if (studentData.isPresent() && courseData.isPresent()) {
            Student student = studentData.get();
            Course course = courseData.get();

            if (student.getCoursesTaken().size() < COURSES_LIMIT
                    && course.getStudents().size() < STUDENTS_LIMIT) {

                student.getCoursesTaken().add(course);
                return new ResponseEntity<>(studentDao.save(student), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
