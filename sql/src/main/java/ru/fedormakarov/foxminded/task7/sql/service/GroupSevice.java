package ru.fedormakarov.foxminded.task7.sql.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ru.fedormakarov.foxminded.task7.sql.businesslogic.Util;
import ru.fedormakarov.foxminded.task7.sql.dao.GroupDAO;
import ru.fedormakarov.foxminded.task7.sql.entity.Group;

public class GroupSevice extends Util implements GroupDAO {

    @Override
    public void add(Group group) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT into groups (group_id, group_name) VALUES (?,?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, group.getGroupId());
            preparedStatement.setString(2, group.getGroupName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public void delete(int group_id) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();

        String sql = "DELETE FROM groups WHERE group_id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, group_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    @Override
    public void update(Group group) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        String sql = "UPDATE groups SET group_name=?,size=? WHERE group_id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, group.getGroupName());
            preparedStatement.setInt(2, group.getSize());
            preparedStatement.setInt(3, group.getGroupId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    @Override
    public List<Group> getAll() throws SQLException {
        List<Group> groupList = new ArrayList<>();
        Connection connection = getConnection();
        Statement statement = null;

        String sql = "SELECT group_id, group_name, size FROM groups";

        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Group group = new Group();
                group.setGroupId(resultSet.getInt("group_id"));
                group.setGroupName(resultSet.getString("group_name"));
                group.setSize(resultSet.getInt("size"));
                groupList.add(group);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return groupList;
    }

    @Override
    public Group getById(int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        String sql = "SELECT group_id, group_name, size FROM groups WHERE group_id=?";

        Group group = new Group();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            group.setGroupId(resultSet.getInt("group_id"));
            group.setGroupName(resultSet.getString("group_name"));
            group.setSize(resultSet.getInt("size"));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return group;
    }

    public List<Group> getGroupsWithLessStudentCount(int inputStudentCount) throws SQLException {
        List<Group> groupList = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;

        String sql = "SELECT group_id, group_name, size FROM groups WHERE size<=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, inputStudentCount);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Group group = new Group();
                group.setGroupId(resultSet.getInt("group_id"));
                group.setGroupName(resultSet.getString("group_name"));
                group.setSize(resultSet.getInt("size"));
                groupList.add(group);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return groupList;

    }
}
