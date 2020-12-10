package com.tinyplan.exam.entity.form;

public class EnrollApplyForm {
    private String candidateId;
    private String examNo;
    private String description;

    public EnrollApplyForm() {}

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getExamNo() {
        return examNo;
    }

    public void setExamNo(String examNo) {
        this.examNo = examNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
