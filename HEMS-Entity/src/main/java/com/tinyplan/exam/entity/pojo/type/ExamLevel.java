package com.tinyplan.exam.entity.pojo.type;

public enum ExamLevel {
    PRIMARY(1, "初级"),
    MIDDLE(2, "中级"),
    ADVANCE(3, "高级");

    private final Integer code;
    private final String description;

    ExamLevel(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
