package com.tinyplan.exam.entity.po;

import java.io.Serializable;

/**
 * 角色
 */
public class Role implements Serializable {
    private String roleId;
    private String roleName;
    private String description;

    public Role() {}

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
