package com.example.aispringboot.common;

public enum ResultCode {
    //    枚举类的第一项必须要是枚举项
    SUCCESS("200", "操作成功"),
    ERROR("-1", "操作失败"),
    UNAUTHORIZED("401", "未授权"),
    SYSTEM_ERROR("500", "系统错误"),

    // 参数相关错误
    PARAM_ERROR("400", "参数错误"),
    PARAM_MISSING("4001", "缺少必要参数"),
    PARAM_INVALID("4002", "参数无效"),

    // 文件操作相关错误
    FILE_FAILED("5001", "文件操作错误"),
    FILE_UPLOAD_FAILED("5002", "文件上传失败"),
    FILE_DELETE_FAILED("5003", "文件删除失败"),
    FILE_SIZE_FAILED("5004", "文件大小超出限制"),
    FILE_TYPE_NOT_SUPPORTED("5005", "文件类型不支持"),
    FILE_NAME_INVALID("5005", "文件名不合法"),

    // 业务相关错误
    BUSINESS_ERROR("6000", "业务处理失败"),
    ACCOUNT_SAME("6001", "用户名已存在"),
    USER_NOT_EXIST("6002", "用户不存在"),

    // token 相关错误
    TOKEN_INVALID("A0230", "token无效"),
    TOKEN_EXPIRED("A0231", "token过期"),
    TOKEN_BLOCKED("A0232", "token已加入黑名单"),
    TOKEN_ACCESS_FORBIDDEN("A0233", "token已被禁止访问"),
    AUTHORIZED_ERROR("A0300", "访问权限异常"),
    ACCESS_UNAUTHORIZED("A0301", "访问未授权");




    private String code;
    private String msg;

    // 构造方法 一定要是私有的
    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // 获取方法
    public String getCode() {
        return code;
    }
    // 获取方法
    public String getMsg() {
        return msg;
    }
}
