package com.tinyplan.exam.entity.vo;

public class ExamSessionVO {
    private String examNo;
    private String examName;
    private String examDate;
    private String examStart;
    private String examEnd;
    private Integer interval;
    private String status;

    public ExamSessionVO() {}

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

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getExamStart() {
        return examStart;
    }

    public void setExamStart(String examStart) {
        this.examStart = examStart;
    }

    public String getExamEnd() {
        return examEnd;
    }

    public void setExamEnd(String examEnd) {
        this.examEnd = examEnd;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
