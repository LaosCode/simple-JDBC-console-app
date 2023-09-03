package org.example.dao;

import lombok.RequiredArgsConstructor;
import org.example.App;
import org.example.model.Course;

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
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Course> showAllCourses() {
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
                        int courseId = resultSet.getInt(1);
                        String courseName = resultSet.getString(2);
                        String courseDescription = resultSet.getString(3);
                        result.add(new Course(courseId, courseName, courseDescription));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } finally {
                    resultSet.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}
