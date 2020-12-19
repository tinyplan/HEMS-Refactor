package com.tinyplan.exam.entity.dto;

import com.tinyplan.exam.entity.po.Enroll;
import com.tinyplan.exam.entity.po.EnrollStatusJobInfo;

public class EnrollExecuteResult {
    public transient static final String LOG_TEMPLATE = "\t执行结果: 报名ID: %s, 状态改变: %s ==> %s, 任务结果: %s";
    private String jobId;
    private String enrollId;
    private Integer originalStatus;
    private Integer updateStatus;
    // 执行结果描述
    private String executeResult;

    public EnrollExecuteResult() {}

    public EnrollExecuteResult(Enroll enroll, EnrollStatusJobInfo job) {
        this.jobId = job.getJobId();
        this.enrollId = enroll.getEnrollId();
        this.originalStatus = job.getOriginalStatus();
        this.updateStatus = job.getUpdateStatus();
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getEnrollId() {
        return enrollId;
    }

    public void setEnrollId(String enrollId) {
        this.enrollId = enrollId;
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

    public String getExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(String executeResult) {
        this.executeResult = executeResult;
    }
}
