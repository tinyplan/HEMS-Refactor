package com.tinyplan.exam.entity.form;

public class UpdateInvigilatorForm {
    private String invigilatorId;
    private String realName;
    private String contact;

    public UpdateInvigilatorForm() {
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
