package com.tinyplan.exam.entity.po;

public class Exam {
    private String examId;
    private Integer level;
    private String examName;
    private Integer capacity;
    private Integer fee;
    private Double passLine;

    public Exam() {}

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
