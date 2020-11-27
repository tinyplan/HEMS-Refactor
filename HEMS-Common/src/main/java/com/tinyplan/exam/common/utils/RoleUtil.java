package com.tinyplan.exam.common.utils;

import com.tinyplan.exam.entity.pojo.UserType;

import java.util.HashMap;
import java.util.Map;

public class RoleUtil {
    public static final Map<String, UserType> ROLE_MAP;

    static {
        ROLE_MAP = new HashMap<>();
        ROLE_MAP.put("r1001", UserType.SYSTEM_ADMIN);
        ROLE_MAP.put("r1002", UserType.EDU_ADMIN);
        ROLE_MAP.put("r1003", UserType.CANDIDATE);
        ROLE_MAP.put("r1004", UserType.INVIGILATOR);
    }

    public static UserType getUserType(String roleId) {
        return ROLE_MAP.get(roleId);
    }

}
