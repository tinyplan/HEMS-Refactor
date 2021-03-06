package com.tinyplan.exam.common.interceptor;

import com.tinyplan.exam.common.annotation.Authorization;
import com.tinyplan.exam.common.service.TokenService;
import com.tinyplan.exam.common.service.impl.TokenServiceImpl;
import com.tinyplan.exam.common.utils.RequestUtil;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 授权拦截器：检查token是否合法
 */
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    public AuthorizationInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 映射到方法
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 检测方法是否被@Authorization注解
            if (method.getAnnotation(Authorization.class) != null) {
                String token = RequestUtil.getToken(request);
                if (token == null || !tokenService.checkToken(token)) {
                    // 未通过验证
                    throw new BusinessException(ResultStatus.RES_ILLEGAL_REQUEST);
                } else {
                    // 刷新时间
                    tokenService.flushExpire(token);
                }
            }
        }
        return true;
    }
}
