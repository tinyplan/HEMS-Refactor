package com.tinyplan.exam.entity.pojo;

/**
 * 日志实体类
 */
public class Log {
    private String userId;
    // 模块名
    private String module;
    // 调用方法名
    private String method;
    // 访问ip地址
    private String ip;
    // 访问时间
    private String date;
    // 响应时间
    private Double executeTime;
    // 调用结果
    private String result;

    public Log() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Double executeTime) {
        this.executeTime = executeTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Log{" +
                "userId='" + userId + '\'' +
                ", module='" + module + '\'' +
                ", method='" + method + '\'' +
                ", ip='" + ip + '\'' +
                ", date='" + date + '\'' +
                ", executeTime=" + executeTime +
                ", result='" + result + '\'' +
                '}';
    }
}
