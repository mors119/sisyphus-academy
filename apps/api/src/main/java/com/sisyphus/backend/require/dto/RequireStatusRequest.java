package com.sisyphus.backend.require.dto;

import com.sisyphus.backend.require.util.RequireStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "RequireStatusRequest",
        description = "요구사항 상태 변경 요청 DTO (ADMIN 전용 등에서 사용)"
)
public record RequireStatusRequest(
        @Schema(description = "변경할 상태", example = "COMPLETED")
        @NotNull RequireStatus status
) {}
