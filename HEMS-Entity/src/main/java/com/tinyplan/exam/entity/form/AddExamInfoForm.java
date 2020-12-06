package com.tinyplan.exam.entity.form;

public class AddExamInfoForm {
    private String examId;
    private Integer level;
    private String examName;
    private String enrollStart;
    private String enrollEnd;
    private String examStart;
    private String examEnd;
    private Integer capacity;
    private Integer fee;
    private Double passLine;

    public AddExamInfoForm() {}

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getEnrollStart() {
        return enrollStart;
    }

    public void setEnrollStart(String enrollStart) {
        this.enrollStart = enrollStart;
    }

    public String getEnrollEnd() {
        return enrollEnd;
    }

    public void setEnrollEnd(String enrollEnd) {
        this.enrollEnd = enrollEnd;
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Double getPassLine() {
        return passLine;
    }

    public void setPassLine(Double passLine) {
        this.passLine = passLine;
    }
}
