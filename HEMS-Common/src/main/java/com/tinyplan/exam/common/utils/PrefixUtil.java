package com.tinyplan.exam.common.utils;

import com.tinyplan.exam.entity.pojo.UserType;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户ID 前缀
 */
public class PrefixUtil {
    public static final Map<UserType, String> PREFIX_MAP;

    static {
        PREFIX_MAP = new HashMap<>();
        PREFIX_MAP.put(UserType.CANDIDATE, "can");
        PREFIX_MAP.put(UserType.INVIGILATOR, "inv");
        PREFIX_MAP.put(UserType.SYSTEM_ADMIN, "adm");
        PREFIX_MAP.put(UserType.EDU_ADMIN, "edu");
    }

    public static String getPrefix(UserType type){
        return PREFIX_MAP.get(type);
    }

}
