package com.tinyplan.exam.common.utils.type;

import com.tinyplan.exam.entity.pojo.type.EnrollStatus;
import com.tinyplan.exam.entity.pojo.type.ExamLevel;
import com.tinyplan.exam.entity.pojo.type.ExamStatus;

import java.util.HashMap;
import java.util.Map;

public class StatusUtil {
    private static final Map<Integer, ExamStatus> EXAM_STATUS_MAP;
    private static final Map<Integer, EnrollStatus> ENROLL_STATUS_MAP;
    private static final Map<Integer, ExamLevel> EXAM_LEVEL_MAP;

    static {
        EXAM_STATUS_MAP = new HashMap<>();
        ENROLL_STATUS_MAP = new HashMap<>();
        EXAM_LEVEL_MAP = new HashMap<>();

        for (ExamStatus status : ExamStatus.values()) {
            EXAM_STATUS_MAP.put(status.getCode(), status);
        }

        for (EnrollStatus status : EnrollStatus.values()) {
            ENROLL_STATUS_MAP.put(status.getCode(), status);
        }

        for (ExamLevel level : ExamLevel.values()) {
            EXAM_LEVEL_MAP.put(level.getCode(), level);
        }
    }

    public static ExamStatus getExamStatus(Integer statusCode) {
        return EXAM_STATUS_MAP.get(statusCode);
    }

    public static EnrollStatus getEnrollStatus(Integer statusCode) {
        return ENROLL_STATUS_MAP.get(statusCode);
    }

    public static ExamLevel getExamLevel(Integer levelCode){
        return EXAM_LEVEL_MAP.get(levelCode);
    }

}
