package com.tinyplan.exam.dao;

import com.tinyplan.exam.entity.po.CandidateDetail;
import com.tinyplan.exam.entity.po.Enroll;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollMapper {

    /**
     * 查询报名成功且报考的考试还未开考的报名信息
     *
     * @param candidateId 考生ID
     */
    List<Enroll> getLivedEnrollByCandidateId(String candidateId);

    Integer insertEnroll(@Param("enroll") Enroll enroll, @Param("detail") CandidateDetail detail);

    Integer updateEnrollStatus(@Param("enrollId") String enrollId, @Param("status") Integer status);

}
