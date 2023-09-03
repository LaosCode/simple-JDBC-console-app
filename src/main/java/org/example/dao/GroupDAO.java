package org.example.dao;

import lombok.RequiredArgsConstructor;
import org.example.App;
import org.example.model.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class GroupDAO {
    public void addGroup(String groupName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = App.getConnection();
            try {
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO groups (group_name) VALUES (?)");
                preparedStatement.setString(1, groupName);
                preparedStatement.execute();
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

    public List<Group> findAllGroupWithLessOrEqualStudents(int numberOfStudents) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Group> result = new ArrayList<>();
        try {
            connection = App.getConnection();
            try {
                preparedStatement =
                        connection.prepareStatement("select g.group_id,group_name, count(*) as n_students" +
                                " from students join groups g on students.group_id = g.group_id" +
                                " group by g.group_id" +
                                " having count(group_name) >= ?");
                preparedStatement.setInt(1, numberOfStudents);
                try {
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        int groupId = resultSet.getInt(1);
                        String groupName = resultSet.getString(2);
                        result.add(new Group(groupId, groupName));
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

    public void deleteGroup(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = App.getConnection();
            try {
                preparedStatement = connection.prepareStatement(
                        "delete from groups where group_id=?");
                preparedStatement.setInt(1, id);
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
