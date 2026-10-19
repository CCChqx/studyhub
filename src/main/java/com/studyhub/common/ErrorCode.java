package com.studyhub.common;

import lombok.Getter;

/**
 * 统一错误码。业务失败只能从这里取，禁止散落魔法数字。
 */
@Getter
public enum ErrorCode {

    SUCCESS(0, "success"),
    PARAM_ERROR(400, "参数校验失败"),
    UNAUTHORIZED(401, "未登录或登录已过期"),
    FORBIDDEN(403, "无权限"),
    NOT_FOUND(404, "资源不存在"),
    CONFLICT(409, "资源已存在"),
    TOO_MANY_REQUESTS(429, "请勿重复提交"),
    SYSTEM_ERROR(500, "系统繁忙，请稍后重试");

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
