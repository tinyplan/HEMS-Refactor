package com.tinyplan.exam.dao;

import com.tinyplan.exam.entity.po.ExamDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamDetailMapper {

    Integer getMaxId();

    Integer insertExamDetail(ExamDetail detail);

    /**
     * 查询是否有存活的考试
     * @param examId
     * @return
     */
    List<ExamDetail> getLivedExamDetail(@Param("examId") String examId);

    /**
     * 查询此类型的考试以及符合查询的状态
     *
     * @param examId 考试ID
     * @param status 考试状态
     */
    ExamDetail getExamDetailByIdAndStatus(@Param("examId") String examId, @Param("status") Integer status);

    /**
     * 修改考试状态
     *
     * @param examNo 考试序号
     * @param status 修改状态
     */
    Integer updateExamStatus(@Param("examNo") String examNo, @Param("status") Integer status);

    ExamDetail getExamDetailByNo(@Param("examNo") String examNo);

}
