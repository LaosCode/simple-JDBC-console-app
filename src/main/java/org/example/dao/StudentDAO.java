package org.example.dao;

import lombok.RequiredArgsConstructor;
import org.example.App;
import org.example.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class StudentDAO {

    public void addStudent(Student student) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = App.getConnection();
            try {
                preparedStatement = connection.prepareStatement("INSERT into students (group_id, first_name, last_name) values (?,?,?);");
                preparedStatement.setInt(1, student.getGroupId());
                preparedStatement.setString(2, student.getFirstName());
                preparedStatement.setString(3, student.getLastName());
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

    public void deleteStudent(int studentId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = App.getConnection();
            try {
                preparedStatement = connection.prepareStatement("DELETE FROM students where student_id=?");
                preparedStatement.setInt(1, studentId);
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

    public List<Student> findAllStudentsByCourseAndByName(String courseName, String firstName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Student> result = new ArrayList<>();

        try {
            connection = App.getConnection();
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
                        int studentId = resultSet.getInt(1);
                        int groupId = resultSet.getInt(2);
                        String lastName = resultSet.getString(4);
                        result.add(new Student(studentId, groupId, firstName, lastName));
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

    public void assignCourseToStudentById(int courseId, int studentId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = App.getConnection();
            try {
                preparedStatement =
                        connection.prepareStatement("insert into students_courses (student_id, course_id) values (?,?)");
                preparedStatement.setInt(1, studentId);
                preparedStatement.setInt(2, courseId);
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

    public void removeStudentFromCourse(int courseId, int studentId) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = App.getConnection();
            try {
                preparedStatement =
                        connection.prepareStatement("delete from students_courses where student_id=? and course_id=?");
                preparedStatement.setInt(1, studentId);
                preparedStatement.setInt(2, courseId);
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

}
