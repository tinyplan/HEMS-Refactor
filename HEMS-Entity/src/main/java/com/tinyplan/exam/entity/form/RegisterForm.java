package com.tinyplan.exam.entity.form;

/**
 * 登录表单 数据接收
 */
public class RegisterForm {
    // 账户名
    private String username;
    // 密码
    private String password;
    // 联系方式
    private String telephone;
    // 邮箱
    private String email;

    public RegisterForm() {}

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
