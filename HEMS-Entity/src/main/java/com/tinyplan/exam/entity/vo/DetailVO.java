package com.tinyplan.exam.entity.vo;

import com.tinyplan.exam.entity.po.Role;
import com.tinyplan.exam.entity.po.User;

import java.io.Serializable;
import java.util.List;

public interface DetailVO extends Serializable {

    void copyValueFromUser(User user);

    // 为了编码方便，才设置这个方法
    void setRoles(List<Role> roles);
}
