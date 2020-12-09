package com.tinyplan.exam.dao;

import com.tinyplan.exam.entity.po.StatusJob;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamStatusJobMapper {

    /**
     * 查询所有未执行的任务
     */
    List<StatusJob> getJobByLivedStatus();

    Integer updateJobStatus(@Param("jobId") String jobId, @Param("status") Integer status);

}
