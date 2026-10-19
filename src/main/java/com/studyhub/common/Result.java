package com.studyhub.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 统一返回体（不可变值对象）。
 * 字段用 final：没有 setter、不可变；构造器私有：只能通过 success()/error() 工厂方法创建。
 * 约定：code == 0 表示成功，非 0 表示失败（错误码见 {@link ErrorCode}）。
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Result<T> {

    private final Integer code;
    private final String message;
    private final T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> error(ErrorCode errorCode) {
        return new Result<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> Result<T> error(ErrorCode errorCode, String message) {
        return new Result<>(errorCode.getCode(), message, null);
    }
}
