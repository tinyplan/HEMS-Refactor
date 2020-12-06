package com.tinyplan.exam.entity.pojo.type;

public enum UserType {
    CANDIDATE("Candidate"),
    INVIGILATOR("Invigilator"),
    SYSTEM_ADMIN("SystemAdmin"),
    EDU_ADMIN("EduAdmin");

    private final String name;

    UserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
