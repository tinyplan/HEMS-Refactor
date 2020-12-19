package com.tinyplan.exam.dao;

import com.tinyplan.exam.entity.po.EnrollStatusJobInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollStatusJobMapper {

    Integer getMaxId();

    List<EnrollStatusJobInfo> getJobByStatus(@Param("status") Integer status);

    Integer updateJobStatus(@Param("jobId") String jobId, @Param("status") Integer status);

    Integer insertJob(EnrollStatusJobInfo job);

}
