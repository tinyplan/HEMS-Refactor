package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.po.Exam;
import com.tinyplan.exam.entity.po.ExamDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExamService {

    Map<String, List<Exam>> getExam();

    Map<String, Object> getExamByLevel(Integer level);

    void addExamDetail(ExamDetail examDetail);

}
