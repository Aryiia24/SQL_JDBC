package ru.fedormakarov.foxminded.task7.sql.entity;

import java.util.HashSet;
import java.util.Set;

public class Student {

    private int id;
    private int groupId;
    private String firstName;
    private String lastName;
    private HashSet<Course> courses = new HashSet<>();

    public Student() {

    }

    public int getStudentId() {
        return id;
    }

    public void setStudentId(int studentId) {
        this.id = studentId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = (HashSet<Course>) courses;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + groupId;
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (groupId != other.groupId)
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "[studentId = " + id + ", groupId = " + groupId + ", firstName = " + firstName + ", lastName = "
                + lastName + "]";
    }

}
