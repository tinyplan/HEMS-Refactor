package com.tinyplan.exam.entity.vo;

public class SystemArrangeVO {
    private String candidateId;
    private String realName;
    private String candidateNo;
    private String examName;
    private String session;
    private String site;
    private Integer seat;

    public SystemArrangeVO() {
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCandidateNo() {
        return candidateNo;
    }

    public void setCandidateNo(String candidateNo) {
        this.candidateNo = candidateNo;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }
}
