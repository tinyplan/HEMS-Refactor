package com.tinyplan.exam.dao;

import com.tinyplan.exam.entity.po.ExamDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamDetailMapper {

    Integer getMaxId();

    Integer insertExamDetail(ExamDetail detail);

}
