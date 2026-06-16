package com.sisyphus.backend.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "CountsResponse",
        description = "관리자 대시보드용 집계 응답 DTO"
)
public record CountsResponse(

        @Schema(description = "OAuth 계정(연결 계정) 수", example = "120")
        long accountCount,

        @Schema(description = "전체 유저 수", example = "85")
        long userCount
) {}
