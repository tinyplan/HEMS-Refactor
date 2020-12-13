package com.tinyplan.exam.entity.vo;

import com.tinyplan.exam.entity.po.CandidateDetail;
import com.tinyplan.exam.entity.po.Role;
import com.tinyplan.exam.entity.po.User;

import java.util.List;

public class CandidateDetailVO implements DetailVO {
    private String id;
    private String accountName;
    private String realName;
    private String idCard;
    private String gender;
    private String contact;
    private String email;
    private String eduBack;
    private String homeAddress;
    private List<Role> roles;
    private String avatar;

    public CandidateDetailVO(CandidateDetail detail) {
        this.id = detail.getId();
        this.realName = detail.getRealName();
        this.idCard = detail.getIdCard();
        this.gender = detail.getGender() == 1 ? "男" : "女";
        this.contact = detail.getContact();
        this.email = detail.getEmail();
        this.eduBack = detail.getEduBack();
        this.homeAddress = detail.getHomeAddress();
        this.avatar = detail.getAvatar();
    }


    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEduBack() {
        return eduBack;
    }

    public void setEduBack(String eduBack) {
        this.eduBack = eduBack;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public void copyValueFromUser(User user) {
        this.accountName = user.getAccountName();
    }
}
