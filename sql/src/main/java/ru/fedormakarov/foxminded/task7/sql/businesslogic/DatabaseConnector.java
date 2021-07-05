package ru.fedormakarov.foxminded.task7.sql.businesslogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static DatabaseConnector instance;
    private Connection connection;
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    private DatabaseConnector() {
        try {
            Class.forName(DB_DRIVER);
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Driver not found", e);
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new RuntimeException("Connection failed", e1);
        }
    }

    public Connection getConnection() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        } else
            try {
                if (instance.getConnection().isClosed()) {
                    instance = new DatabaseConnector();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Connection failed", e);
            }

        return instance;
    }
}
