package ru.fedormakarov.foxminded.task7.sql;

import java.sql.SQLException;

import ru.fedormakarov.foxminded.task7.sql.service.GroupSevice;

public class Test {

    public static void main(String[] args) throws SQLException {
        GroupSevice groupSevice = new GroupSevice();
        System.out.println(groupSevice.getById(1));
    }

}
