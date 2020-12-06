package com.tinyplan.exam.dao;

import com.tinyplan.exam.entity.po.ExamDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamDetailMapper {

    Integer getMaxId();

    Integer insertExamDetail(ExamDetail detail);

    List<ExamDetail> getLivedExamDetail(@Param("examId") String examId);

}
