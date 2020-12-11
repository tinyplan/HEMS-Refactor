package com.tinyplan.exam.entity.form;

public class UpdateApplyStatusForm {
    private String applyId;
    private String feedback;

    public UpdateApplyStatusForm() {}

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
