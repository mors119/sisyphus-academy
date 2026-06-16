package com.sisyphus.backend.image.dto;

import com.sisyphus.backend.image.entity.NoteImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(
        name = "ImageResponse",
        description = "노트/게시글 등에 첨부된 이미지 정보 응답 DTO"
)
public class ImageResponse {

    @Schema(description = "이미지 ID", example = "101")
    private Long id;

    @Schema(description = "원본 파일명", example = "my_photo.png")
    private String originName;

    @Schema(
            description = "이미지 접근 URL 또는 경로",
            example = "/uploads/images/2025/12/20/abc123.webp"
    )
    private String url;

    public static ImageResponse fromEntity(NoteImage noteImage) {
        return new ImageResponse(
                noteImage.getId(),
                noteImage.getOriginName(),
                noteImage.getUrl()
        );
    }
}
