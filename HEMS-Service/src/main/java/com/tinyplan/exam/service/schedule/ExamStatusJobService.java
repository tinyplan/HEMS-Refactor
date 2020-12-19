package com.tinyplan.exam.service.schedule;

import com.tinyplan.exam.entity.dto.ExamDetailExecuteResult;
import com.tinyplan.exam.entity.dto.UpdateJobOrder;
import com.tinyplan.exam.entity.po.ExamDetail;

import java.util.List;

public interface ExamStatusJobService {

    List<ExamDetailExecuteResult> executeJob();

    Integer addJobs(ExamDetail examDetail);

    Integer updateJob(UpdateJobOrder order);
}
