package com.tinyplan.exam.entity.pojo.type;

public enum ExamStatus {
    // 未开始报名
    BEFORE_ENROLL(0, "未开始报名"),
    // 报名中
    DURING_ENROLL(1, "报名中"),
    // 分配中
    ARRANGING(2, "分配中"),
    // 待开考
    BEFORE_EXAM(3, "待开考"),
    // 已开考
    DURING_EXAM(4, "已开考"),
    // 阅卷中
    DURING_CHECK(5, "阅卷中"),
    // 成绩发布
    SCORE_PUBLISH(6, "成绩发布");

    private final Integer code;
    private final String description;

    ExamStatus(Integer code, String description) {
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
