package com.tinyplan.exam.dao;

import com.tinyplan.exam.entity.po.EnrollApply;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollApplyMapper {

    Integer getMaxId();

    Integer insertEnrollApply(EnrollApply apply);

}
