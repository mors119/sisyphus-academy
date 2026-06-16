package com.sisyphus.backend.note.dto;

import com.sisyphus.backend.category.dto.CategorySummary;
import com.sisyphus.backend.image.dto.ImageResponse;
import com.sisyphus.backend.note.entity.Note;
import com.sisyphus.backend.tag.dto.TagResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "NoteResponse", description = "노트 응답 DTO")
public class NoteResponse {

    @Schema(description = "노트 ID", example = "100")
    private Long id;

    @Schema(description = "제목", example = "OAuth 연동 정리")
    private String title;

    @Schema(description = "부제목", example = "Spring Security + JWT", nullable = true)
    private String subTitle;

    @Schema(description = "본문 내용", example = "오늘은 OAuth 흐름을 정리했다...")
    private String description;

    @Schema(description = "태그 목록")
    private List<TagResponse> tags;

    @Schema(description = "생성 시각", example = "2025-12-20T05:10:30")
    private LocalDateTime createdAt;

    @Schema(description = "카테고리 요약", nullable = true)
    private CategorySummary category;

    @Schema(description = "첨부 이미지 목록")
    private List<ImageResponse> image;

    public static NoteResponse fromEntity(Note note) {
        return new NoteResponse(
                note.getId(),
                note.getTitle(),
                note.getSubTitle(),
                note.getDescription(),
                note.getNoteTags().stream()
                        .map(nt -> TagResponse.fromEntity(nt.getTag()))
                        .toList(),
                note.getCreatedAt(),
                note.getCategory() != null ? CategorySummary.fromEntity(note.getCategory()) : null,
                note.getImages().stream()
                        .map(ImageResponse::fromEntity)
                        .toList()
        );
    }
}
