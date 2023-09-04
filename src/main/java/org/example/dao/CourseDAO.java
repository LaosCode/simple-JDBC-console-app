package org.example.dao;

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

@RequiredArgsConstructor
public class CourseDAO {
    public void addCourse(String courseName, String courseDescription) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = App.getConnection();
            try {
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO courses(course_name, course_description) VALUES (?,?)");
                preparedStatement.setString(1, courseName);
                preparedStatement.setString(2, courseDescription);
                try {
                    preparedStatement.execute();
                } catch (SQLException e) {
                    throw new DAOException("Add course error: failed on execution",e);
                }
            } catch (SQLException e) {
                throw new DAOException("Add course error: failed on prepared statement creation",e);
            } finally {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Add course error: failed to close prepared statement",e);
                }
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException("Add course error: failed to close connection statement",e);
            }
        }
    }

    public List<Course> getAllCourses() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Course> result = new ArrayList<>();
        try {
            connection = App.getConnection();
            try {
                preparedStatement =
                        connection.prepareStatement("select * from courses");
                try {
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        result.add(ResultSetMapper.mapToCourse(resultSet));
                    }
                } catch (SQLException e) {
                    throw new DAOException("Get all courses: failed on executeQuery",e);
                } finally {
                    resultSet.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Get all courses: failed on prepared statement",e);
            } finally {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DAOException("Get all courses: failed to close connection",e);
                }
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException("Get all courses: failed to get connection",e);
            }
        }
        return result;
    }
}
