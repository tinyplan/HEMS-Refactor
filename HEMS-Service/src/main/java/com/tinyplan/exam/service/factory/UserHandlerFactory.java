package com.tinyplan.exam.service.factory;

import com.tinyplan.exam.entity.pojo.type.UserType;
import com.tinyplan.exam.service.UserHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务处理类 静态工厂
 */
public class UserHandlerFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserHandlerFactory.class);
    private static final String TEMPLATE_LOGGER_INFO = "注册业务处理: %s ==> %s";

    private static final Map<UserType, UserHandlerService> USER_HANDLER_SERVICE_MAP;

    static {
        USER_HANDLER_SERVICE_MAP = new HashMap<>();
    }

    /**
     * 获取对应的业务处理类
     *
     * @param type 用户类型
     * @return 业务处理类
     */
    public static UserHandlerService getHandlerService(UserType type) {
        return USER_HANDLER_SERVICE_MAP.get(type);
    }

    /**
     * 注册业务方法
     *
     * @param type 类型
     * @param handlerService 业务处理类
     */
    public static void registerHandlerService(UserType type, UserHandlerService handlerService) {
        LOGGER.info(String.format(TEMPLATE_LOGGER_INFO, type.getName(), handlerService.getClass().getName()));
        USER_HANDLER_SERVICE_MAP.put(type, handlerService);
    }

}
