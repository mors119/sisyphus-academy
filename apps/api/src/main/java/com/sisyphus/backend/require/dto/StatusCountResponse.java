package com.sisyphus.backend.require.dto;

import com.sisyphus.backend.require.util.RequireStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(
        name = "StatusCountResponse",
        description = "대시보드 차트용: 월별 상태 카운트 응답 DTO"
)
public class StatusCountResponse {

    @Schema(description = "상태", example = "RECEIVED")
    private RequireStatus status;

    @Schema(description = "카운트", example = "14")
    private Long count;

    @Schema(description = "월(1~12)", example = "12")
    private Integer month;

    public StatusCountResponse(RequireStatus status, Long count, Integer month) {
        this.status = status;
        this.count = count;
        this.month = month;
    }
}
