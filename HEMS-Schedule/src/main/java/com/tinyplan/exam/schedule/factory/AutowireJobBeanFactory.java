package com.tinyplan.exam.schedule.factory;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

/**
 * 为Job自动注入bean的工厂类
 */
@Component("autowireJobBeanFactory")
public class AutowireJobBeanFactory extends SpringBeanJobFactory {
    private final AutowireCapableBeanFactory beanFactory;

    @Autowired
    public AutowireJobBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        // 获取Job实例
        Object job = super.createJobInstance(bundle);
        // 自动装配Job
        beanFactory.autowireBean(job);
        return job;
    }
}
