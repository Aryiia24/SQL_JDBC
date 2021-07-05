package ru.fedormakarov.foxminded.task7.sql;

import ru.fedormakarov.foxminded.task7.sql.businesslogic.Menu;
import ru.fedormakarov.foxminded.task7.sql.businesslogic.TableBinder;
import ru.fedormakarov.foxminded.task7.sql.businesslogic.TableCreator;

public class Domain {
    public static void main(String[] args) {
        TableCreator tableCreator = new TableCreator();
        tableCreator.createAndFillTables("createTables.sql");
        TableBinder tableBinder = new TableBinder();
        tableBinder.bindTable();
        Menu menu = new Menu();
        menu.callMenu();
    }
}