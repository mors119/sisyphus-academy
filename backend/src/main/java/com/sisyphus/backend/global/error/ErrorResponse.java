package com.sisyphus.backend.global.error;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(name = "ErrorResponse", description = "공통 에러 응답 포맷")
public record ErrorResponse(

        @Schema(description = "HTTP 상태 코드", example = "400")
        int status,

        @Schema(description = "에러 코드(프로젝트 표준)", example = "VALIDATION_ERROR")
        String code,

        @Schema(description = "에러 메시지", example = "요청 값이 올바르지 않습니다.")
        String message,

        @Schema(description = "요청 경로", example = "/api/note/create")
        String path,

        @Schema(description = "에러 발생 시각(UTC)", example = "2025-12-20T00:00:00Z")
        Instant timestamp
) {
    public static ErrorResponse of(int status, String code, String message, String path) {
        return new ErrorResponse(status, code, message, path, Instant.now());
    }
}
