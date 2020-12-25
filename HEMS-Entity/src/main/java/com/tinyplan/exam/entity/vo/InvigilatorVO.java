package com.tinyplan.exam.entity.vo;

import com.tinyplan.exam.entity.po.Invigilator;

public class InvigilatorVO {
    private String invigilatorId;
    private String realName;
    private String contact;

    public InvigilatorVO(Invigilator invigilator) {
        this.invigilatorId = invigilator.getInvigilatorId();
        this.realName = invigilator.getRealName();
        this.contact = invigilator.getContact();
    }

    public String getInvigilatorId() {
        return invigilatorId;
    }

    public void setInvigilatorId(String invigilatorId) {
        this.invigilatorId = invigilatorId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
