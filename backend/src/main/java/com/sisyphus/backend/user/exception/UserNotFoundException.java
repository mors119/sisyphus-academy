package com.sisyphus.backend.user.exception;

import com.sisyphus.backend.global.error.ApiErrorCode;
import com.sisyphus.backend.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super("해당 유저를 찾을 수 없습니다.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    @Override public HttpStatus getStatus() { return HttpStatus.NOT_FOUND; }
    @Override public ApiErrorCode getCode() { return ApiErrorCode.NOT_FOUND; }
}
