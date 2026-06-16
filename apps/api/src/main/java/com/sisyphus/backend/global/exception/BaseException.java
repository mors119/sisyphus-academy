package com.sisyphus.backend.global.exception;

import com.sisyphus.backend.global.error.ApiErrorCode;
import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {

    protected BaseException(String message) {
        super(message);
    }

    public abstract HttpStatus getStatus();

    public abstract ApiErrorCode getCode();
}