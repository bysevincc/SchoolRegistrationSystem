package com.SchoolRegistrationSystem.controller;

import com.SchoolRegistrationSystem.dao.CourseDao;
import com.SchoolRegistrationSystem.dao.StudentDao;
import com.SchoolRegistrationSystem.entity.Course;
import com.SchoolRegistrationSystem.entity.Student;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
public class ReportForAdminControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    ReportForAdminController reportForAdminController ;

    @Mock
    StudentDao studentDao;

    @Mock
    CourseDao courseDao;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(reportForAdminController)
                .build();
    }

    @Test
    public void getStudentsWithoutAnyCourses()  throws Exception {
        List<Student> students = Arrays.asList(new Student("StudentName", "StudentSurname"));
        Mockito.when(studentDao.findAll()).thenReturn(students);
        Optional<Course> course = Optional.of(new Course("Test course"));
        Mockito.when(courseDao.findById(1l)).thenReturn(course);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/reports/studentsWithoutAnyCourses"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getCoursesWithoutAnyCourses()  throws Exception {
        List<Student> students = Arrays.asList(new Student("StudentName", "StudentSurname"));
        Mockito.when(studentDao.findAll()).thenReturn(students);
        Optional<Course> course = Optional.of(new Course("Test course"));
        Mockito.when(courseDao.findById(1l)).thenReturn(course);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/reports/coursesWithoutAnyStudents"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getStudentsforSpesificCourses()  throws Exception {
        Student testStudent = new Student("StudentName", "StudentSurname");
        Course testCourse = new Course("Test course");
        testStudent.getCoursesTaken().add(testCourse);

        Optional<Student> student = Optional.of(testStudent);
        Mockito.when(studentDao.findById(1l)).thenReturn(student);
        Optional<Course> course = Optional.of(testCourse);
        Mockito.when(courseDao.findById(1l)).thenReturn(course);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/reports/course/1/students"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getCoursesforSpesificStudents() throws Exception{
        Student testStudent = new Student("StudentName", "StudentSurname");
        Course testCourse = new Course("Test course");
        testStudent.getCoursesTaken().add(testCourse);

        Optional<Student> student = Optional.of(testStudent);
        Mockito.when(studentDao.findById(1l)).thenReturn(student);
        Optional<Course> course = Optional.of(testCourse);
        Mockito.when(courseDao.findById(1l)).thenReturn(course);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/reports/student/1/courses"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}