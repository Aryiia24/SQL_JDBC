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

public class GroupSevice implements GroupDAO {

    Connection connection = Util.getConnection();

    @Override
    public boolean add(Group group) throws SQLException {
        String sql = "INSERT into groups (id, group_name) VALUES (?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
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
        String sql = "DELETE FROM groups WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
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
        String sql = "UPDATE groups SET group_name=?,size=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
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
        String sql = "SELECT id, group_name, size FROM groups";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql);) {
            while (resultSet.next()) {
                groupList.add(constructGroupFromTable(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupList;
    }

    @Override
    public Group getById(int groupId) throws SQLException {
        String sql = "SELECT id, group_name, size FROM groups WHERE id=?";
        Group group = new Group();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, groupId);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    group.setGroupId(resultSet.getInt("id"));
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
        String sql = "SELECT id, group_name, size FROM groups WHERE size<=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, inputStudentCount);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    groupList.add(constructGroupFromTable(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return groupList;
    }

    private static Group constructGroupFromTable(ResultSet resultSet) throws SQLException {
        Group group = new Group();
        group.setGroupId(resultSet.getInt("id"));
        group.setGroupName(resultSet.getString("group_name"));
        group.setSize(resultSet.getInt("size"));
        return group;
    }
}
