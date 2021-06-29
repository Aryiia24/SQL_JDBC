package ru.fedormakarov.foxminded.task7.sql.businesslogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Util instance;
    private static Connection connection = null;
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    private Util() {
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Class not found", e);
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new RuntimeException("Connection not found", e1);
        }
    }

    public static Connection getConnection() {
        if (instance == null) {
            instance = new Util();
        }
        return connection;
    }
}
