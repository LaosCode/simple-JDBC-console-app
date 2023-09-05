package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.App;
import org.example.exceptions.DAOException;
import org.example.model.Student;
import org.example.utils.ResultSetMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class StudentDAO {

    private Connection connection;

    public void addStudent(Student student) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT into students (group_id, first_name, last_name) values (?,?,?);");
            preparedStatement.setInt(1, student.getGroupId());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());
            try {
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new DAOException("Add student: failed to execute query", e);
            }
        } catch (SQLException e) {
            throw new DAOException("Add student: failed to set prepared statement", e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DAOException("Add student: failed to close prepared statement", e);
            }
        }

    }

    public void deleteStudent(int studentId) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM students where student_id=?");
            preparedStatement.setInt(1, studentId);
            try {
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new DAOException("Delete student: failed to execute prepared statement", e);
            }
        } catch (SQLException e) {
            throw new DAOException("Delete student: failed to set prepared statement", e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DAOException("Delete student: failed to close prep statement", e);
            }
        }
    }

    public List<Student> findAllStudentsByCourseAndByName(String courseName, String firstName) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Student> result = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(
                    "select sc.student_id,group_id,first_name,last_name from students join students_courses sc on students.student_id = sc.student_id" +
                            " join courses c on c.course_id = sc.course_id\n" +
                            "where course_name=? and first_name = ?"
            );
            preparedStatement.setString(1, courseName);
            preparedStatement.setString(2, firstName);
            try {
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    result.add(ResultSetMapper.mapToStudent(resultSet));
                }
            } catch (SQLException e) {
                throw new DAOException("findAllStudentsByCourseAndByName: failed to execute prepared statement", e);
            } finally {
                resultSet.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DAOException("findAllStudentsByCourseAndByName: failed to close prepared statement", e);
            }
        }
        return result;
    }

    public void assignCourseToStudentById(int courseId, int studentId) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement =
                    connection.prepareStatement("insert into students_courses (student_id, course_id) values (?,?)");
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            try {
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new DAOException("assignCourseToStudentById: failed to execute prepared statement", e);
            }
        } catch (SQLException e) {
            throw new DAOException("assignCourseToStudentById: failed to set prepared statement", e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DAOException("assignCourseToStudentById: failed close prepared statement", e);
            }
        }
    }

    public void removeStudentFromCourse(int courseId, int studentId) {

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement =
                    connection.prepareStatement("delete from students_courses where student_id=? and course_id=?");
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            try {
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new DAOException("removeStudentFromCourse: failed to execute prep statement", e);
            }
        } catch (SQLException e) {
            throw new DAOException("removeStudentFromCourse: failed to set  prep statement", e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DAOException("removeStudentFromCourse: failed to close prep statement", e);
            }
        }
    }
}
