package com.tinyplan.exam.service;

import com.tinyplan.exam.entity.po.ExamDetail;

import java.util.Map;

public interface ExamService {

    Map<String, Object> getExamByLevel(Integer level);

    void addExamInfo(ExamDetail examDetail);

}
