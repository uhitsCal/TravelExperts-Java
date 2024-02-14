package model;

import jakarta.persistence.*;

@Entity
public class Classes {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ClassId")
    private String classId;
    @Basic
    @Column(name = "ClassName")
    private String className;
    @Basic
    @Column(name = "ClassDesc")
    private String classDesc;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassDesc() {
        return classDesc;
    }

    public void setClassDesc(String classDesc) {
        this.classDesc = classDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Classes classes = (Classes) o;

        if (classId != null ? !classId.equals(classes.classId) : classes.classId != null) return false;
        if (className != null ? !className.equals(classes.className) : classes.className != null) return false;
        if (classDesc != null ? !classDesc.equals(classes.classDesc) : classes.classDesc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = classId != null ? classId.hashCode() : 0;
        result = 31 * result + (className != null ? className.hashCode() : 0);
        result = 31 * result + (classDesc != null ? classDesc.hashCode() : 0);
        return result;
    }
}
