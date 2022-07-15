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
public class StudentControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    StudentController studentsController;

    @Mock
    StudentDao studentDao;

    @Mock
    CourseDao courseDao;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(studentsController)
                .build();
    }

    @Test
    public void registerStudentOnCourse() throws Exception{
        Optional<Student> student = Optional.of(new Student("StudentName", "StudentSurname"));
        Mockito.when(studentDao.findById(1l)).thenReturn(student);
        Optional<Course> course = Optional.of(new Course("Test course"));
        Mockito.when(courseDao.findById(1l)).thenReturn(course);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/students/1/register/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void deleteStudent() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/students/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    public void getById() throws Exception{
    }

    @Test
    public void getAllStudents() throws Exception{
        List<Student> students = Arrays.asList(new Student("StudentName", "StudentSurname"));
        Mockito.when(studentDao.findAll()).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/students"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}