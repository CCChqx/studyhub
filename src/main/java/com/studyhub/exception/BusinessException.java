package com.studyhub.exception;

import com.studyhub.common.ErrorCode;
import lombok.Getter;

/**
 * 业务异常。业务失败一律抛它，由 {@link GlobalExceptionHandler} 统一转成 Result。
 */
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
