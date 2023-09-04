package org.example.utils;

import org.example.model.Course;
import org.example.model.Group;
import org.example.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetMapper {

    public static Student mapToStudent(ResultSet resultSet) throws SQLException {
        int studentId = resultSet.getInt(1);
        int groupId = resultSet.getInt(2);
        String firstName = resultSet.getString(3);
        String lastName = resultSet.getString(4);
        return new Student(studentId, groupId, firstName, lastName);
    }

    public static Course mapToCourse(ResultSet resultSet) throws SQLException {
        int courseId = resultSet.getInt(1);
        String courseName = resultSet.getString(2);
        String courseDescription = resultSet.getString(3);
        return new Course(courseId, courseName, courseDescription);
    }

    public static Group mapToGroup(ResultSet resultSet) throws SQLException {
        int groupId = resultSet.getInt(1);
        String groupName = resultSet.getString(2);
        return new Group(groupId, groupName);
    }

}
