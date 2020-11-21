package com.tinyplan.exam.common.config;

import com.tinyplan.exam.common.interceptor.AuthorizationInterceptor;
import com.tinyplan.exam.common.manager.TokenManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

/**
 * 拦截器 配置类
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Resource(name = "tokenManager")
    private TokenManager tokenManager;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor(tokenManager)).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
