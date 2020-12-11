package com.tinyplan.exam.entity.vo;

public class PortalEnrollVO {
    private PortalCandidateInfoVO candidateInfo;
    private ExamDetailVO examInfo;

    public PortalEnrollVO() {}

    public PortalCandidateInfoVO getCandidateInfo() {
        return candidateInfo;
    }

    public void setCandidateInfo(PortalCandidateInfoVO candidateInfo) {
        this.candidateInfo = candidateInfo;
    }

    public ExamDetailVO getExamInfo() {
        return examInfo;
    }

    public void setExamInfo(ExamDetailVO examInfo) {
        this.examInfo = examInfo;
    }
}
