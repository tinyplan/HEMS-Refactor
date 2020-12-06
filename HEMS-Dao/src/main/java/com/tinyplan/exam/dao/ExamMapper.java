package com.tinyplan.exam.dao;

import com.tinyplan.exam.entity.po.Exam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamMapper {

    List<Exam> getExamByLevel(@Param("level") Integer level);

}
