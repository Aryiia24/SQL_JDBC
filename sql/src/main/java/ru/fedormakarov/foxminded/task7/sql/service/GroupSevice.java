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
    public boolean add(Group group) throws SQLException {
        String sql = "INSERT into groups (group_id, group_name) VALUES (?,?)";
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, group.getGroupId());
            preparedStatement.setString(2, group.getGroupName());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int groupId) throws SQLException {

        String sql = "DELETE FROM groups WHERE group_id=?";

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, groupId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Group group) throws SQLException {
        String sql = "UPDATE groups SET group_name=?,size=? WHERE group_id=?";
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setString(1, group.getGroupName());
            preparedStatement.setInt(2, group.getSize());
            preparedStatement.setInt(3, group.getGroupId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Group> getAll() throws SQLException {
        List<Group> groupList = new ArrayList<>();
        String sql = "SELECT group_id, group_name, size FROM groups";
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);) {
            while (resultSet.next()) {
                Group group = new Group();
                group.setGroupId(resultSet.getInt("group_id"));
                group.setGroupName(resultSet.getString("group_name"));
                group.setSize(resultSet.getInt("size"));
                groupList.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupList;
    }

    @Override
    public Group getById(int groupId) throws SQLException {
        String sql = "SELECT group_id, group_name, size FROM groups WHERE group_id=?";
        Group group = new Group();
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, groupId);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    group.setGroupId(resultSet.getInt("group_id"));
                    group.setGroupName(resultSet.getString("group_name"));
                    group.setSize(resultSet.getInt("size"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return group;
    }

    public List<Group> getGroupsWithLessStudentCount(int inputStudentCount) throws SQLException {
        List<Group> groupList = new ArrayList<>();
        String sql = "SELECT group_id, group_name, size FROM groups WHERE size<=?";
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, inputStudentCount);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Group group = new Group();
                    group.setGroupId(resultSet.getInt("group_id"));
                    group.setGroupName(resultSet.getString("group_name"));
                    group.setSize(resultSet.getInt("size"));
                    groupList.add(group);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return groupList;
    }
}
