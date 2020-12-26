package com.tinyplan.exam.entity.po;

public class InvigilatorArrange {
    private String invigilatorId;
    private String examNo;
    private String siteId;

    public InvigilatorArrange() {
    }

    public InvigilatorArrange(String invigilatorId, String examNo, String siteId) {
        this.invigilatorId = invigilatorId;
        this.examNo = examNo;
        this.siteId = siteId;
    }

    public String getInvigilatorId() {
        return invigilatorId;
    }

    public void setInvigilatorId(String invigilatorId) {
        this.invigilatorId = invigilatorId;
    }

    public String getExamNo() {
        return examNo;
    }

    public void setExamNo(String examNo) {
        this.examNo = examNo;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
