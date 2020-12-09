package com.tinyplan.exam.schedule.job;

import com.tinyplan.exam.common.utils.type.ExamStatusUtil;
import com.tinyplan.exam.entity.dto.ExamDetailExecuteResult;
import com.tinyplan.exam.service.schedule.ExamStatusJobService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.util.List;

/**
 * 负责检查考试状态的Job
 */
public class ExamStatusJob extends QuartzJobBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExamStatusJob.class);

    @Resource(name = "examStatusJobServiceImpl")
    private ExamStatusJobService examStatusJobService;

    public ExamStatusJob() {}

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        LOGGER.info("开始轮询考试状态任务...");
        long start = System.currentTimeMillis();
        List<ExamDetailExecuteResult> resultList = examStatusJobService.executeJob();
        long end = System.currentTimeMillis();
        LOGGER.info("结束轮询考试状态任务...耗时: " + (end - start) / 1000.0 + "s");
        if (resultList.size() == 0){
            LOGGER.info("无任务被执行！");
        }else {
            LOGGER.info("任务执行结果: ");
            for (ExamDetailExecuteResult result : resultList) {
                LOGGER.info(String.format(ExamDetailExecuteResult.LOG_TEMPLATE,
                        result.getExamNo(), result.getExamName(),
                        ExamStatusUtil.getStatus(result.getOriginalStatus()).getDescription(),
                        ExamStatusUtil.getStatus(result.getUpdateStatus()).getDescription(),
                        result.getExecuteResult()));
            }
        }
    }
}
