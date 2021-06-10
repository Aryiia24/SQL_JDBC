package ru.fedormakarov.foxminded.task7.sql.formatter;

import java.util.List;

import ru.fedormakarov.foxminded.task7.sql.entity.Group;

public class GroupFormatter {
    public void showGroups(List<Group> groupList) {
        for (Group group : groupList) {
            System.out.println(group);
        }
    }
}
