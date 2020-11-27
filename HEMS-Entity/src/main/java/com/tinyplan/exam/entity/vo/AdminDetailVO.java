package com.tinyplan.exam.entity.vo;

import com.tinyplan.exam.entity.po.Role;
import com.tinyplan.exam.entity.po.User;

import java.io.Serializable;
import java.util.List;

/**
 * 管理者详细信息
 */
public class AdminDetailVO implements DetailVO {
    private String id;
    private String accountName;
    private String avatar;
    private List<Role> roles;

    public AdminDetailVO() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * 从用户对象中获取数据
     *
     * @param user 用户对象
     */
    @Override
    public void copyValueFromUser(User user){
        this.setId(user.getId());
        this.setAccountName(user.getAccountName());
        this.setAvatar(user.getAvatar());
    }
}
