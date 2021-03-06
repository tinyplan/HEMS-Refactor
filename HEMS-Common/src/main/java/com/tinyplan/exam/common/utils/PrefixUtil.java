package com.tinyplan.exam.common.utils;

import com.tinyplan.exam.entity.pojo.type.ObjectType;
import com.tinyplan.exam.entity.pojo.type.UserType;

import java.util.HashMap;
import java.util.Map;

/**
 * ID 前缀
 */
public class PrefixUtil {
    public static final String ID_SEPARATOR = "_";

    public static final Map<UserType, String> USER_PREFIX_MAP;
    public static final Map<ObjectType, String> OBJECT_PREFIX_MAP;

    static {
        USER_PREFIX_MAP = new HashMap<>();
        USER_PREFIX_MAP.put(UserType.CANDIDATE, "can");
        USER_PREFIX_MAP.put(UserType.INVIGILATOR, "inv");
        USER_PREFIX_MAP.put(UserType.SYSTEM_ADMIN, "adm");
        USER_PREFIX_MAP.put(UserType.EDU_ADMIN, "edu");

        OBJECT_PREFIX_MAP = new HashMap<>();
        OBJECT_PREFIX_MAP.put(ObjectType.NEWS, "news");
        OBJECT_PREFIX_MAP.put(ObjectType.ENROLL_APPLY, "ea");
        OBJECT_PREFIX_MAP.put(ObjectType.EXAM_JOB, "job_exam");
        OBJECT_PREFIX_MAP.put(ObjectType.ENROLL_JOB, "job_enroll");
        OBJECT_PREFIX_MAP.put(ObjectType.SITE, "site");
    }

    public static String getUserPrefix(UserType type) {
        return USER_PREFIX_MAP.get(type);
    }

    public static String getObjectPrefix(ObjectType type) {
        return OBJECT_PREFIX_MAP.get(type);
    }

    /**
     * 生成ID
     */
    public static String generateId(String... args){
        return String.join(ID_SEPARATOR, args);
    }

}
