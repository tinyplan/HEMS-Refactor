package com.tinyplan.exam.entity.pojo.type;

public enum  JobExecuteStatus {
    CANCEL(-1, "过时取消"),
    NOT_EXECUTE(0, "未执行"),
    EXECUTE(1, "已执行");

    private final Integer code;
    private final String description;

    JobExecuteStatus(Integer code, String description) {
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
