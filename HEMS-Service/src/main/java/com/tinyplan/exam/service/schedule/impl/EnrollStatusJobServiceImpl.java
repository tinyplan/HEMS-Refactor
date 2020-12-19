package com.tinyplan.exam.service.schedule.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.tinyplan.exam.common.utils.CommonUtil;
import com.tinyplan.exam.common.utils.PrefixUtil;
import com.tinyplan.exam.dao.EnrollMapper;
import com.tinyplan.exam.dao.EnrollStatusJobMapper;
import com.tinyplan.exam.entity.dto.EnrollExecuteResult;
import com.tinyplan.exam.entity.po.Enroll;
import com.tinyplan.exam.entity.po.EnrollStatusJobInfo;
import com.tinyplan.exam.entity.pojo.type.EnrollStatus;
import com.tinyplan.exam.entity.pojo.type.JobExecuteStatus;
import com.tinyplan.exam.entity.pojo.type.ObjectType;
import com.tinyplan.exam.service.schedule.EnrollStatusJobService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EnrollStatusJobServiceImpl implements EnrollStatusJobService {

    @Resource(name = "enrollMapper")
    private EnrollMapper enrollMapper;

    @Resource(name = "enrollStatusJobMapper")
    private EnrollStatusJobMapper enrollStatusJobMapper;

    @Override
    @Transactional
    public List<EnrollExecuteResult> executeJob() {
        List<EnrollStatusJobInfo> livedJobList = enrollStatusJobMapper.getJobByStatus(JobExecuteStatus.NOT_EXECUTE.getCode());
        List<EnrollExecuteResult> resultList = new ArrayList<>(livedJobList.size());
        for (EnrollStatusJobInfo job : livedJobList) {
            Enroll enroll = enrollMapper.getEnrollById(job.getEnrollId());
            EnrollExecuteResult result = new EnrollExecuteResult(enroll, job);
            // 报名的状态没有明显的时序，只要报名当前的状态与任务标识的状态不符，可以直接判定任务过期
            if (job.getOriginalStatus().equals(enroll.getStatus())) {
                Date executeTime = DateUtil.parse(job.getExecuteTime(), "yyyy-MM-dd HH:mm");
                if (DateUtil.between(executeTime, new Date(), DateUnit.MINUTE, false) >= 0) {
                    // 更新报名信息状态
                    enrollMapper.updateEnrollStatus(enroll.getEnrollId(), job.getUpdateStatus());
                    // 更新任务状态
                    enrollStatusJobMapper.updateJobStatus(job.getJobId(), JobExecuteStatus.EXECUTE.getCode());
                    result.setExecuteResult("任务完成");
                    resultList.add(result);
                }
            } else {
                enrollStatusJobMapper.updateJobStatus(job.getJobId(), JobExecuteStatus.CANCEL.getCode());
                result.setExecuteResult("任务过期");
                resultList.add(result);
            }
        }
        return resultList;
    }

    @Override
    @Transactional
    public Integer addJobs(String enrollId) {
        EnrollStatusJobInfo job = new EnrollStatusJobInfo();

        String prefix = PrefixUtil.getObjectPrefix(ObjectType.ENROLL_JOB);
        String date = DateUtil.format(new Date(), "yyyyMMdd");
        String maxId = String.valueOf(CommonUtil.checkMaxId(enrollStatusJobMapper.getMaxId()) + 1);
        job.setJobId(PrefixUtil.generateId(prefix, date, maxId));

        job.setEnrollId(enrollId);
        job.setOriginalStatus(EnrollStatus.WAITING_PAY.getCode());
        job.setUpdateStatus(EnrollStatus.ENROLL_FAIL.getCode());
        job.setStatus(JobExecuteStatus.NOT_EXECUTE.getCode());

        Date executeTime = DateUtil.offsetDay(new Date(), 1);
        job.setExecuteTime(DateUtil.format(executeTime, "yyyy-MM-dd HH:mm"));
        return enrollStatusJobMapper.insertJob(job);
    }
}
