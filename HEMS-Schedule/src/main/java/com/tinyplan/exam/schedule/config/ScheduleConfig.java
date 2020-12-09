package com.tinyplan.exam.schedule.config;

import com.tinyplan.exam.schedule.job.ExamStatusJob;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.*;

import java.util.Objects;

/**
 * 静态定时任务 配置类
 */
@Configuration
public class ScheduleConfig {

    @Bean
    public JobDetailFactoryBean examStatusJobDetail(){
        JobDetailFactoryBean jobDetail = new JobDetailFactoryBean();
        // 指定工作
        jobDetail.setJobClass(ExamStatusJob.class);
        jobDetail.setGroup("job");
        jobDetail.setName("examStatusJob");
        jobDetail.setDurability(true);
        return jobDetail;
    }

    @Bean
    public CronTriggerFactoryBean examStatusTrigger(JobDetailFactoryBean examStatusJobDetail){
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        // 关联job
        cronTriggerFactoryBean.setJobDetail(Objects.requireNonNull(examStatusJobDetail.getObject()));
        cronTriggerFactoryBean.setCronExpression("0/5 * 6-23 * * ? *");
        // 每天7点开始, 每半小时执行一次, 一直到18点
        // cronTriggerFactoryBean.setCronExpression("0 0/30 7-18 * * ? *");
        cronTriggerFactoryBean.setGroup("trigger");
        cronTriggerFactoryBean.setName("examStatusTrigger");
        return cronTriggerFactoryBean;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(SpringBeanJobFactory myJobFactory, CronTriggerFactoryBean examStatusTrigger) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(myJobFactory);
        schedulerFactoryBean.setTriggers(examStatusTrigger.getObject());
        return schedulerFactoryBean;
    }



}
