package com.tinyplan.exam.dao;

import com.tinyplan.exam.entity.po.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper {

    User getAdminByUsername(@Param("username") String username);

}
