package com.sisyphus.backend.image.dto;

import com.sisyphus.backend.image.entity.Image;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "ImageUploadResponse",
        description = "이미지 업로드/교체 결과 응답 DTO"
)
public record ImageUploadResponse(

        @Schema(description = "이미지 ID", example = "101")
        Long id,

        @Schema(
                description = "이미지 공개 URL",
                example = "/uploads/images/abc123.webp"
        )
        String url,

        @Schema(description = "원본 파일명", example = "my_photo.png")
        String originName,

        @Schema(description = "파일 확장자(소문자 권장)", example = "png")
        String extension,

        @Schema(description = "파일 크기(bytes)", example = "345678")
        Long size
) {
        /**
         * @param image Image (역할: 엔티티 → DTO 변환, 타입: Image)
         * @return ImageUploadResponse (역할: 응답 DTO, 타입: ImageUploadResponse)
         */
        public static ImageUploadResponse from(Image image) {
                return new ImageUploadResponse(
                        image.getId(),
                        image.getUrl(),
                        image.getOriginName(),
                        image.getExtension(),
                        image.getSize()
                );
        }
}
