package com.SchoolRegistrationSystem.controller;

import com.SchoolRegistrationSystem.dao.CourseDao;
import com.SchoolRegistrationSystem.entity.Course;
import org.junit.Before;
import org.junit.jupiter.api.Test;
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

@RunWith(SpringRunner.class)
public class CourseControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    CourseController courseController;

    @Mock
    CourseDao courseDao;


    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(courseController)
                .build();
    }

    @Test
    public void getAllCourses() throws Exception {
        List<Course> courses = Arrays.asList(new Course("TestCourse"));
        Mockito.when(courseDao.findAll()).thenReturn(courses);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getById() throws Exception  {
        Optional<Course> course = Optional.of(new Course("TestCourse"));
        Mockito.when(courseDao.findById(1l)).thenReturn(course);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void deleteCourse() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/courses/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}