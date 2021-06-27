package ru.fedormakarov.foxminded.task7.sql.entity;

public class Course {
    private int id;
    private String courseName;
    private String courseDescription;

    public Course() {
        super();
    }

    public int getCourseId() {
        return id;
    }

    public void setCourseId(int courseId) {
        this.id = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((courseDescription == null) ? 0 : courseDescription.hashCode());
        result = prime * result + id;
        result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
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
        Course other = (Course) obj;
        if (courseDescription == null) {
            if (other.courseDescription != null)
                return false;
        } else if (!courseDescription.equals(other.courseDescription))
            return false;
        if (id != other.id)
            return false;
        if (courseName == null) {
            if (other.courseName != null)
                return false;
        } else if (!courseName.equals(other.courseName))
            return false;
        return true;
    }

}
