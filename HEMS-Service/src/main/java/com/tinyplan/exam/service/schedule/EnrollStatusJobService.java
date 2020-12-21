package com.tinyplan.exam.service.schedule;

import com.tinyplan.exam.entity.dto.EnrollExecuteResult;

import java.util.List;

public interface EnrollStatusJobService {

    List<EnrollExecuteResult> executeJob();

    Integer addJobs(String enrollId, String enrollEnd);

}
