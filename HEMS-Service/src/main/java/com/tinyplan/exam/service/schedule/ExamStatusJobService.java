package com.tinyplan.exam.service.schedule;

import com.tinyplan.exam.entity.dto.ExamDetailExecuteResult;

import java.util.List;

public interface ExamStatusJobService {

    List<ExamDetailExecuteResult> executeJob();

}
