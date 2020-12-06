package com.tinyplan.exam.entity.pojo.type;

public enum ExamStatus {
    // 未开始报名
    BEFORE_ENROLL(0),
    // 报名中
    DURING_ENROLL(1),
    // 分配中
    ARRANGING(2),
    // 待开考
    BEFORE_EXAM(3),
    // 已开考
    DURING_EXAM(4),
    // 阅卷中
    DURING_CHECK(5),
    // 成绩发布
    SCORE_PUBLISH(6);

    private final Integer code;

    ExamStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
