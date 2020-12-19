package com.tinyplan.exam.schedule.job;

import com.tinyplan.exam.common.utils.type.StatusUtil;
import com.tinyplan.exam.entity.dto.EnrollExecuteResult;
import com.tinyplan.exam.service.schedule.EnrollStatusJobService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.util.List;

public class EnrollStatusJob extends QuartzJobBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnrollStatusJob.class);

    @Resource(name = "enrollStatusJobServiceImpl")
    private EnrollStatusJobService service;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        LOGGER.info("开始轮询报名状态任务...");
        long start = System.currentTimeMillis();
        List<EnrollExecuteResult> resultList = service.executeJob();
        long end = System.currentTimeMillis();
        LOGGER.info("结束轮询报名状态任务...耗时: " + (end - start) / 1000.0 + "s");
        if (resultList.size() == 0){
            LOGGER.info("无任务被执行！");
        }else {
            LOGGER.info("任务执行结果: ");
            for (EnrollExecuteResult result : resultList) {
                LOGGER.info(String.format(EnrollExecuteResult.LOG_TEMPLATE,
                        result.getEnrollId(),
                        StatusUtil.getEnrollStatus(result.getOriginalStatus()).getDescription(),
                        StatusUtil.getEnrollStatus(result.getUpdateStatus()).getDescription(),
                        result.getExecuteResult()));
            }
        }
    }
}
