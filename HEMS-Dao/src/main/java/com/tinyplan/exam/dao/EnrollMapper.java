package com.tinyplan.exam.dao;

import com.tinyplan.exam.entity.po.CandidateDetail;
import com.tinyplan.exam.entity.po.Enroll;
import com.tinyplan.exam.entity.po.ExamDetail;
import com.tinyplan.exam.entity.po.Score;
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

    /**
     * 修改报名状态为考试完成(专用)
     */
    Integer updateEnrollStatusByScore(@Param("scoreList") List<Score> scoreList, @Param("status") Integer status);

    /**
     * 根据真实姓名获取报名信息(若该报名信息对应的考试不是在报名中的状态, 将不会被查询)
     *
     * @param realName 真实姓名
     * @param enrollingList 还在报名中的考试列表(应保证不为空)
     */
    List<Enroll> getEnrollByCondition(@Param("enrollingList") List<ExamDetail> enrollingList,
                                      @Param("realName") String realName,
                                      @Param("status") Integer status);

    Integer updateCandidateEnroll(Enroll enroll);

    List<Enroll> getEnrollByCandidateId(@Param("candidateId") String candidateId);

    Enroll getEnrollByCandidateIdAndEnrollId(@Param("candidateId") String candidateId,
                                             @Param("enrollId") String enrollId);

    Enroll getEnrollById(@Param("enrollId") String enrollId);
}
