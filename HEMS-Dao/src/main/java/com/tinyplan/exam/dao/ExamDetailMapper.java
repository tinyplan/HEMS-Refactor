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
     * 查询是否有存活的考试(成绩发布之前都记做存活)
     *
     * @param examId 考试ID
     */
    List<ExamDetail> getLivedExamDetail(@Param("examId") String examId);

    /**
     * 查询所有存活的考试
     */
    List<ExamDetail> getAllLivedExamDetail();

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

    Integer updateExamRemain(@Param("examNo") String examNo, @Param("remain") Integer remain);

    Integer getExamRemain(@Param("examNo") String examNo);

    ExamDetail getExamDetailByNo(@Param("examNo") String examNo);

    List<ExamDetail> getExamDetailByStatus(@Param("status") Integer status);

    Integer getExamDetailCountByStatus(@Param("status") Integer status);

    List<ExamDetail> getExamDetailByCondition(@Param("examName") String examName,
                                              @Param("level") Integer level,
                                              @Param("status") Integer status);

    /**
     * 查询此状态之前的考试详细信息
     *
     * @param status 要查询的状态
     */
    List<ExamDetail> getExamDetailBeforeQueryStatus(@Param("status") Integer status);

    Integer updateExamDetail(ExamDetail examDetail);

}
