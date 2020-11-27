package com.tinyplan.exam.entity.form;

/**
 * 后台系统登录表单
 */
public class SystemLoginForm {
    private String username;
    private String password;

    public SystemLoginForm() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
