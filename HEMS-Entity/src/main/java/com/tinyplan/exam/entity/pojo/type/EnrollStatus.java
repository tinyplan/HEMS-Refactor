package com.tinyplan.exam.entity.pojo.type;

public enum EnrollStatus {
    FINISH_EXAM(2, "完成考试"),
    ENROLL_SUCCESS(1, "报名成功"),
    WAITING_PAY(0, "待付款"),
    ENROLL_FAIL(-1, "报名失败");

    private final Integer code;
    private final String description;

    EnrollStatus(Integer code, String description) {
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
