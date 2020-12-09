package com.tinyplan.exam.entity.dto;

/**
 * 考试状态处理结果
 */
public class ExamDetailExecuteResult {
    public transient static final String LOG_TEMPLATE = "执行结果: 考试NO: %s, 考试名称: %s, 状态改变: %s ==> %s, 任务结果: %s";
    private String jobId;
    private String examNo;
    private String examName;
    private Integer originalStatus;
    private Integer updateStatus;
    // 执行结果描述
    private String executeResult;

    public ExamDetailExecuteResult() {}

    public String getExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(String executeResult) {
        this.executeResult = executeResult;
    }

    public String getExamNo() {
        return examNo;
    }

    public void setExamNo(String examNo) {
        this.examNo = examNo;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Integer getOriginalStatus() {
        return originalStatus;
    }

    public void setOriginalStatus(Integer originalStatus) {
        this.originalStatus = originalStatus;
    }

    public Integer getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }
}
