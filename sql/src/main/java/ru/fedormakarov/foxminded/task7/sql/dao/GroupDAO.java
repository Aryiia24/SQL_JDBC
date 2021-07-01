package ru.fedormakarov.foxminded.task7.sql.dao;

import java.util.List;

import ru.fedormakarov.foxminded.task7.sql.entity.Group;

public interface GroupDAO extends GenericDAO<Group> {
    List<Group> getGroupsWithLessStudentCount(int inputStudentCount);
}
