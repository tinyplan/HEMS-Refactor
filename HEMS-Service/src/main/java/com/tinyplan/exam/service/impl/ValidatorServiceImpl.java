package com.tinyplan.exam.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.tinyplan.exam.entity.form.AddExamInfoForm;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import com.tinyplan.exam.service.ValidatorService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ValidatorServiceImpl implements ValidatorService {

    @Override
    public boolean check(AddExamInfoForm form) {
        ValidatorFactory factory = ValidatorFactory.newInstance();
        boolean valid = true;
        Field[] fields = form.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean accessFlag = field.isAccessible();
            field.setAccessible(true);
            try {
                valid = factory.isValid(field.getType(), field.get(form));
            } catch (IllegalAccessException e) {
                throw new BusinessException(ResultStatus.RES_INVALID_PARAM);
            }
            field.setAccessible(accessFlag);
            if (!valid) {
                throw new BusinessException(ResultStatus.RES_INVALID_PARAM);
            }
        }
        Date now = new Date();
        Date enrollStart = DateUtil.parse(form.getEnrollStart(), "yyyy-MM-dd HH:mm");
        Date enrollEnd = DateUtil.parse(form.getEnrollEnd(), "yyyy-MM-dd HH:mm");
        Date examStart = DateUtil.parse(form.getExamStart(), "yyyy-MM-dd HH:mm");
        Date examEnd = DateUtil.parse(form.getExamEnd(), "yyyy-MM-dd HH:mm");
        if (!(later(now, enrollStart) && later(enrollStart, enrollEnd) && later(enrollEnd, examStart) && later(examStart, examEnd))) {
            valid = false;
        }
        return valid;
    }

    private boolean later(Date begin, Date end) {
        return DateUtil.between(begin, end, DateUnit.MINUTE, false) > 0;
    }
}

interface Validator {
    boolean isValid(Object raw);
}

class ValidatorFactory {
    private static ValidatorFactory INSTANCE;
    private final Map<String, Validator> VALIDATOR_MAP;

    private ValidatorFactory() {
        StringValidator stringValidator = new StringValidator();
        IntegerValidator integerValidator = new IntegerValidator();
        DoubleValidator doubleValidator = new DoubleValidator();

        VALIDATOR_MAP = new HashMap<>();
        VALIDATOR_MAP.put(String.class.getCanonicalName(), stringValidator);
        VALIDATOR_MAP.put(Integer.class.getCanonicalName(), integerValidator);
        VALIDATOR_MAP.put(Double.class.getCanonicalName(), doubleValidator);
    }

    public static ValidatorFactory newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ValidatorFactory();
        }
        return INSTANCE;
    }

    public <T> boolean isValid(Class<T> type, Object data) {
        Validator validator = VALIDATOR_MAP.get(type.getCanonicalName());
        if (validator == null) {
            throw new BusinessException(ResultStatus.RES_INVALID_PARAM);
        }
        return validator.isValid(data);
    }

}

class StringValidator implements Validator {
    @Override
    public boolean isValid(Object raw) {
        String param = (String) raw;
        return !(param == null || "".equals(param));
    }
}

class IntegerValidator implements Validator {
    @Override
    public boolean isValid(Object raw) {
        Integer param = (Integer) raw;
        return param != null && param >= 0;
    }
}

class DoubleValidator implements Validator {
    @Override
    public boolean isValid(Object raw) {
        Double param = (Double) raw;
        return param != null && param >= 0.00;
    }
}


