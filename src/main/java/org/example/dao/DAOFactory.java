package org.example.dao;

import lombok.Getter;

import java.sql.Connection;

@Getter
public class DAOFactory {
    private Connection connection;
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private GroupDAO groupDAO;

    public DAOFactory(Connection connection) {
        this.connection = connection;
        studentDAO = new StudentDAO(connection);
        courseDAO = new CourseDAO(connection);
        groupDAO = new GroupDAO(connection);
    }
}
