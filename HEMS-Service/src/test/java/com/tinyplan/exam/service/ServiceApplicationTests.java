package com.tinyplan.exam.service;

import cn.hutool.core.date.DateUtil;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.tinyplan.exam.entity.form.AddExamInfoForm;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.Date;

/*@SpringBootTest*/
class ServiceApplicationTests {

    @Test
    void contextLoads() throws IllegalAccessException {
        AddExamInfoForm form = new AddExamInfoForm();
        form.setEnrollEnd("2020-10-02 09:00");
        form.setCapacity(1000);
        form.setPassLine(0.0);
        Field[] fields = form.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean accessFlag = field.isAccessible();
            field.setAccessible(true);
            System.out.println(field.getName() + " " + field.get(form));
            System.out.println("valid = " + isValid(field.getType(), field.get(form)));
            field.setAccessible(accessFlag);
        }
    }

    private <T> boolean isValid(Class<T> type, Object data) {
        String classType = type.getCanonicalName();
        if (String.class.getCanonicalName().equals(classType)) {
            return isValid((String) data);
        }else if (Integer.class.getCanonicalName().equals(classType)) {
            return isValid((Integer) data);
        } else if (Double.class.getCanonicalName().equals(classType)) {
            return isValid((Double) data);
        } else {
            throw new BusinessException(ResultStatus.RES_INVALID_PARAM);
        }
    }

    private boolean isValid(String param) {
        return !(param == null || "".equals(param));
    }

    private boolean isValid(Integer param) {
        return param != null && param >= 0;
    }

    private boolean isValid(Double param) {
        return param != null && param >= 0.00;
    }

    @Test
    void test(){
        Date now = new Date();
        System.out.println(DateUtil.format(now, "yyyyMMdd"));
    }

}
