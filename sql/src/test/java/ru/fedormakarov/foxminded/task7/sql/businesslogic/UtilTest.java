package ru.fedormakarov.foxminded.task7.sql.businesslogic;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

class UtilTest {
    private static Util util = new Util();

    @Test
    void testConection() {
        Connection connecton = Util.getConnection();
        assertTrue(connecton != null);
    }

}
