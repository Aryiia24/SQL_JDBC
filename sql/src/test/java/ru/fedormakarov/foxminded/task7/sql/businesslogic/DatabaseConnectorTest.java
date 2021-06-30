package ru.fedormakarov.foxminded.task7.sql.businesslogic;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

class DatabaseConnectorTest {

    @Test
    void testConection() {
        Connection connecton = DatabaseConnector.getInstance().getConnection();
        assertTrue(connecton != null);
    }

}
