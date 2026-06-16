package com.sisyphus.backend.search.dto;

import com.sisyphus.backend.category.entity.Category;
import com.sisyphus.backend.note.entity.Note;
import com.sisyphus.backend.tag.entity.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(
        name = "SearchResponse",
        description = "통합 검색 결과 아이템(tag/category/note)"
)
public class SearchResponse {

    @Schema(description = "검색 결과 타입", example = "TAG")
    private SearchType type;

    @Schema(description = "대상 ID", example = "12")
    private Long id;

    @Schema(description = "표시 제목(태그명/카테고리명/노트제목)", example = "OAuth 정리")
    private String title;

    private SearchResponse(SearchType type, Long id, String title) {
        this.type = type;
        this.id = id;
        this.title = title;
    }

    public static SearchResponse from(Tag tag) {
        return new SearchResponse(SearchType.TAG, tag.getId(), tag.getName());
    }

    public static SearchResponse from(Category category) {
        return new SearchResponse(SearchType.CATEGORY, category.getId(), category.getTitle());
    }

    public static SearchResponse from(Note note) {
        return new SearchResponse(SearchType.NOTE, note.getId(), note.getTitle());
    }
}
