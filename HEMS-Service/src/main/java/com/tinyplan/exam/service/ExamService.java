package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.po.Exam;
import com.tinyplan.exam.entity.po.ExamDetail;
import com.tinyplan.exam.entity.vo.ExamDetailVO;
import com.tinyplan.exam.entity.vo.Pagination;

import java.util.List;
import java.util.Map;

public interface ExamService {

    Map<String, List<List<Exam>>> getExam();

    Map<String, Object> getExamByLevel(Integer level);

    void addExamDetail(ExamDetail examDetail);

    Pagination<ExamDetailVO> getExam(Integer pageSize);

    void updateExamStatus(String examNo, Integer status);

}
