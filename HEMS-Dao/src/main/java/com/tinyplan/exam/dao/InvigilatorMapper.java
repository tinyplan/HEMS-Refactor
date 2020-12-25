package com.tinyplan.exam.dao;

import com.tinyplan.exam.entity.po.Invigilator;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvigilatorMapper {

    Integer getMaxId();

    List<Invigilator> getInvigilatorByStatus(@Param("status") Integer status);

    Integer insertInvigilator(Invigilator invigilator);

    Integer insertInvigilators(@Param("invigilatorList") List<Invigilator> invigilatorList);

    /**
     * 这里根据手机号来判断用户是否重复
     * 已被弃用的教师不计入
     */
    Integer getInvigilatorCount(@Param("contact") String contact);

    Integer getInvigilatorCountExceptSelf(@Param("contact") String contact, @Param("selfId") String selfId);

    Integer updateInvigilatorStatus(@Param("invigilatorId") String invigilatorId, @Param("status") Integer status);

    Integer updateInvigilator(@Param("inv") Invigilator invigilator);

}
