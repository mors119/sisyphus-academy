package com.sisyphus.backend.tag.dto;

import com.sisyphus.backend.tag.entity.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Schema(
        name = "TagResponse",
        description = "태그 응답 DTO"
)
public class TagResponse {

    @Schema(description = "태그 ID", example = "12")
    private Long id;

    @Schema(description = "태그 이름", example = "Frontend")
    private String name;

    public static TagResponse fromEntity(Tag tag) {
        return new TagResponse(tag.getId(), tag.getName());
    }
}
