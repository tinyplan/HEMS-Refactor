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
    // 发布考试信息
    RES_EXAM_DETAIL_HAS_LIVED_EXAM(20035, "有正在进行中的考试"),
    // 考生报名
    RES_EXAM_NOT_DURING_ENROLL(20040, "未在报名时间"),
    RES_EXAM_CAPACITY_OVERFLOW(20041, "报考名额已满"),
    RES_ENROLL_HAVE_NOT_EXAM_QUALIFICATION(20042, "没有考试资格"),
    RES_ENROLL_SAME_EXAM(20043, "不能报考相同的考试"),
    RES_ENROLL_TIME_CONFLICT(20044, "考试时间冲突"),
    // 修改考试状态
    RES_NOT_EXIST_EXAM(20046, "不存在的考试"),
    RES_UPDATE_ILLEGAL_EXAM_STATUS(20047, "修改考试状态非法"),
    RES_UPDATE_EXAM_FAIL(20048, "无法修改考试场次"),
    // 考点管理
    RES_SITE_CONFLICT(20050, "考点已存在"),
    RES_SITE_UPDATE_CAPACITY_FAIL(20051, "有考试待开考, 无法修改考点容量"),
    // 监考教师管理
    RES_ERROR_CONTACT(20056, "错误的手机号码"),
    RES_EXISTED_INV(20057, "重复的教师"),
    // 考试分配
    RES_ARRANGE_NEED_MORE_INV(20061, "教师不足"),

    RES_ILLEGAL_REQUEST(40003, "非法请求"),
    RES_UNKNOWN_ERROR(50000, "未知异常"),
    RES_API_CALL_FAILED(50001, "API调用失败"),
    RES_INVALID_PARAM(50002, "参数校验异常"),
    RES_INVALID_FILE_TYPE(50003, "文件格式不符"),
    RES_INVALID_REQUEST_TYPE(50004, "错误的请求方法");


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
