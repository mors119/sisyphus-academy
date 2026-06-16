package com.sisyphus.backend.global.exception;

import com.sisyphus.backend.global.error.ApiErrorCode;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseException {

    public UnauthorizedException(String message) {
        super(message);
    }

    @Override public HttpStatus getStatus() { return HttpStatus.UNAUTHORIZED; }
    @Override public ApiErrorCode getCode() { return ApiErrorCode.UNAUTHORIZED; }
}
