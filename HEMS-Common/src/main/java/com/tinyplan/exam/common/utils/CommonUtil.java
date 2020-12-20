package com.tinyplan.exam.common.utils;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

public class CommonUtil {

    /**
     * 检查从表中查询出的最大ID
     * 防止当表为空时，查询的结果为null的情况
     *
     * @param maxId 查询出的最大ID
     */
    public static Integer checkMaxId(Integer maxId) {
        return maxId == null ? 0 : maxId;
    }

    public static boolean later(Date begin, Date end) {
        return DateUtil.between(begin, end, DateUnit.MINUTE, false) > 0;
    }

}
