package com.arraypay.market.constant;

/**
 * Created by fred on 2017/12/5.
 */
public enum StatusCode {

    SUCCESS("000000", "成功"),
    SYS_ERROR("000001", "系统错误"),
    INVALID_PARAM("000002", "参数错误"),
    USER_NOT_EXIST("000003", "用户不存在"),
    USER_EXIST("000004", "用户已存在"),
    ACTIVE_CODE_INVALID("000005", "短信验证码错误"),
    ACTIVE_CODE_EXPIRED("000006", "短信验证码过期"),

    PERMISSION_ERROR("100001", "认证错误"),
    ACCESS_TOKEN_INVALID("100002", "access_token错误"),
    ACCESS_TOKEN_EXPIRED("100003", "access_token过期"),
    REFRESH_TOKEN_INVALID("100004", "refresh_token错误"),
    REFRESH_TOKEN_EXPIRED("100005", "refresh_token过期");

    private String code;
    private String message;

    StatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static StatusCode getByCode(String code) {
        for (StatusCode statusCode : StatusCode.values()) {
            if (statusCode.getCode().equals(code)) {
                return statusCode;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
