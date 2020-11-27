package com.tinyplan.exam.common.aop;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.tinyplan.exam.common.annotation.SysLog;
import com.tinyplan.exam.common.service.LogService;
import com.tinyplan.exam.common.utils.RequestUtil;
import com.tinyplan.exam.entity.pojo.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 日志切面类
 */
@Aspect
@Component
public class LogAspect {

    @Resource(name = "logServiceImpl")
    private LogService logService;

    // 切入点
    @Pointcut("execution(public * com.tinyplan.exam.*.controller.*.*(..))")
    private void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 检查当前的切入点是否为方法
        Signature signature = point.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能作用于方法");
        }
        MethodSignature methodSignature = (MethodSignature) signature;

        // 获取真正的Method对象
        Method method = null;
        try {
            method = point.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        Object object = null;
        if (null != method && method.isAnnotationPresent(SysLog.class)) {
            Log log = new Log();

            log.setUserId("user123456");
            log.setDate(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), "yyyy-MM-dd HH:mm:ss"));

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            log.setIp(RequestUtil.getIpAddress(request));

            SysLog sysLog = method.getAnnotation(SysLog.class);
            log.setModule(sysLog.module());
            log.setMethod(sysLog.method());

            long start = System.currentTimeMillis();
            try {
                object = point.proceed();
                log.setResult("success");
            } catch (Throwable e) {
                log.setResult("fail");
                e.printStackTrace();
            } finally {
                long end = System.currentTimeMillis();
                log.setExecuteTime((end - start) * 1.0);
                logService.save(log);
            }
        } else {
            // 不需要拦截 或 没有包含注解
            object = point.proceed();
        }

        return object;
    }

}
