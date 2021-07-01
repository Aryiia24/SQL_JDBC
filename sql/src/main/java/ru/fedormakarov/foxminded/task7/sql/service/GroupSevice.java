package ru.fedormakarov.foxminded.task7.sql.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ru.fedormakarov.foxminded.task7.sql.businesslogic.DatabaseConnector;
import ru.fedormakarov.foxminded.task7.sql.dao.GroupDAO;
import ru.fedormakarov.foxminded.task7.sql.entity.Group;

public class GroupSevice implements GroupDAO {

    private Connection connection = DatabaseConnector.getInstance().getConnection();

    @Override
    public boolean save(Group group) {
        String sql = "INSERT into groups (id, group_name) VALUES (?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, group.getGroupId());
            preparedStatement.setString(2, group.getGroupName());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't save group", e);
        }
        return false;
    }

    @Override
    public boolean delete(int groupId) {
        String sql = "DELETE FROM groups WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, groupId);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't delete group", e);
        }
        return false;
    }

    @Override
    public boolean update(Group group) {
        String sql = "UPDATE groups SET group_name=?,size=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, group.getGroupName());
            preparedStatement.setInt(2, group.getSize());
            preparedStatement.setInt(3, group.getGroupId());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't update group", e);
        }
        return false;
    }

    @Override
    public List<Group> getAll() {
        List<Group> groupList = new ArrayList<>();
        String sql = "SELECT id, group_name, size FROM groups";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql);) {
            while (resultSet.next()) {
                groupList.add(constructGroupFromTable(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't get all groups", e);
        }
        return groupList;
    }

    @Override
    public Group getById(int groupId) {
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
            throw new RuntimeException("Can't get group", e);
        }
        return group;
    }

    @Override
    public List<Group> getGroupsWithLessStudentCount(int inputStudentCount) {
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
            throw new RuntimeException("Can't get groups", e);
        }
        return groupList;
    }

    private Group constructGroupFromTable(ResultSet resultSet) {
        Group group = new Group();
        try {
            group.setGroupId(resultSet.getInt("id"));
            group.setGroupName(resultSet.getString("group_name"));
            group.setSize(resultSet.getInt("size"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't construct group", e);
        }

        return group;
    }
}
