package com.tinyplan.exam.entity.form;

import com.tinyplan.exam.entity.po.CandidateDetail;

public class EnrollForm {
    private String examId;
    private CandidateDetail candidateInfo;

    public EnrollForm() {}

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public CandidateDetail getCandidateInfo() {
        return candidateInfo;
    }

    public void setCandidateInfo(CandidateDetail candidateInfo) {
        this.candidateInfo = candidateInfo;
    }
}
