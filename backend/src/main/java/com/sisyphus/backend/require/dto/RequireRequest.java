package com.sisyphus.backend.require.dto;

import com.sisyphus.backend.require.util.RequireStatus;
import com.sisyphus.backend.require.util.RequireType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "RequireRequest",
        description = "요구사항(Require) 생성/수정 요청 DTO"
)
public class RequireRequest {

    @Schema(
            description = "요구사항 타입",
            example = "BUG",
            allowableValues = {"BUG", "NEW"}
    )
    private RequireType requireType;

    @Schema(
            description = "요구사항 제목",
            example = "태그 드래그 앤 드롭 정렬 기능"
    )
    private String title;

    @Schema(
            description = "요구사항 설명",
            example = "태그를 드래그해서 순서를 변경할 수 있도록 구현합니다."
    )
    private String description;

    @Schema(
            description = "요구사항 상태",
            example = "RECEIVED"
    )
    private RequireStatus status;
}
