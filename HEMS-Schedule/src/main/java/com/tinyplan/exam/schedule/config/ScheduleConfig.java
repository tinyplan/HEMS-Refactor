package com.tinyplan.exam.schedule.config;

import com.tinyplan.exam.common.properties.HEMSProperties;
import com.tinyplan.exam.schedule.job.EnrollStatusJob;
import com.tinyplan.exam.schedule.job.ExamStatusJob;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 静态定时任务 配置类
 */
@Configuration
public class ScheduleConfig {

    @Resource(name = "hemsProperties")
    private HEMSProperties hemsProperties;

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
    public CronTriggerFactoryBean examStatusTrigger(@Qualifier("examStatusJobDetail") JobDetailFactoryBean examStatusJobDetail){
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        // 关联job
        cronTriggerFactoryBean.setJobDetail(Objects.requireNonNull(examStatusJobDetail.getObject()));
        if (hemsProperties.isDebug()) {
            cronTriggerFactoryBean.setCronExpression("0/5 * 6-23 * * ? *");
            // cronTriggerFactoryBean.setCronExpression("* 0/1 6-23 * * ? *");
        } else {
            // 每天7点开始, 每半小时执行一次, 一直到18点
            cronTriggerFactoryBean.setCronExpression("0 0/30 7-18 * * ? *");
        }
        cronTriggerFactoryBean.setGroup("trigger");
        cronTriggerFactoryBean.setName("examStatusTrigger");
        return cronTriggerFactoryBean;
    }

    @Bean
    public JobDetailFactoryBean enrollStatusJobDetail(){
        JobDetailFactoryBean jobDetail = new JobDetailFactoryBean();
        // 指定工作
        jobDetail.setJobClass(EnrollStatusJob.class);
        jobDetail.setGroup("job");
        jobDetail.setName("enrollStatusJob");
        jobDetail.setDurability(true);
        return jobDetail;
    }

    @Bean
    public CronTriggerFactoryBean enrollStatusTrigger(@Qualifier("enrollStatusJobDetail") JobDetailFactoryBean enrollStatusJobDetail){
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        // 关联job
        cronTriggerFactoryBean.setJobDetail(Objects.requireNonNull(enrollStatusJobDetail.getObject()));
        if (hemsProperties.isDebug()) {
            cronTriggerFactoryBean.setCronExpression("0/10 * 6-23 * * ? *");
        } else {
            // 每天7点开始, 每半小时执行一次, 一直到18点
            cronTriggerFactoryBean.setCronExpression("0 0/30 7-18 * * ? *");
        }
        cronTriggerFactoryBean.setGroup("trigger");
        cronTriggerFactoryBean.setName("enrollStatusJobDetail");
        return cronTriggerFactoryBean;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(SpringBeanJobFactory myJobFactory,
                                                     @Qualifier("examStatusTrigger") CronTriggerFactoryBean examStatusTrigger,
                                                     @Qualifier("enrollStatusTrigger") CronTriggerFactoryBean enrollStatusTrigger) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(myJobFactory);
        schedulerFactoryBean.setTriggers(examStatusTrigger.getObject(), enrollStatusTrigger.getObject());
        return schedulerFactoryBean;
    }

}
