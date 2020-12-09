package com.tinyplan.exam.service.schedule.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.tinyplan.exam.dao.ExamDetailMapper;
import com.tinyplan.exam.dao.ExamStatusJobMapper;
import com.tinyplan.exam.entity.dto.ExamDetailExecuteResult;
import com.tinyplan.exam.entity.po.ExamDetail;
import com.tinyplan.exam.entity.po.StatusJob;
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

    @Override
    @Transactional
    public List<ExamDetailExecuteResult> executeJob() {
        // 查询未执行的任务
        List<StatusJob> livedJobList = examStatusJobMapper.getJobByLivedStatus();
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
            // 检查任务是否过时(比较考试当前状态和任务中记录的初始状态是否一致)
            if (examDetail.getStatus() > job.getOriginalStatus()) {
                // 标记任务过期
                examStatusJobMapper.updateJobStatus(job.getJobId(), -1);
                executeResult.setExecuteResult("任务过期");
            } else {
                // 执行任务
                Date executeTime = DateUtil.parse(job.getExecuteTime(), "yyyy-MM-dd HH:mm");
                Date now = new Date();
                if (DateUtil.between(executeTime, now, DateUnit.MINUTE, false) >= 0){
                    // 更新考试状态
                    examDetailMapper.updateExamStatus(job.getExamNo(), job.getUpdateStatus());
                    // 标记任务已经完成
                    examStatusJobMapper.updateJobStatus(job.getJobId(), 1);
                    executeResult.setExecuteResult("任务完成");
                }
            }
            result.add(executeResult);
        }
        return result;
    }
}
