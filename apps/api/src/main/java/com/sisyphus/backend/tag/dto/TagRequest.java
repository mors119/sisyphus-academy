package com.sisyphus.backend.tag.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "TagRequest",
        description = "태그 생성/수정 요청 DTO"
)
public class TagRequest {

    @Schema(
            description = "태그 ID (수정 시 사용, 생성 시 null 가능)",
            example = "12",
            nullable = true
    )
    private Long id;

    @Schema(
            description = "태그 이름",
            example = "Frontend"
    )
    private String name;
}
