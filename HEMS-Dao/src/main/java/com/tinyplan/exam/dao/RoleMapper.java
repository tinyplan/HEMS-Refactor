package com.tinyplan.exam.dao;

import com.tinyplan.exam.entity.po.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {

    List<Role> getRolesByIds(@Param("roleIdList") List<String> roleIdList);

}
