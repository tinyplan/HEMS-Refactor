package com.tinyplan.exam.entity.po;

import com.tinyplan.exam.entity.dto.ScoreDTO;

public class Score {
    private String candidateId;
    private String candidateNo;
    private String examNo;
    private Integer level;
    private Double score;
    private Integer pass;

    public Score() {}

    public Score(ScoreDTO dto, Enroll enroll, ExamDetail detail) {
        this.candidateId = enroll.getCandidateId();
        this.candidateNo = dto.getCandidateNo();
        this.examNo = enroll.getExamNo();
        this.level = detail.getLevel();
        this.score = dto.getScore();
        this.pass = this.score >= detail.getPassLine() ? 1 : -1;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateNo() {
        return candidateNo;
    }

    public void setCandidateNo(String candidateNo) {
        this.candidateNo = candidateNo;
    }

    public String getExamNo() {
        return examNo;
    }

    public void setExamNo(String examNo) {
        this.examNo = examNo;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getPass() {
        return pass;
    }

    public void setPass(Integer pass) {
        this.pass = pass;
    }
}
