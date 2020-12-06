package com.tinyplan.exam.entity.pojo;

/**
 * 状态码
 */
public enum ResultStatus {
    RES_SUCCESS(20000, "请求成功"),
    // 登录
    RES_LOGIN_UNKNOWN_USER(20001, "用户不存在"),
    RES_LOGIN_WRONG_PASS(20002, "密码错误"),
    // 获取用户信息
    RES_INFO_NOT_EXIST(20006, "用户信息不存在"),
    RES_INFO_UPDATE_FAILED(20007, "更新用户信息失败"),
    RES_INFO_WRONG_OLD_PASSWORD(20008, "原密码错误"),
    RES_INFO_EXISTED_ACCOUNT_NAME(20009, "重复的用户名"),
    // 注册
    RES_REGISTER_EXISTED_USER(20010, "存在重复用户"),
    RES_REGISTER_FAIL(20011, "注册失败"),
    // 文件上传
    RES_FILE_UPLOAD_FAILED(20020, "文件上传失败"),
    RES_FILE_DELETE_NOT_EXIST(20021, "文件不存在"),
    RES_FILE_DELETE_FAILED(20022, "文件删除失败"),
    // 新闻
    RES_NEWS_PUBLISH_FAILED(20025, "发布新闻失败"),
    RES_NEWS_DELETE_FAILED(20026, "删除新闻失败"),
    RES_NEWS_UPDATE_FAILED(20027, "更新新闻失败"),
    // 实名认证失败
    RES_CERTIFICATE_RECOGNIZE_FAILED(20030, "身份证识别失败"),
    RES_CERTIFICATE_OUT_OF_DATE(20031, "身份证已失效"),
    RES_CERTIFICATE_FAILED(20032, "身份证信息与输入的信息不匹配"),

    RES_ILLEGAL_REQUEST(40003, "非法请求"),
    RES_UNKNOWN_ERROR(50000, "未知异常"),
    RES_API_CALL_FAILED(50001, "API调用失败"),
    RES_INVALID_PARAM(50002, "参数校验异常");

    private final int code;
    private final String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
