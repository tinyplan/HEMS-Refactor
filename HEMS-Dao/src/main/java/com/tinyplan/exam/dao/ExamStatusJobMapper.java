package com.tinyplan.exam.dao;

import com.tinyplan.exam.entity.po.StatusJob;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamStatusJobMapper {

    Integer getMaxId();

    /**
     * 根据状态查询任务
     *
     * @param status 任务状态 {@link com.tinyplan.exam.entity.pojo.type.JobExecuteStatus}
     */
    List<StatusJob> getJobByStatus(@Param("status") Integer status);

    /**
     * 更新任务状态
     *
     * @param jobId 任务ID
     * @param status 更新的状态
     */
    Integer updateJobStatus(@Param("jobId") String jobId, @Param("status") Integer status);

    /**
     * 插入一个任务
     *
     * @param statusJob 任务
     */
    Integer insertJob(StatusJob statusJob);

    Integer updateJobExecuteTime(@Param("job") StatusJob job, @Param("oldTime") String oldTime);

}
