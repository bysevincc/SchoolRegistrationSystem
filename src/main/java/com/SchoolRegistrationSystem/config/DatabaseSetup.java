package com.SchoolRegistrationSystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
public class DatabaseSetup {
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() {
        {
            try {
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                statement.execute("DROP TABLE IF EXISTS students;");
                statement.executeUpdate(
                        "CREATE TABLE students("
                                + "id INTEGER Primary key AUTO_INCREMENT, "
                                + "first_name varchar(30) not null, "
                                + "last_name varchar(30) not null)"
                );
                statement.executeUpdate(
                        "INSERT INTO students "
                                + "(id, student_name, student_surname) "
                                + "VALUES "
                                + "(1, 'Sevinc', 'Besdas'),"
                                + "(2, 'Paris', 'Hilton'),"
                );

                statement.execute("DROP TABLE IF EXISTS courses;");
                statement.executeUpdate(
                        "CREATE TABLE courses("
                                + "id INTEGER Primary key AUTO_INCREMENT, "
                                + "courseName varchar(30) not null)"
                );
                statement.executeUpdate(
                        "INSERT INTO courses "
                                + "(id, courseName) "
                                + "VALUES "
                                + "(1, 'Cooking'),"
                                + "(2, 'Garden'),"
                                + "(3, 'Docker'),"
                );

                statement.execute("DROP TABLE IF EXISTS students_courses;");
                statement.executeUpdate(
                        "CREATE TABLE students_courses("
                                + "student_id integer not null, "
                                + "course_id integer not null)"
                );
                statement.executeUpdate(
                        "INSERT INTO students_courses "
                                + "(student_id, course_id) "
                                + "VALUES "
                                + "(1, 1),"
                                + "(1, 2),"
                                + "(1, 3);"
                                + "(2, 1);"
                                + "(2, 3);"
                );
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
