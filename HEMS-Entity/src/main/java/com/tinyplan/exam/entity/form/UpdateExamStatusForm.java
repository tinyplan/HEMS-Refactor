package com.tinyplan.exam.entity.form;

public class UpdateExamStatusForm {
    private String examNo;
    private Integer status;

    public UpdateExamStatusForm() {}

    public String getExamNo() {
        return examNo;
    }

    public void setExamNo(String examNo) {
        this.examNo = examNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
