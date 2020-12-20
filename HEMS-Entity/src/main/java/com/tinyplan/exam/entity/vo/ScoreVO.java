package com.tinyplan.exam.entity.vo;

import com.tinyplan.exam.entity.po.CandidateDetail;
import com.tinyplan.exam.entity.po.ExamDetail;
import com.tinyplan.exam.entity.po.Score;

public class ScoreVO {
    private String candidateNo;
    private String realName;
    private String examNo;
    private String examName;
    private Double score;
    private String pass;

    public ScoreVO() {
    }

    public ScoreVO(Score score, ExamDetail examDetail, CandidateDetail candidateDetail) {
        this.candidateNo = score.getCandidateNo();
        this.realName = candidateDetail.getRealName();
        this.examNo = score.getExamNo();
        this.examName = examDetail.getExamName();
        this.score = score.getScore();
        this.pass = score.getPass() == 1 ? "合格" : "不合格";
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

    public String getExamNo() {
        return examNo;
    }

    public void setExamNo(String examNo) {
        this.examNo = examNo;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
