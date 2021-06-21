package ru.fedormakarov.foxminded.task7.sql.businesslogic;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

class UtilTest {
    Util util = new Util();

    @Test
    void testConection() {
        Connection connecton = util.getConnection();
        assertEquals(connecton != null, true);
    }

}
