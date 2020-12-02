package com.tinyplan.exam.common.config;

import com.tinyplan.exam.common.interceptor.AuthorizationInterceptor;
import com.tinyplan.exam.common.properties.HEMSProperties;
import com.tinyplan.exam.common.service.TokenService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

/**
 * 拦截器 配置类
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Resource(name = "tokenServiceImpl")
    private TokenService tokenService;

    @Resource(name = "hemsProperties")
    private HEMSProperties hemsProperties;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        if (!hemsProperties.isDebug()) {
            registry.addInterceptor(new AuthorizationInterceptor(tokenService)).addPathPatterns("/**");
        }
        super.addInterceptors(registry);
    }
}
