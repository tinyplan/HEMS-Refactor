package com.tinyplan.exam.entity.dto;

public class ScoreDTO {
    private String examNo;
    private String candidateNo;
    private String realName;
    private Double score;

    public ScoreDTO() {
    }

    public String getExamNo() {
        return examNo;
    }

    public void setExamNo(String examNo) {
        this.examNo = examNo;
    }

    public String getCandidateNo() {
        return candidateNo;
    }

    public void setCandidateNo(String candidateNo) {
        this.candidateNo = candidateNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ScoreDTO{" +
                "examNo='" + examNo + '\'' +
                ", candidateNo='" + candidateNo + '\'' +
                ", realName='" + realName + '\'' +
                ", score=" + score +
                '}';
    }
}
