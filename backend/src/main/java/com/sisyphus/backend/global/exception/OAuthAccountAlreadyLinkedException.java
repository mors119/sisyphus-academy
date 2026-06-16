package com.sisyphus.backend.global.exception;

import com.sisyphus.backend.global.error.ApiErrorCode;
import com.sisyphus.backend.user.util.Provider;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OAuthAccountAlreadyLinkedException extends BaseException {

    private final Provider provider;

    public OAuthAccountAlreadyLinkedException(Provider provider) {
        super("이미 연동된 " + provider + " 계정입니다.");
        this.provider = provider;
    }

    @Override public HttpStatus getStatus() { return HttpStatus.CONFLICT; }
    @Override public ApiErrorCode getCode() { return ApiErrorCode.CONFLICT; }
}
