package com.tinyplan.exam.entity.pojo.aliyun;

public class CertificateBack {
    // 有效期起始
    private String start_date;
    // 有效期结束
    private String end_date;
    // 签发机关
    private String issue;
    private boolean success;

    public CertificateBack() {}

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "CertificateBack{" +
                "start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", issue='" + issue + '\'' +
                ", success=" + success +
                '}';
    }
}
