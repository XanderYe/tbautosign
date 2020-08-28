package cn.xanderye.tbautosign.enums;

import lombok.Getter;

/**
 * Created by Xander on 2018-11-05.
 */
@Getter
public enum ErrorCode {
    AUTHENTICATION_EXCEPTION(10000, "认证异常！"),
    ACCOUNT_OR_PASSWORD_ERROR(10001, "账号或密码错误！"),
    ACCOUNT_EXIST(10002, "用户名已存在！"),
    UNSAFE_PASSWORD(10003, "密码不安全！"),
    PARAMETER_ERROR(10004, "参数错误！"),
    PARAMETER_EMPTY(10005, "参数不为空！"),
    CAPTCHA_ERROR(10006, "验证码错误！"),
    BDUSS_ERROR(10007,"BDUSS错误！"),
    BDUSS_EXIST(10008,"BDUSS已存在！");
    private int code;
    private String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}