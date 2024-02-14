package com.example.workshop6;

public class Classes {
    private String classId;
    private String className;
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

    public Classes(String classId, String className, String classDesc) {
        this.classId = classId;
        this.className = className;
        this.classDesc = classDesc;
    }
}
