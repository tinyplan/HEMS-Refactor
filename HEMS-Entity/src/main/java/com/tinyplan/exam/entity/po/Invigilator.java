package com.tinyplan.exam.entity.po;

public class Invigilator extends User {
    private String invigilatorId;
    private String accountName;
    private String realName;
    private String password;
    private String contact;
    private String roleId;
    private Integer status;

    public Invigilator() {
    }

    public String getInvigilatorId() {
        return invigilatorId;
    }

    public void setInvigilatorId(String invigilatorId) {
        this.invigilatorId = invigilatorId;
    }

    @Override
    public String getAccountName() {
        return accountName;
    }

    @Override
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String getRoleId() {
        return roleId;
    }

    @Override
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Invigilator{" +
                "invigilatorId='" + invigilatorId + '\'' +
                ", accountName='" + accountName + '\'' +
                ", realName='" + realName + '\'' +
                ", password='" + password + '\'' +
                ", contact='" + contact + '\'' +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
