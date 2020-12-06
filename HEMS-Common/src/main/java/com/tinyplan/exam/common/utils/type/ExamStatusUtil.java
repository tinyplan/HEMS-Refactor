package com.tinyplan.exam.common.utils.type;

import com.tinyplan.exam.entity.pojo.type.ExamStatus;

import java.util.HashMap;
import java.util.Map;

public class ExamStatusUtil {
    private static final Map<Integer, ExamStatus> EXAM_STATUS_MAP;

    static {
        EXAM_STATUS_MAP = new HashMap<>();
        for (ExamStatus status : ExamStatus.values()) {
            EXAM_STATUS_MAP.put(status.getCode(), status);
        }
    }

    public static ExamStatus getStatus(Integer statusCode) {
        return EXAM_STATUS_MAP.get(statusCode);
    }


}
