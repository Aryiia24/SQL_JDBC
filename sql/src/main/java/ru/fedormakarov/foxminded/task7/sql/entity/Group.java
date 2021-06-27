package ru.fedormakarov.foxminded.task7.sql.entity;

public class Group {
    private int id;
    private String groupName;
    private int size;

    public Group() {

    }

    public int getGroupId() {
        return id;
    }

    public void setGroupId(int groupId) {
        this.id = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
        result = prime * result + size;
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
        Group other = (Group) obj;
        if (id != other.id)
            return false;
        if (groupName == null) {
            if (other.groupName != null)
                return false;
        } else if (!groupName.equals(other.groupName))
            return false;
        if (size != other.size)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "[groupId=" + id + ", groupName=" + groupName + ", size=" + size + "]";
    }

}
