package com.tinyplan.exam.service.schedule.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.tinyplan.exam.common.utils.CommonUtil;
import com.tinyplan.exam.common.utils.PrefixUtil;
import com.tinyplan.exam.dao.ExamDetailMapper;
import com.tinyplan.exam.dao.ExamStatusJobMapper;
import com.tinyplan.exam.entity.dto.ExamDetailExecuteResult;
import com.tinyplan.exam.entity.po.ExamDetail;
import com.tinyplan.exam.entity.po.StatusJob;
import com.tinyplan.exam.entity.pojo.type.ExamStatus;
import com.tinyplan.exam.entity.pojo.type.JobExecuteStatus;
import com.tinyplan.exam.entity.pojo.type.ObjectType;
import com.tinyplan.exam.service.schedule.ExamStatusJobService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExamStatusJobServiceImpl implements ExamStatusJobService {

    @Resource(name = "examStatusJobMapper")
    private ExamStatusJobMapper examStatusJobMapper;

    @Resource(name = "examDetailMapper")
    private ExamDetailMapper examDetailMapper;

    /**
     * 执行任务
     *
     * 业务说明(以下用cur表示考试当前的状态, original表示任务标识的原始状态):
     *  1.  cur < original 不会执行任务, 这样可能会导致任务可能很久不会被执行(即使到了任务的执行时间,
     *      若考试依旧停留在之前的状态, 任务便不会执行)
     *  2.  cur = original 没什么好说的, 任务正常执行
     *  3.  cur > original 任务过期
     *
     * @return 任务执行的结果
     */
    @Override
    @Transactional
    public List<ExamDetailExecuteResult> executeJob() {
        // 查询未执行的任务
        List<StatusJob> livedJobList = examStatusJobMapper.getJobByStatus(JobExecuteStatus.NOT_EXECUTE.getCode());
        List<ExamDetailExecuteResult> result = new ArrayList<>(livedJobList.size());
        // 查询对应的考试详细信息
        for (StatusJob job : livedJobList) {
            ExamDetailExecuteResult executeResult = new ExamDetailExecuteResult();
            executeResult.setJobId(job.getJobId());
            executeResult.setOriginalStatus(job.getOriginalStatus());
            executeResult.setUpdateStatus(job.getUpdateStatus());
            ExamDetail examDetail = examDetailMapper.getExamDetailByNo(job.getExamNo());
            executeResult.setExamNo(examDetail.getExamNo());
            executeResult.setExamName(examDetail.getExamName());
            // 比较考试当前状态和任务中记录的原始状态
            if (examDetail.getStatus() > job.getOriginalStatus()) {
                // 标记任务过期
                examStatusJobMapper.updateJobStatus(job.getJobId(), JobExecuteStatus.CANCEL.getCode());
                executeResult.setExecuteResult("任务过期");
            } else if (examDetail.getStatus().equals(job.getOriginalStatus())){
                // 只有当任务标明的初始状态与考试当前的状态相同时, 才会执行任务
                Date executeTime = DateUtil.parse(job.getExecuteTime(), "yyyy-MM-dd HH:mm");
                if (DateUtil.between(executeTime, new Date(), DateUnit.MINUTE, false) >= 0){
                    // 更新考试状态
                    examDetailMapper.updateExamStatus(job.getExamNo(), job.getUpdateStatus());
                    // 标记任务已经完成
                    examStatusJobMapper.updateJobStatus(job.getJobId(), JobExecuteStatus.EXECUTE.getCode());
                    executeResult.setExecuteResult("任务完成");
                }
            }
            result.add(executeResult);
        }
        return result;
    }

    @Override
    @Transactional
    public Integer addJobs(ExamDetail examDetail) {
        StatusJob job = new StatusJob(JobExecuteStatus.NOT_EXECUTE.getCode());
        String prefix = PrefixUtil.getObjectPrefix(ObjectType.SCHEDULE_JOB);
        String date = DateUtil.format(new Date(), "yyyyMMdd");

        String maxId = String.valueOf(CommonUtil.checkMaxId(examStatusJobMapper.getMaxId() + 1));
        job.setJobId(PrefixUtil.generateId(prefix, date, maxId));
        job.setExamNo(examDetail.getExamNo());
        job.setExecuteTime(examDetail.getEnrollStart());
        job.setOriginalStatus(ExamStatus.BEFORE_ENROLL.getCode());
        job.setUpdateStatus(ExamStatus.DURING_ENROLL.getCode());
        examStatusJobMapper.insertJob(job);

        maxId = String.valueOf(CommonUtil.checkMaxId(examStatusJobMapper.getMaxId() + 1));
        job.setJobId(PrefixUtil.generateId(prefix, date, maxId));
        job.setExecuteTime(examDetail.getEnrollEnd());
        job.setOriginalStatus(ExamStatus.DURING_ENROLL.getCode());
        job.setUpdateStatus(ExamStatus.ARRANGING.getCode());
        examStatusJobMapper.insertJob(job);

        maxId = String.valueOf(CommonUtil.checkMaxId(examStatusJobMapper.getMaxId() + 1));
        job.setJobId(PrefixUtil.generateId(prefix, date, maxId));
        job.setExecuteTime(examDetail.getExamStart());
        job.setOriginalStatus(ExamStatus.BEFORE_EXAM.getCode());
        job.setUpdateStatus(ExamStatus.DURING_EXAM.getCode());
        examStatusJobMapper.insertJob(job);

        maxId = String.valueOf(CommonUtil.checkMaxId(examStatusJobMapper.getMaxId() + 1));
        job.setJobId(PrefixUtil.generateId(prefix, date, maxId));
        job.setExecuteTime(examDetail.getExamEnd());
        job.setOriginalStatus(ExamStatus.DURING_EXAM.getCode());
        job.setUpdateStatus(ExamStatus.DURING_CHECK.getCode());
        examStatusJobMapper.insertJob(job);

        return 4;
    }
}
