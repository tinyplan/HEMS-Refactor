package com.tinyplan.exam.entity.pojo;

/**
 * 统一请求返回体
 *
 * @param <T> 数据体类型
 */
public class ApiResult<T> {
    private int code;
    private String message;
    private T data;

    private ApiResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResult(ResultStatus resultStatus, T data) {
        this(resultStatus.getCode(), resultStatus.getMessage(), data);
    }

    /**
     * 覆盖原始信息
     * @param resultStatus 消息状态
     * @param message 自定义消息
     * @param data 返回信息
     */
    public ApiResult(ResultStatus resultStatus, String message, T data) {
        this(resultStatus.getCode(), message, data);
    }

    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }
}
