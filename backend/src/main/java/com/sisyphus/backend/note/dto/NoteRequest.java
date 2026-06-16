package com.sisyphus.backend.note.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

//  단어 등록 요청 DTO
@Getter
@Setter
@Schema(name = "NoteRequest", description = "노트 생성/수정 요청 DTO")
public class NoteRequest {

    @Schema(description = "제목", example = "OAuth 연동 정리")
    private String title;

    @Schema(description = "부제목", example = "Spring Security + JWT")
    private String subTitle;

    @Schema(description = "본문 내용", example = "오늘은 OAuth 흐름을 정리했다...")
    private String description;

    @Schema(description = "태그 ID 목록", example = "[12, 13]", nullable = true)
    private List<Long> tagIds;

    @Schema(description = "카테고리 ID", example = "3", nullable = true)
    private Long categoryId;

    @Schema(description = "대표 이미지 ID", example = "101", nullable = true)
    private Long imageId;
}

