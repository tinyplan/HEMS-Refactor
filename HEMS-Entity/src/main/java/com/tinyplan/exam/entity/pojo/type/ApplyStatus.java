package com.tinyplan.exam.entity.pojo.type;

public enum ApplyStatus {
    PASS(1, "通过审核"),
    AUDIT(0, "待审核"),
    REJECT(-1, "拒绝");

    private final Integer code;
    private final String description;

    ApplyStatus(Integer code, String description) {
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
