package com.tinyplan.exam.entity.pojo;

/**
 * 通用业务异常类
 */
public class BusinessException extends RuntimeException{

    private int code;
    /**
     * 不使用message变量名
     *
     * 若使用，则生成的getter方法名为getMessage(),
     * 而父类中同名方法返回的是detailMessage, 有歧义
     */
    private String msg;
    private ResultStatus status;

    public BusinessException(){
        // 默认为未知错误
        this(ResultStatus.RES_UNKNOWN_ERROR);
    }

    public BusinessException(ResultStatus status){
        // 使用默认的消息
        this(status, status.getMessage());
    }

    public BusinessException(ResultStatus status, String msg){
        this.code = status.getCode();
        this.msg = msg;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public ResultStatus getStatus() {
        return status;
    }
}
