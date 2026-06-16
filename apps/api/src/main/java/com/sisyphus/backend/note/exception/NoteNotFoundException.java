package com.sisyphus.backend.note.exception;

import com.sisyphus.backend.global.error.ApiErrorCode;
import com.sisyphus.backend.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class NoteNotFoundException extends BaseException {

    public NoteNotFoundException() {
        super("해당 노트를 찾을 수 없습니다.");
    }

    @Override public HttpStatus getStatus() { return HttpStatus.NOT_FOUND; }
    @Override public ApiErrorCode getCode() { return ApiErrorCode.NOT_FOUND; }
}
