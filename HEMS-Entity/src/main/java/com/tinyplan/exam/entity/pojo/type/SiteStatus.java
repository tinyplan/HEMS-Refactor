package com.tinyplan.exam.entity.pojo.type;

public enum SiteStatus {
    AVAILABLE(1, "可用"),
    WASTE(-1, "废弃");

    private final Integer code;
    private final String description;

    SiteStatus(Integer code, String description) {
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
