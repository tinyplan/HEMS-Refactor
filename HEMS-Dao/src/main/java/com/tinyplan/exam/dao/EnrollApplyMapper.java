package com.tinyplan.exam.dao;

import com.tinyplan.exam.entity.po.EnrollApply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;

@Repository
public interface EnrollApplyMapper {

    Integer getMaxId();

    Integer insertEnrollApply(EnrollApply apply);

    List<EnrollApply> getEnrollApplyByStatus(@Param("status") Integer status);

    Integer updateEnrollApply(EnrollApply apply);

}
