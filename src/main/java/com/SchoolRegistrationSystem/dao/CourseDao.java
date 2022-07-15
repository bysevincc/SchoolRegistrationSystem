package com.SchoolRegistrationSystem.dao;

import com.SchoolRegistrationSystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseDao extends JpaRepository<Course,Long> {
}
