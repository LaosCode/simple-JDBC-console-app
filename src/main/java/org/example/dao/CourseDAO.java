package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.App;
import org.example.exceptions.DAOException;
import org.example.model.Course;
import org.example.utils.ResultSetMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CourseDAO {

    private Connection connection;

    public void addCourse(String courseName, String courseDescription) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO courses(course_name, course_description) VALUES (?,?)")) {
            preparedStatement.setString(1, courseName);
            preparedStatement.setString(2, courseDescription);
            try {
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new DAOException("Add course error: failed on execution", e);
            }
        } catch (SQLException e) {
            throw new DAOException("Add course error: failed on prepared statement creation", e);
        }
    }

    public List<Course> getAllCourses() {
        List<Course> result = new ArrayList<>();

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("select * from courses")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(ResultSetMapper.mapToCourse(resultSet));
                }
            } catch (SQLException e) {
                throw new DAOException("Get all courses: failed on executeQuery", e);
            }
        } catch (SQLException e) {
            throw new DAOException("Get all courses: failed on prepared statement", e);
        }
        return result;
    }
}
