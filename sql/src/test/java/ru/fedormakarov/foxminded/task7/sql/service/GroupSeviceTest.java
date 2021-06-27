package ru.fedormakarov.foxminded.task7.sql.service;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import ru.fedormakarov.foxminded.task7.sql.entity.Group;

@TestMethodOrder(OrderAnnotation.class)
class GroupSeviceTest {

    private final GroupSevice groupService = new GroupSevice();

    @Test
    @Order(1)
    void testAddGroupMethod() throws SQLException {
        Group testGroup = new Group();
        testGroup.setGroupId(11);
        testGroup.setGroupName("Test");
        testGroup.setSize(0);
        assertTrue(groupService.add(testGroup));
        Group actualGroupInTable = groupService.getById(11);
        assertEquals(testGroup, actualGroupInTable);

    }

    @Test
    @Order(2)
    void testUpdateGroupMethod() throws SQLException {
        Group testGroup = new Group();
        testGroup.setGroupId(11);
        testGroup.setGroupName("Test2");
        testGroup.setSize(0);
        assertTrue(groupService.update(testGroup));
        Group updatedGroup = groupService.getById(11);
        assertEquals(testGroup, updatedGroup);
    }

    @Test
    @Order(3)
    void testDeleteMethod() throws SQLException {
        assertTrue(groupService.delete(11));
        
    }

    @Test
    @Order(4)
    void testGetAllMethod() throws SQLException {
        List<Group> groupList = groupService.getAll();
        assertNotNull(groupList);
        assertEquals(10, groupList.size());

    }

    @Test
    @Order(5)
    void testGetGrouptByIdMethod() throws SQLException {
        int groupId1 = 5;
        int groupId2 = 3;
        Group group1 = groupService.getById(groupId1);
        Group group2 = groupService.getById(groupId2);
        assertNotNull(group1);
        assertNotNull(group2);
        assertEquals(groupId1, group1.getGroupId());
        assertEquals(groupId2, group2.getGroupId());
    }

    @Test
    @Order(6)
    void testGetGroupsWithLessSize() throws SQLException {
        int groupSize = 16;
        List<Group> groupList = groupService.getGroupsWithLessStudentCount(groupSize);
        assertNotNull(groupList);
        for (Group group : groupList) {
            assertTrue(group.getSize() <= groupSize);
        }
    }
}
