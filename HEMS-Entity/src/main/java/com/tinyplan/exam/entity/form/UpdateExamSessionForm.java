package com.tinyplan.exam.entity.form;

public class UpdateExamSessionForm {
    private String examNo;
    private String examStart;
    private String examEnd;
    private Integer interval;

    public UpdateExamSessionForm() {}

    public String getExamNo() {
        return examNo;
    }

    public void setExamNo(String examNo) {
        this.examNo = examNo;
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
}
